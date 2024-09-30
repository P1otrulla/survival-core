package pl.crafthype.core.feature.market;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import pl.crafthype.core.notification.NotificationBroadcaster;

import java.time.Instant;
import java.util.ArrayList;

public class MarketExpireItemTask implements Runnable {

    private final NotificationBroadcaster broadcaster;
    private final MarketExpiredService expiredService;
    private final MarketService service;
    private final MarketConfig config;
    private final Server server;

    public MarketExpireItemTask(NotificationBroadcaster broadcaster, MarketExpiredService expiredService, MarketService service, MarketConfig config, Server server) {
        this.broadcaster = broadcaster;
        this.expiredService = expiredService;
        this.service = service;
        this.config = config;
        this.server = server;
    }

    @Override
    public void run() {
        for (MarketItem marketItem : new ArrayList<>(this.service.items())) {
            Instant now = Instant.now();
            Instant issuedAt = marketItem.issuedAt();
            Instant expireAt = issuedAt.plus(this.config.itemExpire);

            if (now.isAfter(expireAt)) {
                this.expiredService.addItem(marketItem);

                this.service.removeItem(marketItem);

                Player player = this.server.getPlayer(marketItem.uniqueId());

                if (player != null) {
                    this.broadcaster.sendAnnounce(player, this.config.itemExpired);
                }
            }
        }
    }
}
