package pl.crafthype.core.command;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import org.bukkit.command.CommandSender;
import pl.crafthype.core.notification.Notification;
import pl.crafthype.core.notification.NotificationBroadcaster;

public class NotificationHandler implements Handler<CommandSender, Notification> {

    private final NotificationBroadcaster notificationBroadcaster;

    public NotificationHandler(NotificationBroadcaster notificationBroadcaster) {
        this.notificationBroadcaster = notificationBroadcaster;
    }

    @Override
    public void handle(CommandSender commandSender, LiteInvocation invocation, Notification value) {
        this.notificationBroadcaster.sendAnnounce(commandSender, value);
    }
}
