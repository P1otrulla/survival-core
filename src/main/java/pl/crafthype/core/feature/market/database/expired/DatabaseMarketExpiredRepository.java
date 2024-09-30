package pl.crafthype.core.feature.market.database.expired;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import pl.crafthype.core.database.AbstractRepository;
import pl.crafthype.core.database.DatabaseService;
import pl.crafthype.core.feature.market.MarketExpiredRepository;
import pl.crafthype.core.feature.market.MarketItem;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DatabaseMarketExpiredRepository extends AbstractRepository implements MarketExpiredRepository {

    protected DatabaseMarketExpiredRepository(DatabaseService databaseService) {
        super(databaseService);
    }

    public static DatabaseMarketExpiredRepository create(DatabaseService databaseService) {
        try {
            TableUtils.createTableIfNotExists(databaseService.connection(), ExpiredMarketItemWrapper.class);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new DatabaseMarketExpiredRepository(databaseService);
    }

    @Override
    public void addItem(MarketItem item) {
        this.save(ExpiredMarketItemWrapper.class, ExpiredMarketItemWrapper.from(item));
    }

    @Override
    public void removeItem(MarketItem item) {
        this.action(ExpiredMarketItemWrapper.class, dao -> {
            DeleteBuilder<ExpiredMarketItemWrapper, Object> deleteBuilder = dao.deleteBuilder();

            deleteBuilder.where()
                .eq("unique_id", item.uniqueId())
                .and()
                .eq("item", item.itemStack());

            return deleteBuilder.delete();
        });
    }

    @Override
    public CompletableFuture<List<MarketItem>> findAll() {
        return this.selectAll(ExpiredMarketItemWrapper.class)
            .thenApply(item -> item.stream().map(ExpiredMarketItemWrapper::toMarketItem).toList());
    }
}
