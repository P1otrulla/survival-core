package pl.crafthype.core.feature.statistic;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "core_statistics")
public class StatisticEntryWrapper {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(columnName = "user_id", dataType = DataType.UUID)
    private UUID owner;

    @DatabaseField(columnName = "statistic", dataType = DataType.STRING)
    private String statistic;

    @DatabaseField(columnName = "value", dataType = DataType.LONG)
    private long value;

    public StatisticEntryWrapper() {
    }

    public StatisticEntryWrapper(UUID owner, StatisticEntry entry) {
        this.owner = owner;
        this.statistic = entry.statistic().name();
        this.value = entry.value();
    }

    public StatisticEntryWrapper(long id, UUID owner, StatisticEntry entry) {
        this.id = id;
        this.owner = owner;
        this.statistic = entry.statistic().name();
        this.value = entry.value();
    }

    public static StatisticEntryWrapper from(UUID uniqueId, StatisticEntry entry) {
        return new StatisticEntryWrapper(uniqueId, entry);
    }

    public long id() {
        return this.id;
    }

    public UUID owner() {
        return this.owner;
    }

    public String statistic() {
        return this.statistic;
    }

    public long value() {
        return this.value;
    }

    public void addValue(long value) {
        this.value += value;
    }

    StatisticEntry toStatisticEntry() {
        return new StatisticEntry(this.owner, Statistic.valueOf(this.statistic), this.value);
    }
}
