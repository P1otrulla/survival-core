package pl.crafthype.core.feature.market.database.expired;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.bukkit.inventory.ItemStack;
import pl.crafthype.core.database.persister.ItemStackPersister;
import pl.crafthype.core.feature.market.MarketItem;

import java.time.Instant;
import java.util.UUID;

@DatabaseTable(tableName = "core_market_items_expired")
public class ExpiredMarketItemWrapper {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(columnName = "item_id", dataType = DataType.UUID)
    private UUID itemId;

    @DatabaseField(columnName = "price", dataType = DataType.DOUBLE)
    private double price;

    @DatabaseField(columnName = "unique_id", dataType = DataType.UUID)
    private UUID uniqueId;

    @DatabaseField(columnName = "seller", dataType = DataType.STRING)
    private String seller;

    @DatabaseField(columnName = "issued_at", dataType = DataType.SERIALIZABLE)
    private Instant issuedAt;

    @DatabaseField(columnName = "item", persisterClass = ItemStackPersister.class)
    private ItemStack itemStack;

    public ExpiredMarketItemWrapper(UUID itemId, ItemStack itemStack, double price, UUID uniqueId, String seller, Instant issuedAt) {
        this.itemId = itemId;
        this.itemStack = itemStack;
        this.price = price;
        this.uniqueId = uniqueId;
        this.seller = seller;
        this.issuedAt = issuedAt;
    }

    public ExpiredMarketItemWrapper() {
    }

    static ExpiredMarketItemWrapper from(MarketItem marketItem) {
        return new ExpiredMarketItemWrapper(marketItem.itemId(), marketItem.itemStack(), marketItem.price(), marketItem.uniqueId(), marketItem.seller(), marketItem.issuedAt());
    }

    public ItemStack itemStack() {
        return this.itemStack;
    }

    public double price() {
        return this.price;
    }

    public UUID uniqueId() {
        return this.uniqueId;
    }

    public String seller() {
        return this.seller;
    }

    public Instant issuedAt() {
        return this.issuedAt;
    }

    MarketItem toMarketItem() {
        return new MarketItem(this.itemId, this.itemStack, this.price, this.uniqueId, this.seller, this.issuedAt);
    }
}
