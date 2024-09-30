package pl.crafthype.core.user;

import java.util.UUID;

public class User {

    private final UUID uniqueId;
    private String name;

    public User(UUID uniqueId) {
        this(uniqueId, null);
    }

    public User(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public UUID uniqueId() {
        return this.uniqueId;
    }

    public String name() {
        return this.name;
    }

    void updateName(String name) {
        this.name = name;
    }
}
