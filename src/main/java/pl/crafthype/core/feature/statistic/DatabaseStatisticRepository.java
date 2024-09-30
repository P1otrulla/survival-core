package pl.crafthype.core.feature.statistic;

import com.j256.ormlite.table.TableUtils;
import pl.crafthype.core.database.AbstractRepository;
import pl.crafthype.core.database.DatabaseService;

import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class DatabaseStatisticRepository extends AbstractRepository implements StatisticRepository {

    private DatabaseStatisticRepository(DatabaseService databaseService) {
        super(databaseService);
    }

    public static DatabaseStatisticRepository create(DatabaseService databaseService) {
        try {
            TableUtils.createTableIfNotExists(databaseService.connection(), StatisticEntryWrapper.class);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new DatabaseStatisticRepository(databaseService);
    }

    @Override
    public void updateEntry(StatisticEntry entry) {
        this.action(StatisticEntryWrapper.class, dao -> {
            StatisticEntryWrapper dbEntry = dao.queryBuilder()
                .where()
                .eq("user_id", entry.owner())
                .and()
                .eq("statistic", entry.statistic().name())
                .queryForFirst();

            if (dbEntry == null) {
                dbEntry = StatisticEntryWrapper.from(entry.owner(), entry);

                return dao.create(dbEntry);
            }

            return dao.update(new StatisticEntryWrapper(dbEntry.id(), entry.owner(), entry));
        });
    }

    @Override
    public CompletableFuture<Collection<StatisticEntry>> getAll() {
        return this.selectAll(StatisticEntryWrapper.class).thenApply(entries ->
            entries.stream().map(StatisticEntryWrapper::toStatisticEntry).toList());
    }
}
