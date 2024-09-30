package pl.crafthype.core.feature.achievement.database;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.j256.ormlite.table.TableUtils;
import pl.crafthype.core.database.AbstractRepository;
import pl.crafthype.core.database.DatabaseService;
import pl.crafthype.core.database.Refreshable;
import pl.crafthype.core.feature.achievement.Achievement;
import pl.crafthype.core.feature.achievement.AchievementRepository;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DatabaseAchievementRepository extends AbstractRepository implements AchievementRepository, Refreshable {

    private static final Executor EXECUTOR = Executors.newCachedThreadPool();
    private final AsyncLoadingCache<UUID, Integer> cache = Caffeine.newBuilder()
        .expireAfterWrite(3, TimeUnit.MINUTES)
        .expireAfterAccess(1, TimeUnit.MINUTES)
        .refreshAfterWrite(3, TimeUnit.SECONDS)
        .executor(EXECUTOR)
        .buildAsync((uuid, executor) -> this.findGained_(uuid));

    public DatabaseAchievementRepository(DatabaseService databaseService) {
        super(databaseService);
    }

    public static DatabaseAchievementRepository create(DatabaseService databaseService) {
        try {
            TableUtils.createTableIfNotExists(databaseService.connection(), AchievementWrapper.class);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new DatabaseAchievementRepository(databaseService);
    }

    @Override
    public void add(UUID uniqueId, Achievement achievement) {
        this.save(AchievementWrapper.class, AchievementWrapper.from(uniqueId, achievement.id()));
        this.cache.synchronous().refresh(uniqueId);
    }

    @Override
    public CompletableFuture<Boolean> isGained(UUID uniqueId, Achievement achievement) {
        return this.action(AchievementWrapper.class, dao -> dao.queryBuilder()
            .where()
            .eq("user_id", uniqueId)
            .and()
            .eq("achievement_id", achievement.id())
            .queryForFirst() != null);
    }

    public CompletableFuture<Integer> findGained_(UUID uniqueId) {
        return this.action(AchievementWrapper.class, dao -> dao.queryBuilder()
            .where()
            .eq("user_id", uniqueId)
            .query()
            .size());
    }

    @Override
    public int findGained(UUID uniqueId) {
        return this.cache.synchronous().get(uniqueId);
    }

    @Override
    public void refresh(UUID uniqueId) {
        this.cache.synchronous().refresh(uniqueId);
    }

}