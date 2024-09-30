package pl.crafthype.core.feature.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarketService {

    private final List<MarketItem> items = new ArrayList<>();
    private final MarketRepository repository;

    public MarketService(MarketRepository repository) {
        this.repository = repository;
    }

    public void addItem(MarketItem item) {
        this.items.add(item);

        this.repository.addItem(item);
    }

    public void removeItem(MarketItem item) {
        this.items.remove(item);

        this.repository.removeItem(item);
    }

    public boolean containsItem(MarketItem item) {
        return this.items.contains(item);
    }

    public void addAllItems(List<MarketItem> items) {
        this.items.addAll(items);
    }

    public List<MarketItem> items() {
        return Collections.unmodifiableList(this.items);
    }

}
