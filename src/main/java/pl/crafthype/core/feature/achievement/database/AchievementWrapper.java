package pl.crafthype.core.feature.achievement.database;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "core_gained-achievements")
public class AchievementWrapper {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(columnName = "user_id", dataType = DataType.UUID)
    private UUID uniqueId;

    @DatabaseField(columnName = "achievement_id", dataType = DataType.STRING)
    private String achievementId;

    public AchievementWrapper(UUID uniqueId, String achievementId) {
        this.uniqueId = uniqueId;
        this.achievementId = achievementId;
    }

    public AchievementWrapper() {

    }

    public static AchievementWrapper from(UUID uniqueId, String achievementId) {
        return new AchievementWrapper(uniqueId, achievementId);
    }

    public UUID uniqueId() {
        return this.uniqueId;
    }

    public String achievementId() {
        return this.achievementId;
    }
}
