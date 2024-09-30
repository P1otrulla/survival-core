package pl.crafthype.core.feature.weding;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;
import pl.crafthype.core.money.MoneyService;
import pl.crafthype.core.notification.NotificationBroadcaster;

@Route(name = "divorce", aliases = { "rozwód", "rozwod" })
public class DivorceCommand {

    private final NotificationBroadcaster broadcaster;
    private final WeddingRepository repository;
    private final MoneyService moneyService;
    private final WeddingConfig config;
    private final Server server;

    public DivorceCommand(NotificationBroadcaster broadcaster, WeddingRepository repository, MoneyService moneyService, WeddingConfig config, Server server) {
        this.broadcaster = broadcaster;
        this.repository = repository;
        this.moneyService = moneyService;
        this.config = config;
        this.server = server;
    }

    @Execute
    void execute(Player player) {
        if (this.moneyService.getBalance(player) < this.config.divorceCost) {
            this.broadcaster.sendAnnounce(player, this.config.divorceNoMoney);

            return;
        }

        this.repository.findWedding(player.getUniqueId()).whenComplete((weding, throwable) -> {
            if (weding.isEmpty()) {
                this.broadcaster.sendAnnounce(player, this.config.notWedding);

                return;
            }

            Formatter formatter = new Formatter()
                .register("{PLAYER_ONE}", player.getName())
                .register("{PLAYER_TWO}", () -> {
                    if (player.getUniqueId().equals(weding.playerOne())) {
                        return this.server.getOfflinePlayer(weding.playerTwo()).getName();
                    }

                    return this.server.getOfflinePlayer(weding.playerOne()).getName();
                });

            this.repository.deleteWedding(player.getUniqueId()).whenComplete((aVoid, throwable1) -> {
                this.broadcaster.sendAnnounce(player, this.config.divorced, formatter);
                this.broadcaster.sendAnnounceAll(this.config.divorcedBroadcast, formatter);

                this.moneyService.withdraw(player, this.config.divorceCost);
            });
        });
    }
}
