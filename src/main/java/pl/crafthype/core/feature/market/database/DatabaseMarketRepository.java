package pl.crafthype.core.feature.market.database;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import pl.crafthype.core.database.AbstractRepository;
import pl.crafthype.core.database.DatabaseService;
import pl.crafthype.core.feature.market.MarketItem;
import pl.crafthype.core.feature.market.MarketRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DatabaseMarketRepository extends AbstractRepository implements MarketRepository {

    protected DatabaseMarketRepository(DatabaseService databaseService) {
        super(databaseService);
    }

    public static DatabaseMarketRepository create(DatabaseService databaseService) {
        try {
            TableUtils.createTableIfNotExists(databaseService.connection(), MarketItemWrapper.class);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new DatabaseMarketRepository(databaseService);
    }

    @Override
    public void addItem(MarketItem item) {
        this.save(MarketItemWrapper.class, MarketItemWrapper.from(item));
    }

    @Override
    public void removeItem(MarketItem item) {
        this.action(MarketItemWrapper.class, dao -> {
            DeleteBuilder<MarketItemWrapper, Object> deleteBuilder = dao.deleteBuilder();

            deleteBuilder
                .where()
                .eq("item_id", item.itemId());

            return deleteBuilder.delete();
        });
    }

    @Override
    public CompletableFuture<List<MarketItem>> findItems() {
        return this.selectAll(MarketItemWrapper.class)
            .thenApply(item -> item.stream().map(MarketItemWrapper::toMarketItem).toList());
    }
}
