package pl.crafthype.core.config.item;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.builder.item.SkullBuilder;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import panda.utilities.text.Formatter;
import pl.crafthype.core.shared.legacy.LegacyColorProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.crafthype.core.shared.AdventureUtil.reset;

@Contextual
public class ConfigItem {

    @Exclude
    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
        .postProcessor(new LegacyColorProcessor())
        .build();
    @Exclude
    private static final Formatter EMPTY_FORMATTER = new Formatter();

    private String name;
    private List<String> lore = new ArrayList<>();

    private Material material = Material.DIRT;

    private int amount = 1;
    private int slot = 0;

    private String skullTexture;

    private List<ItemFlag> flags = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();

    public static Builder builder() {
        return new Builder();
    }

    public String name() {
        return this.name;
    }

    public List<String> lore() {
        return this.lore;
    }

    public void updateLore(List<String> lore) {
        this.lore = lore;
    }

    public Material material() {
        return this.material;
    }

    public int amount() {
        return this.amount;
    }

    public int slot() {
        return this.slot;
    }

    public void glow(boolean glow) {
        if (glow) {
            this.enchantments.put(Enchantment.DURABILITY, 1);
            this.flags.add(ItemFlag.HIDE_ENCHANTS);

            return;
        }

        this.enchantments.remove(Enchantment.DURABILITY);
        this.flags.remove(ItemFlag.HIDE_ENCHANTS);
    }

    public List<ItemFlag> flags() {
        return this.flags;
    }

    public Map<Enchantment, Integer> enchantments() {
        return this.enchantments;
    }

    public ItemStack toItemStack() {
        return this.toItemStack(EMPTY_FORMATTER);
    }

    public ItemStack toItemStack(Formatter formatter) {
        ItemBuilder itemBuilder = ItemBuilder.from(this.material);

        if (this.name != null) {
            itemBuilder.name(reset(MINI_MESSAGE.deserialize(formatter.format(this.name))));
        }

        if (this.lore != null) {
            itemBuilder.lore(this.lore.stream()
                .map(component -> reset(MINI_MESSAGE.deserialize(formatter.format(component))))
                .collect(Collectors.toList()));
        }

        if (!this.flags.isEmpty()) {
            itemBuilder.flags(this.flags.toArray(new ItemFlag[0]));
        }

        if (!this.enchantments.isEmpty()) {
            itemBuilder.enchant(this.enchantments);
        }

        itemBuilder.amount(this.amount);

        if (this.skullTexture != null && this.material == Material.PLAYER_HEAD) {
            ItemStack itemStack = itemBuilder.build();

            SkullBuilder builder = ItemBuilder.skull(itemStack).texture(this.skullTexture);

            return builder.build();
        }

        return itemBuilder.build();
    }

    public static class Builder {

        private final ConfigItem itemElement;

        public Builder() {
            this.itemElement = new ConfigItem();
        }

        public ConfigItem.Builder name(String name) {
            this.itemElement.name = name;

            return this;
        }

        public ConfigItem.Builder lore(List<String> lore) {
            this.itemElement.lore = lore;

            return this;
        }

        public ConfigItem.Builder lore(String... lore) {
            this.itemElement.lore = List.of(lore);

            return this;
        }

        public ConfigItem.Builder material(Material material) {
            this.itemElement.material = material;

            return this;
        }

        public ConfigItem.Builder amount(int amount) {
            this.itemElement.amount = amount;

            return this;
        }

        public ConfigItem.Builder slot(int slot) {
            this.itemElement.slot = slot;

            return this;
        }

        public ConfigItem.Builder flags(List<ItemFlag> flags) {
            this.itemElement.flags = flags;

            return this;
        }

        public ConfigItem.Builder flag(ItemFlag flag) {
            this.itemElement.flags.add(flag);

            return this;
        }

        public ConfigItem.Builder enchantments(Map<Enchantment, Integer> enchantments) {
            this.itemElement.enchantments = enchantments;

            return this;
        }

        public ConfigItem.Builder enchantment(Enchantment enchantment, int level) {
            this.itemElement.enchantments.put(enchantment, level);

            return this;
        }

        public ConfigItem.Builder glow(boolean glow) {
            this.itemElement.glow(glow);

            return this;
        }

        public ConfigItem.Builder texture(String skullTexture) {
            if (this.itemElement.material != Material.PLAYER_HEAD) {
                return this;
            }

            this.itemElement.skullTexture = skullTexture;

            return this;
        }

        public ConfigItem build() {
            return this.itemElement;
        }
    }
}