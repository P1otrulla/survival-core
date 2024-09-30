package pl.crafthype.core.controller;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.crafthype.core.shared.RegionUtil;

public class AutoLapisController implements Listener {

    private static final String SPAWN_REGION = "spawn";
    private final ItemStack lapis;

    public AutoLapisController(ItemStack lapis) {
        this.lapis = lapis;
    }

    @EventHandler
    void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (!RegionUtil.isInRegion(player, SPAWN_REGION)) {
            return;
        }

        Inventory inventory = event.getInventory();

        if (!(inventory.getType() == InventoryType.ENCHANTING)) {
            return;
        }

        inventory.setItem(1, lapis.clone());
    }

    @EventHandler
    void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (!RegionUtil.isInRegion(player, SPAWN_REGION)) {
            return;
        }

        Inventory inventory = event.getInventory();

        if (inventory == null) {
            return;
        }

        if (!(inventory.getType() == InventoryType.ENCHANTING)) {
            return;
        }

        inventory.setItem(1, null);
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!RegionUtil.isInRegion(player, SPAWN_REGION)) {
            return;
        }

        Inventory inventory = event.getClickedInventory();

        if (inventory == null) {
            return;
        }

        if (!(inventory.getType() == InventoryType.ENCHANTING)) {
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        if (currentItem.getType() == Material.LAPIS_LAZULI) {
            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler
    void onEnchantItem(EnchantItemEvent event) {
        Inventory inventory = event.getInventory();
        Player player = event.getEnchanter();

        if (!RegionUtil.isInRegion(player, SPAWN_REGION)) {
            return;
        }

        if (inventory.getType() != InventoryType.ENCHANTING) {
            return;
        }

        inventory.setItem(1, null);
        inventory.setItem(1, lapis.clone());
        player.updateInventory();
    }
}
