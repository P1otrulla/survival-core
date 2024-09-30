package pl.crafthype.core.shared;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class ItemUtil {

    private ItemUtil() {
    }

    public static void giveItem(HumanEntity humanEntity, ItemStack itemStack) {
        if (hasSpace(humanEntity.getInventory(), itemStack)) {
            humanEntity.getInventory().addItem(itemStack);

            return;
        }

        humanEntity.getLocation().getWorld().dropItemNaturally(humanEntity.getLocation(), itemStack);
    }

    private static boolean hasSpace(Inventory inventory, ItemStack itemStack) {
        if (inventory.firstEmpty() != -1) {
            return true;
        }

        for (ItemStack itemInv : inventory.getContents()) {
            if (itemInv == null) {
                continue;
            }

            if (!itemInv.isSimilar(itemStack)) {
                continue;
            }

            if (itemInv.getMaxStackSize() > itemInv.getAmount()) {
                return true;
            }
        }

        return false;
    }

    public static String encode(ItemStack item) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("i", item);
        return config.saveToString();
    }

    public static ItemStack decode(String itemData) {
        try {
            YamlConfiguration config = new YamlConfiguration();
            config.loadFromString(itemData);
            return config.getItemStack("i");
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}