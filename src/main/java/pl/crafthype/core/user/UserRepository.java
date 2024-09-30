package pl.crafthype.core.user;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserRepository {

    void save(User user);

    CompletableFuture<List<User>> loadUsers();

}
