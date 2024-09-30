package pl.crafthype.core.command;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Formatter;
import pl.crafthype.core.config.implementation.MessagesConfig;
import pl.crafthype.core.notification.NotificationBroadcaster;

public class InvalidUsageHandler implements Handler<CommandSender, Schematic> {

    private final NotificationBroadcaster announcer;
    private final MessagesConfig messages;

    public InvalidUsageHandler(NotificationBroadcaster announcer, MessagesConfig messages) {
        this.announcer = announcer;
        this.messages = messages;
    }

    @Override
    public void handle(CommandSender commandSender, LiteInvocation invocation, Schematic schematic) {
        if (schematic.isOnlyFirst()) {
            this.announcer.sendAnnounce(commandSender, this.messages.correctUsage, new Formatter().register("{USAGE}", schematic.first()));

            return;
        }

        this.announcer.sendAnnounce(commandSender, this.messages.correctUsageHeader);

        schematic.getSchematics().forEach(usage -> this.announcer.sendAnnounce(commandSender, this.messages.correctUsageEntry, new Formatter().register("{USAGE}", usage)));
    }
}
