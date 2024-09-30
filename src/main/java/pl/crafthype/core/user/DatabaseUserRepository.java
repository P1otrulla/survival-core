package pl.crafthype.core.user;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import pl.crafthype.core.database.AbstractRepository;
import pl.crafthype.core.database.DatabaseService;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DatabaseUserRepository extends AbstractRepository implements UserRepository {

    private DatabaseUserRepository(DatabaseService databaseService) {
        super(databaseService);
    }

    public static DatabaseUserRepository create(DatabaseService databaseService) {
        try {
            TableUtils.createTableIfNotExists(databaseService.connection(), UserWrapper.class);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new DatabaseUserRepository(databaseService);
    }

    @Override
    public void save(User user) {
        this.action(UserWrapper.class, dao -> {
            QueryBuilder<UserWrapper, Object> queryBuilder = dao.queryBuilder();

            UserWrapper userWrapper = queryBuilder.where()
                .eq("unique_id", user.uniqueId())
                .queryForFirst();

            if (userWrapper == null) {
                userWrapper = UserWrapper.from(user);

                return dao.create(userWrapper);
            }

            return dao.update(userWrapper);
        });
    }

    @Override
    public CompletableFuture<List<User>> loadUsers() {
        return this.selectAll(UserWrapper.class)
            .thenApply(userWrappers -> userWrappers.stream()
                .map(UserWrapper::toUser)
                .toList());
    }
}