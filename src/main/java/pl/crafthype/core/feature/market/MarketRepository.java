package pl.crafthype.core.feature.market;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MarketRepository {

    void addItem(MarketItem item);

    void removeItem(MarketItem item);

    CompletableFuture<List<MarketItem>> findItems();
}
