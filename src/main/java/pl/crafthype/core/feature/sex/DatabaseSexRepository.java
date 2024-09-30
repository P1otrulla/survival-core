package pl.crafthype.core.feature.sex;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import pl.crafthype.core.database.AbstractRepository;
import pl.crafthype.core.database.DatabaseService;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DatabaseSexRepository extends AbstractRepository implements SexRepository {

    private static final Executor EXECUTOR = Executors.newCachedThreadPool();
    private final AsyncLoadingCache<UUID, CompletableFuture<String>> cache = Caffeine.newBuilder()
        .expireAfterWrite(3, TimeUnit.MINUTES)
        .expireAfterAccess(1, TimeUnit.MINUTES)
        .refreshAfterWrite(3, TimeUnit.SECONDS)
        .executor(EXECUTOR)
        .buildAsync(this::findSex);

    protected DatabaseSexRepository(DatabaseService databaseService) {
        super(databaseService);
    }

    public static DatabaseSexRepository create(DatabaseService databaseService) {
        try {
            TableUtils.createTableIfNotExists(databaseService.connection(), SexWrapper.class);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new DatabaseSexRepository(databaseService);
    }

    @Override
    public void updateSex(UUID uniqueId, String sex) {
        this.action(SexWrapper.class, dao -> {
            QueryBuilder<SexWrapper, Object> queryBuilder = dao.queryBuilder();

            queryBuilder.where()
                .eq("unique_id", uniqueId);

            SexWrapper sexWrapper = queryBuilder.queryForFirst();

            if (sexWrapper == null) {
                return dao.create(SexWrapper.from(uniqueId, sex));
            }

            sexWrapper.updateSex(sex);

            return dao.update(sexWrapper);
        }).thenApply(aVoid -> {
            this.cache.synchronous().refresh(uniqueId);
            return null;
        });

    }

    @Override
    public CompletableFuture<String> findSex(UUID uniqueId) {
        return this.action(SexWrapper.class, dao -> {
            SexWrapper sexWrapper = dao.queryBuilder()
                .where()
                .eq("unique_id", uniqueId)
                .queryForFirst();

            return sexWrapper == null ? "null" : sexWrapper.sex();
        });
    }

    @Override
    public String findSexSync(UUID uniqueId) {
        return this.cache.synchronous().get(uniqueId).join();
    }

    @Override
    public void refresh(UUID uniqueId) {
        this.cache.synchronous().refresh(uniqueId);
    }
}
