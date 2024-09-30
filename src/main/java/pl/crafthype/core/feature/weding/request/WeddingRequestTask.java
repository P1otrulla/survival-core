package pl.crafthype.core.feature.weding.request;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import pl.crafthype.core.feature.weding.WeddingConfig;
import pl.crafthype.core.notification.NotificationBroadcaster;

import java.time.Instant;

public class WeddingRequestTask implements Runnable {

    private final WeddingRequestService weddingRequestService;
    private final NotificationBroadcaster broadcaster;
    private final WeddingConfig config;
    private final Server server;

    public WeddingRequestTask(WeddingRequestService weddingRequestService, NotificationBroadcaster broadcaster, WeddingConfig config, Server server) {
        this.weddingRequestService = weddingRequestService;
        this.broadcaster = broadcaster;
        this.config = config;
        this.server = server;
    }

    @Override
    public void run() {
        for (WeddingRequest weddingRequest : this.weddingRequestService.getRequests()) {
            Instant now = Instant.now();

            if (weddingRequest.expirationDate().isBefore(now)) {
                this.weddingRequestService.removeRequest(weddingRequest.sender());

                Player playerOne = this.server.getPlayer(weddingRequest.sender());
                Player playerTwo = this.server.getPlayer(weddingRequest.receiver());

                if (playerOne != null) {
                    this.broadcaster.sendAnnounce(playerOne, this.config.requestExpiredSender);
                }

                if (playerTwo != null) {
                    this.broadcaster.sendAnnounce(playerTwo, this.config.requestExpiredReceiver);
                }
            }
        }
    }
}
