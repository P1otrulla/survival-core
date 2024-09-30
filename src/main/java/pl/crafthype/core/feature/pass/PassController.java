package pl.crafthype.core.feature.pass;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.crafthype.core.CorePlugin;
import pl.crafthype.core.notification.NotificationBroadcaster;

public class PassController implements Listener {

    private final PassTimeResolver passTimeResolver = new PassTimeResolver();
    private final NotificationBroadcaster broadcaster;
    private final PassRepository passRepository;
    private final PassSettings passSettings;
    private final PassConfig passConfig;
    private final Server server;

    public PassController(NotificationBroadcaster broadcaster, PassRepository passRepository, PassSettings passSettings, PassConfig passConfig, CorePlugin plugin, Server server) {
        this.broadcaster = broadcaster;
        this.passRepository = passRepository;
        this.passSettings = passSettings;
        this.passConfig = passConfig;
        this.server = server;
    }

    @EventHandler
    void checkOfflinePlayers(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String world = player.getWorld().getName();

        if (!this.passTimeResolver.canJoin(player, world)) {
            event.setCancelled(true);

            Location spawnLocation = this.server.getWorld("world").getSpawnLocation();

            player.teleport(spawnLocation);
        }
    }

    @EventHandler
    void onPortal(PlayerPortalEvent event) {
        String world = event.getTo().getWorld().getName();
        Player player = event.getPlayer();

        if (!this.passSettings.blockedWorlds().contains(world)) {
            return;
        }

        if (!this.passTimeResolver.canJoin(player, world)) {
            event.setCancelled(true);

            // TODO: wiadomość świat jest wyłączony w tej godzinie

            return;
        }

        if (this.passRepository.hasPass(player.getUniqueId(), world)) {
            event.setCancelled(true);
            this.passConfig.noAccesTime.forEach(notification -> this.broadcaster.sendAnnounce(player, notification));
            return;
        }

        event.setCancelled(true);
        this.passConfig.noAcces.forEach(notification -> this.broadcaster.sendAnnounce(player, notification));
    }

    @EventHandler
    void onTeleport(PlayerTeleportEvent event) {
        Location location = event.getTo();
        String world = location.getWorld().getName();

        if (!this.passSettings.blockedWorlds().contains(world)) {
            return;
        }

        Player player = event.getPlayer();

        if (!this.passRepository.hasPass(player.getUniqueId(), world)) {
            event.setCancelled(true);

            this.passConfig.noAcces.forEach(notification -> this.broadcaster.sendAnnounce(player, notification));
        }

    }
}
