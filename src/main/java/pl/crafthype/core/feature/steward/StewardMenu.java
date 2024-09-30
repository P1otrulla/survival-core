package pl.crafthype.core.feature.steward;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;
import pl.crafthype.core.shared.DurationUtil;

import java.time.Duration;
import java.time.Instant;

public class StewardMenu {

    private final StewardRepository repository;
    private final MiniMessage miniMessage;
    private final StewardConfig config;
    private final Server server;

    public StewardMenu(StewardRepository repository, MiniMessage miniMessage, StewardConfig config, Server server) {
        this.repository = repository;
        this.miniMessage = miniMessage;
        this.config = config;
        this.server = server;
    }

    public void openInventory(Player player) {
        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(this.config.title))
            .rows(this.config.rows)
            .disableAllInteractions()
            .create();

        gui.getFiller().fill(new GuiItem(Material.GRAY_STAINED_GLASS_PANE));

        for (Steward steward : this.config.stewards.values()) {
            Formatter formatter = new Formatter()
                .register("{NAME}", steward.name())
                .register("{PERMISSION}", steward.permission())
                .register("{TIME}", () -> {
                    Instant now = Instant.now();
                    Instant end = this.repository.findCollectTime(player.getUniqueId(), steward);
                    Duration between = Duration.between(now, end);

                    return DurationUtil.format(between);
                });

            if (!player.hasPermission(steward.permission())) {
                gui.setItem(steward.slot(), new GuiItem(this.config.noPermission.toItemStack(formatter)));

                continue;
            }

            if (this.repository.isCollected(player.getUniqueId(), steward)) {
                gui.setItem(steward.slot(), new GuiItem(this.config.cooldown.toItemStack(formatter)));

                continue;
            }

            GuiItem guiItem = new GuiItem(this.config.avaiable.toItemStack(formatter));

            guiItem.setAction(event -> {
                this.repository.addCollected(player.getUniqueId(), steward);

                for (String command : steward.commands()) {
                    this.server.dispatchCommand(this.server.getConsoleSender(), command.replace("{PLAYER}", player.getName()));
                }

                gui.updateItem(steward.slot(), new GuiItem(this.config.cooldown.toItemStack(formatter)));
            });

            gui.setItem(steward.slot(), guiItem);
        }

        gui.open(player);
    }
}