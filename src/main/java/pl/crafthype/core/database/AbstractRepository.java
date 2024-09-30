package pl.crafthype.core.database;

import com.j256.ormlite.dao.Dao;
import panda.std.function.ThrowingFunction;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractRepository {

    protected final DatabaseService databaseService;

    protected AbstractRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public <T> CompletableFuture<Dao.CreateOrUpdateStatus> save(Class<T> type, T warp) {
        return this.action(type, dao -> dao.createOrUpdate(warp));
    }

    public <T> CompletableFuture<T> saveIfNotExist(Class<T> type, T warp) {
        return this.action(type, dao -> dao.createIfNotExists(warp));
    }

    public <T, ID> CompletableFuture<T> select(Class<T> type, ID id) {
        return this.action(type, dao -> dao.queryForId(id));
    }

    public <T> CompletableFuture<Integer> delete(Class<T> type, T warp) {
        return this.action(type, dao -> dao.delete(warp));
    }

    public <T, ID> CompletableFuture<Integer> deleteById(Class<T> type, ID id) {
        return this.action(type, dao -> dao.deleteById(id));
    }

    public <T> CompletableFuture<List<T>> selectAll(Class<T> type) {
        return this.action(type, Dao::queryForAll);
    }

    public <T, ID, R> CompletableFuture<R> action(Class<T> type, ThrowingFunction<Dao<T, ID>, R, SQLException> action) {
        CompletableFuture<R> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            Dao<T, ID> dao = this.databaseService.getDao(type);

            try {
                completableFuture.complete(action.apply(dao));
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return completableFuture;
    }

}
