package pl.crafthype.core.feature.rankjoinquit;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import panda.utilities.text.Formatter;
import pl.crafthype.core.notification.NotificationBroadcaster;

public class RankJoinQuitController implements Listener {

    private final LuckPerms luckPerms;
    private final RankJoinQuitConfig rankJoinQuitConfig;
    private final NotificationBroadcaster broadcaster;

    public RankJoinQuitController(RankJoinQuitConfig rankJoinQuitConfig, NotificationBroadcaster broadcaster) {
        this.rankJoinQuitConfig = rankJoinQuitConfig;
        this.broadcaster = broadcaster;

        this.luckPerms = Bukkit.getServicesManager().load(LuckPerms.class);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        Player player = event.getPlayer();

        CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(event.getPlayer());
        String primaryGroup = metaData.getPrimaryGroup();

        if (this.rankJoinQuitConfig.ranksJoin.containsKey(primaryGroup)) {
            Formatter formatter = new Formatter()
                .register("{PLAYER}", playerName);


            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            this.broadcaster.sendAnnounceAll(rankJoinQuitConfig.ranksJoin.get(primaryGroup), formatter);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();

        CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(event.getPlayer());
        String primaryGroup = metaData.getPrimaryGroup();

        if (this.rankJoinQuitConfig.ranksQuit.containsKey(primaryGroup)) {
            Formatter formatter = new Formatter()
                .register("{PLAYER}", playerName);

            this.broadcaster.sendAnnounceAll(this.rankJoinQuitConfig.ranksQuit.get(primaryGroup), formatter);
        }
    }

}