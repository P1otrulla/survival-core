package pl.crafthype.core.feature.sex;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "core_sex")
public class SexWrapper {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(columnName = "unique_id")
    private UUID uniqueId;

    @DatabaseField(columnName = "sex")
    private String sex;

    public SexWrapper() {
    }

    public SexWrapper(UUID uniqueId, String sex) {
        this.uniqueId = uniqueId;
        this.sex = sex;
    }

    static SexWrapper from(UUID uniqueId, String sex) {
        return new SexWrapper(uniqueId, sex);
    }

    public UUID uniqueId() {
        return this.uniqueId;
    }

    public String sex() {
        return this.sex;
    }

    public void updateSex(String sex) {
        this.sex = sex;
    }
}
