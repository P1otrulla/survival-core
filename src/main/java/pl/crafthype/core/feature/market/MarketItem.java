package pl.crafthype.core.feature.market;

import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class MarketItem {

    private final UUID itemId;
    private final ItemStack itemStack;
    private final double price;
    private final UUID uniqueId;
    private final String seller;
    private final Instant issuedAt;

    public MarketItem(UUID itemId, ItemStack itemStack, double price, UUID uniqueId, String seller, Instant issuedAt) {
        this.itemId = itemId;
        this.itemStack = itemStack;
        this.price = price;
        this.uniqueId = uniqueId;
        this.seller = seller;
        this.issuedAt = issuedAt;
    }

    public UUID itemId() {
        return this.itemId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        MarketItem that = (MarketItem) o;
        return Double.compare(that.price, this.price) == 0
            && Objects.equals(this.itemStack, that.itemStack)
            && Objects.equals(this.uniqueId, that.uniqueId)
            && Objects.equals(this.seller, that.seller)
            && Objects.equals(this.issuedAt, that.issuedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.itemStack, this.price, this.uniqueId, this.seller, this.issuedAt);
    }
}
