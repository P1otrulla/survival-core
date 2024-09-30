package pl.crafthype.core;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.crafthype.core.config.ConfigService;
import pl.crafthype.core.notification.Notification;
import pl.crafthype.core.notification.NotificationBroadcaster;
import pl.crafthype.core.shared.InformationBookService;

@Route(name = "core", aliases = "chCore")
@Permission("crafthype.core")
public final class CoreCommand {

    private final InformationBookService bookService;
    private final NotificationBroadcaster announcer;
    private final ConfigService configService;

    public CoreCommand(InformationBookService bookService, NotificationBroadcaster announcer, ConfigService configService) {
        this.bookService = bookService;
        this.announcer = announcer;
        this.configService = configService;
    }

    @Execute(route = "reload")
    void reload(CommandSender sender) {
        this.configService.reload();

        this.announcer.sendAnnounce(sender, Notification.chat("&aPomyślnie przeładowano konfigurację!"));
    }

    @Execute(route = "book")
    void informBook(Player player) {
        this.bookService.openBook(player);
    }
}
