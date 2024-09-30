package pl.crafthype.core.feature.pass;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.money.MoneyService;
import pl.crafthype.core.notification.NotificationBroadcaster;

public class PassMenu {

    private final NotificationBroadcaster broadcaster;
    private final PassRepository passRepository;
    private final MoneyService moneyService;
    private final MiniMessage miniMessage;
    private final PassConfig config;

    public PassMenu(NotificationBroadcaster broadcaster, PassRepository passRepository, MoneyService moneyService, MiniMessage miniMessage, PassConfig config) {
        this.broadcaster = broadcaster;
        this.passRepository = passRepository;
        this.moneyService = moneyService;
        this.miniMessage = miniMessage;
        this.config = config;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(this.config.title))
            .rows(this.config.rows)
            .disableAllInteractions()
            .create();

        gui.getFiller().fill(new GuiItem(this.config.filler.toItemStack()));

        for (ConfigItem item : this.config.passes.values()) {
            if (this.passRepository.hasPass(player.getUniqueId(), this.config.passesByMaterial.get(item.material()))) {
                gui.setItem(item.slot(), new GuiItem(this.config.alreadyHas.toItemStack()));

                continue;
            }

            gui.setItem(item.slot(), new GuiItem(item.toItemStack(), event -> {
                String passName = this.config.passesByMaterial.get(item.material());

                if (this.passRepository.hasPass(player.getUniqueId(), passName)) {
                    return;
                }

                if (this.moneyService.getBalance(player) >= this.config.price) {
                    this.moneyService.withdraw(player, this.config.price);

                    this.passRepository.addPass(player.getUniqueId(), passName);

                    this.broadcaster.sendAnnounce(player, this.config.bought);
                } else {
                    this.broadcaster.sendAnnounce(player, this.config.noMoney);
                }
            }));
        }

        gui.open(player);
    }
}
