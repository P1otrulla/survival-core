package pl.crafthype.core.feature.weding;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "core_weddings")
public class WeddingWrapper {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "player_one", dataType = DataType.UUID)
    private UUID playerOne;

    @DatabaseField(columnName = "player_two", dataType = DataType.UUID)
    private UUID playerTwo;

    public WeddingWrapper(UUID playerOne, UUID playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public WeddingWrapper() {
    }

    public static WeddingWrapper from(Wedding wedding) {
        return new WeddingWrapper(wedding.playerOne(), wedding.playerTwo());
    }

    public UUID playerOne() {
        return this.playerOne;
    }

    public UUID playerTwo() {
        return this.playerTwo;
    }

    Wedding toWedding() {
        return Wedding.create(this.playerOne, this.playerTwo);
    }
}