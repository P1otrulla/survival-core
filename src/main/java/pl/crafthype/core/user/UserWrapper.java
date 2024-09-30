package pl.crafthype.core.user;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "core_user")
public class UserWrapper {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(columnName = "unique_id", dataType = DataType.UUID)
    private UUID uniqueId;

    @DatabaseField(columnName = "name", dataType = DataType.STRING)
    private String name;

    public UserWrapper() {
    }

    public UserWrapper(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public static UserWrapper from(User user) {
        return new UserWrapper(user.uniqueId(), user.name());
    }

    public UUID uniqueId() {
        return this.uniqueId;
    }

    public String name() {
        return this.name;
    }

    User toUser() {
        return new User(this.uniqueId, this.name);
    }
}