package pl.crafthype.core.command.implementation;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.async.Async;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.crafthype.core.config.implementation.MessagesConfig;
import pl.crafthype.core.notification.Notification;
import pl.crafthype.core.notification.NotificationBroadcaster;
import pl.crafthype.core.shared.legacy.Legacy;

@Route(name = "nick")
public class NickCommand {

    private static final TagResolver[] RESOLVERS = new TagResolver[]{
        TagResolver.standard(),
        StandardTags.gradient(),
        StandardTags.rainbow()
    };

    private final NotificationBroadcaster broadcaster;
    private final MessagesConfig messages;
    private final MiniMessage miniMessage;

    public NickCommand(NotificationBroadcaster broadcaster, MessagesConfig messages, MiniMessage miniMessage) {
        this.broadcaster = broadcaster;
        this.messages = messages;
        this.miniMessage = miniMessage;
    }

    @Execute(required = 0)
    @Async
    void execute(Player player) {
        this.messages.nickNameInfo.forEach(notification -> this.broadcaster.sendAnnounce(player, notification));
    }

    @Execute(route = "ustaw", required = 1)
    @Permission("crafthype.nick")
    @Async
    void execute(Player player, @Arg String nick) {
        String colored = Legacy.AMPERSAND_SERIALIZER.serialize(this.miniMessage.deserialize(nick, RESOLVERS));
        String striped = this.miniMessage.stripTags(nick);

        if (!striped.equalsIgnoreCase(player.getName())) {
            this.broadcaster.sendAnnounce(player, Notification.chat("&cNick musi być taki sam jak twoja nazwa!"));

            return;
        }

        player.setDisplayName(colored);

        this.broadcaster.sendAnnounce(player, Notification.chat("&aZmieniono nick na " + nick));
    }

    @Execute(route = "usun", required = 0)
    @Permission("crafthype.nick")
    @Async
    void clear(Player player) {
        player.setDisplayName(player.getName());

        this.broadcaster.sendAnnounce(player, Notification.chat("&cUsunieto nick!"));
    }

    @Execute(route = "clear", required = 1)
    @Permission("crafthype.nick.clear")
    @Async
    void clear(CommandSender sender, @Arg Player player) {
        player.setDisplayName(player.getName());

        sender.sendMessage("Usunieto nick gracza " + player.getName());
    }
}
