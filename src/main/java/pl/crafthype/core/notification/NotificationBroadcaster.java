package pl.crafthype.core.notification;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;
import pl.crafthype.core.user.User;

import java.time.Duration;

public class NotificationBroadcaster {

    private static final Formatter EMPTY_FORMATTER = new Formatter();

    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;
    private final Server server;
    private Title.Times titleTimes;

    public NotificationBroadcaster(AudienceProvider audienceProvider, MiniMessage miniMessage, Server server) {
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
        this.server = server;
    }

    public void sendAnnounce(Object sender, Notification notification) {
        this.sendAnnounce(sender, notification, EMPTY_FORMATTER);
    }

    public void sendAnnounce(Object sender, Notification notification, Formatter formatter) {
        Audience audience = this.toAudience(sender);

        String message = formatter.format(notification.message());

        for (NotificationType notificationType : notification.types()) {
            switch (notificationType) {
                case TITLE -> {
                    audience.sendTitlePart(TitlePart.TITLE, this.miniMessage.deserialize(message));
                    audience.sendTitlePart(TitlePart.TIMES, this.times());

                }
                case SUBTITLE -> {
                    audience.sendTitlePart(TitlePart.SUBTITLE, this.miniMessage.deserialize(message));
                    audience.sendTitlePart(TitlePart.TIMES, this.times());
                }
                case ACTIONBAR -> audience.sendActionBar(this.miniMessage.deserialize(message));
                case CHAT -> audience.sendMessage(this.miniMessage.deserialize(message));
                case NONE -> {
                }
            }
        }
    }

    public void sendAnnounceAll(Notification notification, Formatter formatter) {
        this.server.getOnlinePlayers().forEach(player -> this.sendAnnounce(player, notification, formatter));
    }

    private Audience toAudience(Object sender) {
        if (sender instanceof Player player) {
            return this.audienceProvider.player(player.getUniqueId());
        }

        if (sender instanceof OfflinePlayer offlinePlayer) {
            return this.audienceProvider.player(offlinePlayer.getUniqueId());
        }

        if (sender instanceof User user) {
            return this.audienceProvider.player(user.uniqueId());
        }

        return this.audienceProvider.console();
    }

    private Title.Times times() {
        if (this.titleTimes == null) {
            this.titleTimes = Title.Times.times(
                Duration.ofSeconds(1),
                Duration.ofSeconds(2),
                Duration.ofSeconds(1)
            );
        }

        return this.titleTimes;
    }
}
