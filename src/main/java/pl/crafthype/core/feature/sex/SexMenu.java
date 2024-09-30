package pl.crafthype.core.feature.sex;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.notification.NotificationBroadcaster;

import java.util.Map;
import java.util.Objects;

public class SexMenu {

    private final NotificationBroadcaster broadcaster;
    private final SexRepository repository;
    private final MiniMessage miniMessage;
    private final SexConfig config;

    public SexMenu(NotificationBroadcaster broadcaster, SexRepository repository, MiniMessage miniMessage, SexConfig config) {
        this.broadcaster = broadcaster;
        this.repository = repository;
        this.miniMessage = miniMessage;
        this.config = config;
    }

    public void open(Player player) {
        SexConfig.Menu menu = this.config.menu;

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(menu.title))
            .rows(menu.rows)
            .disableAllInteractions()
            .create();

        gui.getFiller().fill(new GuiItem(menu.filler.toItemStack()));

        for (Map.Entry<Integer, ConfigItem> entry : menu.decorations.entrySet()) {
            Integer slot = entry.getKey();
            ConfigItem value = entry.getValue();
            gui.setItem(slot, new GuiItem(value.toItemStack()));
        }

        for (Map.Entry<String, ConfigItem> entry : menu.icons.entrySet()) {
            String sex = entry.getKey();
            ConfigItem item = entry.getValue();
            gui.setItem(item.slot(), new GuiItem(item.toItemStack(), event -> {
                if (Objects.equals(this.repository.findSexSync(player.getUniqueId()), sex)) {
                    return;
                }

                this.repository.updateSex(player.getUniqueId(), sex);

                this.broadcaster.sendAnnounce(player, this.config.choosedSex);

                player.closeInventory();
            }));
        }

        gui.open(player);
    }
}
