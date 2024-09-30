package pl.crafthype.core.feature.weding;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;
import pl.crafthype.core.feature.weding.request.WeddingRequest;
import pl.crafthype.core.feature.weding.request.WeddingRequestService;
import pl.crafthype.core.money.MoneyService;
import pl.crafthype.core.notification.NotificationBroadcaster;

@Route(name = "weding", aliases = { "ślub", "slub" })
public class WeddingCommand {

    private final NotificationBroadcaster broadcaster;
    private final WeddingRequestService requestService;
    private final WeddingRepository repository;
    private final MoneyService moneyService;
    private final WeddingConfig config;

    public WeddingCommand(NotificationBroadcaster broadcaster, WeddingRequestService requestService, WeddingRepository repository, MoneyService moneyService, WeddingConfig config) {
        this.broadcaster = broadcaster;
        this.requestService = requestService;
        this.repository = repository;
        this.moneyService = moneyService;
        this.config = config;
    }

    @Execute(required = 1)
    void execute(Player player, @Arg @Name("gracz") Player target) {
        if (this.moneyService.getBalance(player) < this.config.wedingCost) {
            this.broadcaster.sendAnnounce(player, this.config.wedingNoCostMessage);

            return;
        }

        if (player.getUniqueId().equals(target.getUniqueId())) {
            this.broadcaster.sendAnnounce(player, this.config.noSelf);

            return;
        }

        this.repository.findWedding(player.getUniqueId()).whenComplete((weding, throwable) -> {
            if (!weding.isEmpty()) {
                this.broadcaster.sendAnnounce(player, this.config.alreadyWedding);

                return;
            }

            this.repository.findWedding(target.getUniqueId()).whenComplete((weding1, throwable1) -> {
                if (!weding1.isEmpty()) {
                    this.broadcaster.sendAnnounce(player, this.config.playerAlreadyWedding);

                    return;
                }

                if (this.requestService.existsRequestBetween(player.getUniqueId(), target.getUniqueId())) {
                    this.broadcaster.sendAnnounce(player, this.config.weddingBetweenPlayers);

                    return;
                }

                WeddingRequest request = WeddingRequest.create(player.getUniqueId(), target.getUniqueId());
                this.requestService.addRequest(request);

                this.broadcaster.sendAnnounce(player, this.config.sendWeding, new Formatter().register("{PLAYER}", target.getName()));
                this.broadcaster.sendAnnounce(target, this.config.receivedWeding, new Formatter().register("{PLAYER}", player.getName()));
            });
        });
    }

    @Execute(route = "akceptuj", aliases = "accept", required = 1)
    void accept(Player player, @Arg @Name("gracz") Player target) {
        WeddingRequest request = this.requestService.findRequest(player.getUniqueId());

        if (request == null) {
            this.broadcaster.sendAnnounce(player, this.config.noRequest);

            return;
        }

        if (!request.sender().equals(target.getUniqueId())) {
            this.broadcaster.sendAnnounce(player, this.config.noRequest);

            return;
        }

        Wedding wedding = Wedding.create(request.sender(), request.receiver());

        this.repository.createWedding(wedding).whenComplete((aVoid, throwable) -> {
            this.requestService.removeRequest(request.sender());

            this.moneyService.withdraw(player, this.config.wedingCost);

            this.broadcaster.sendAnnounce(player, this.config.youAcceptWeding, new Formatter().register("{PLAYER}", target.getName()));
            this.broadcaster.sendAnnounce(target, this.config.acceptedWeding, new Formatter().register("{PLAYER}", player.getName()));
            this.broadcaster.sendAnnounceAll(this.config.wedingBroadcast, new Formatter()
                .register("{PLAYER_ONE}", player.getName())
                .register("{PLAYER_TWO}", target.getName()));
        });
    }


}
