package pl.crafthype.core.feature.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MarketExpiredService {

    private final MarketExpiredRepository repository;
    private final Map<UUID, List<MarketItem>> expiredItems = new HashMap<>();

    public MarketExpiredService(MarketExpiredRepository repository) {
        this.repository = repository;
    }

    public void addItem(MarketItem item) {
        this.addExpired(item);

        this.repository.addItem(item);
    }

    public void removeItem(MarketItem item) {
        List<MarketItem> items = this.expiredItems.getOrDefault(item.uniqueId(), new ArrayList<>());
        items.remove(item);

        this.expiredItems.put(item.uniqueId(), items);

        this.repository.removeItem(item);
    }

    public List<MarketItem> findExpiredItems(UUID uniqueId) {
        return this.expiredItems.getOrDefault(uniqueId, new ArrayList<>());
    }

    public void addExpired(MarketItem marketItem) {
        List<MarketItem> items = this.expiredItems.getOrDefault(marketItem.uniqueId(), new ArrayList<>());
        items.add(marketItem);

        this.expiredItems.put(marketItem.uniqueId(), items);
    }
}
