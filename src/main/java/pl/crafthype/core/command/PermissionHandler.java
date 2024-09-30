package pl.crafthype.core.command;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.Handler;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Formatter;
import panda.utilities.text.Joiner;
import pl.crafthype.core.config.implementation.MessagesConfig;
import pl.crafthype.core.notification.NotificationBroadcaster;

public class PermissionHandler implements Handler<CommandSender, RequiredPermissions> {

    private final NotificationBroadcaster announcer;
    private final MessagesConfig messages;

    public PermissionHandler(NotificationBroadcaster announcer, MessagesConfig messages) {
        this.announcer = announcer;
        this.messages = messages;
    }

    @Override
    public void handle(CommandSender commandSender, LiteInvocation invocation, RequiredPermissions permissions) {
        Formatter formatter = new Formatter()
            .register("{PERMISSIONS}", Joiner.on(", ")
                .join(permissions.getPermissions())
                .toString());

        this.announcer.sendAnnounce(commandSender, this.messages.noPermission, formatter);
    }
}
