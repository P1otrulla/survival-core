package pl.crafthype.core.user;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService {

    private final Map<UUID, User> users = new HashMap<>();

    public User findOrCreate(UUID uniqueId) {
        return this.users.computeIfAbsent(uniqueId, User::new);
    }

    public Optional<User> find(UUID uniqueId) {
        return Optional.ofNullable(this.users.get(uniqueId));
    }

    public void addAll(List<User> users) {
        this.users.putAll(users.stream().collect(Collectors.toMap(User::uniqueId, user -> user)));
    }

    public Collection<User> users() {
        return Collections.unmodifiableCollection(this.users.values());
    }
}
