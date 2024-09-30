package pl.crafthype.core.command.implementation;

import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pl.crafthype.core.CorePlugin;
import pl.crafthype.core.config.implementation.MessagesConfig;
import pl.crafthype.core.notification.Legacy;
import pl.crafthype.core.notification.NotificationBroadcaster;

import java.util.UUID;

@Route(name = "autograph", aliases = "podpis")
@Permission("crafthype.podpis")
public class AutographCommand {


    private final NotificationBroadcaster announcer;
    private final NamespacedKey autographKey;
    private final MessagesConfig messages;
    private final MiniMessage miniMessage;

    private final CorePlugin plugin;

    public AutographCommand(NotificationBroadcaster announcer, MessagesConfig messages, MiniMessage miniMessage, CorePlugin plugin) {
        this.announcer = announcer;
        this.messages = messages;
        this.plugin = plugin;

        this.autographKey = new NamespacedKey(this.plugin, "uniqueAutograph");
        this.miniMessage = miniMessage;
    }


    @Execute(min = 1)
    void execute(Player player, @Joiner String autographName) {
        ItemStack itemInUse = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInUse.getItemMeta();
        UUID playerUuid = player.getUniqueId();

        if (itemMeta == null) {
            this.announcer.sendAnnounce(playerUuid, this.messages.autographAirMessage);
            return;
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if (container.has(this.autographKey, PersistentDataType.BYTE)) {
            this.announcer.sendAnnounce(playerUuid, this.messages.autographAlreadySigned);
            return;
        }

        this.addAutograph(player, autographName);
        this.announcer.sendAnnounce(playerUuid, this.messages.autographSuccessMessage);
    }

    void addAutograph(Player player, String autographName) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack itemInUse = playerInventory.getItemInMainHand();
        int heldItemSlot = playerInventory.getHeldItemSlot();

        ItemStack itemBuilder = ItemBuilder.from(itemInUse)
            .name(Legacy.RESET_ITALIC.append(this.miniMessage.deserialize(autographName)))
            .glow(true)
            .build();

        ItemMeta meta = itemBuilder.getItemMeta();
        PersistentDataContainer autographContainer = meta.getPersistentDataContainer();
        autographContainer.set(this.autographKey, PersistentDataType.BYTE, (byte) 1);
        itemBuilder.setItemMeta(meta);

        playerInventory.remove(itemInUse);
        playerInventory.setItem(heldItemSlot, itemBuilder);
    }
}
