package pl.crafthype.core.feature.weding;

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
import java.util.function.Function;

public class DatabaseWeddingRepository extends AbstractRepository implements WeddingRepository {

    private static final Executor EXECUTOR = Executors.newCachedThreadPool();
    private final AsyncLoadingCache<UUID, CompletableFuture<Wedding>> cache = Caffeine.newBuilder()
        .expireAfterWrite(3, TimeUnit.MINUTES)
        .expireAfterAccess(1, TimeUnit.MINUTES)
        .refreshAfterWrite(3, TimeUnit.SECONDS)
        .executor(EXECUTOR)
        .buildAsync(this::findWedding);

    protected DatabaseWeddingRepository(DatabaseService databaseService) {
        super(databaseService);
    }

    public static DatabaseWeddingRepository create(DatabaseService databaseService) {
        try {
            TableUtils.createTableIfNotExists(databaseService.connection(), WeddingWrapper.class);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new DatabaseWeddingRepository(databaseService);
    }

    @Override
    public CompletableFuture<Wedding> findWedding(UUID uniqueId) {
        return this.action(WeddingWrapper.class, dao -> {
            QueryBuilder<WeddingWrapper, Object> queryBuilder = dao.queryBuilder();

            queryBuilder.where()
                .eq("player_one", uniqueId)
                .or()
                .eq("player_two", uniqueId);

            WeddingWrapper weddingWrapper = queryBuilder.queryForFirst();

            if (weddingWrapper == null) {
                return Wedding.empty();
            }

            return weddingWrapper.toWedding();
        });
    }

    @Override
    public CompletableFuture<Void> createWedding(Wedding wedding) {
        return this.save(WeddingWrapper.class, WeddingWrapper.from(wedding)).thenApply(aVoid -> {
            this.cache.synchronous().refresh(wedding.playerOne());
            this.cache.synchronous().refresh(wedding.playerTwo());

            return null;
        });
    }

    @Override
    public CompletableFuture<Void> deleteWedding(UUID uniqueId) {
        return this.action(WeddingWrapper.class, dao -> {
            QueryBuilder<WeddingWrapper, Object> queryBuilder = dao.queryBuilder();

            queryBuilder.where()
                .eq("player_one", uniqueId)
                .or()
                .eq("player_two", uniqueId);

            WeddingWrapper weddingWrapper = queryBuilder.queryForFirst();

            if (weddingWrapper != null) {
                dao.delete(weddingWrapper);

                this.cache.synchronous().invalidate(weddingWrapper.playerOne());
                this.cache.synchronous().invalidate(weddingWrapper.playerTwo());
            }

            return null;
        });
    }

    @Override
    public CompletableFuture<Wedding> findWeddingAsync(UUID uniqueId) {
        return this.cache.get(uniqueId).thenCompose(Function.identity());
    }

    @Override
    public void refresh(UUID uniqueId) {
        this.cache.synchronous().refresh(uniqueId);
    }
}
