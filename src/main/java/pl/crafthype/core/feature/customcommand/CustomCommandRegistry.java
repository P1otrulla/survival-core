package pl.crafthype.core.feature.customcommand;

import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import pl.crafthype.core.notification.NotificationBroadcaster;

import java.lang.reflect.Field;

public class CustomCommandRegistry {

    private final NotificationBroadcaster announcer;
    private final Server server;
    private CommandMap commandMap;

    public CustomCommandRegistry(NotificationBroadcaster announcer, Server server) {
        this.announcer = announcer;
        this.server = server;
    }

    public void addCommand(CustomCommand command) {
        WrappedCommand wrappedCommand = new WrappedCommand(command.name(), command.aliases(), this.announcer, command.messages());

        this.commandMap().register("ch-core", wrappedCommand);
    }

    CommandMap commandMap() {
        if (this.commandMap == null) {
            try {
                Field commandMap = this.server.getClass().getDeclaredField("commandMap");
                commandMap.setAccessible(true);

                this.commandMap = (CommandMap) commandMap.get(this.server);
            }
            catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return this.commandMap;
        }

        return this.commandMap;
    }
}
