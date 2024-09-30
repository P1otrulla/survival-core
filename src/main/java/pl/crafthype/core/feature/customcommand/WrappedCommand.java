package pl.crafthype.core.feature.customcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.crafthype.core.notification.Notification;
import pl.crafthype.core.notification.NotificationBroadcaster;

import java.util.List;

public class WrappedCommand extends Command {

    private final NotificationBroadcaster announcer;
    private final List<String> messages;

    protected WrappedCommand(@NotNull String name, List<String> aliases, NotificationBroadcaster announcer, List<String> messages) {
        super(name, name + " command", "", aliases);
        this.announcer = announcer;
        this.messages = messages;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        this.messages.forEach(message -> this.announcer.sendAnnounce(sender, Notification.chat(message)));

        return true;
    }
}
