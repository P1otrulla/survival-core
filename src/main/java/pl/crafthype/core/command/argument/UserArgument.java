package pl.crafthype.core.command.argument;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Result;
import pl.crafthype.core.config.implementation.MessagesConfig;
import pl.crafthype.core.user.User;
import pl.crafthype.core.user.UserService;

import java.util.List;
import java.util.Optional;

@ArgumentName("user")
public class UserArgument implements OneArgument<User> {

    private final UserService userService;
    private final MessagesConfig messages;
    private final Server server;

    public UserArgument(UserService userService, MessagesConfig messages, Server server) {
        this.userService = userService;
        this.messages = messages;
        this.server = server;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Result<User, ?> parse(LiteInvocation invocation, String argument) {
        OfflinePlayer player = this.server.getOfflinePlayer(argument);

        if (player == null) {
            return Result.error(this.messages.playerNotFound);
        }

        Optional<User> optionalUser = this.userService.find(player.getUniqueId());

        if (optionalUser.isEmpty()) {
            return Result.error(this.messages.playerNotFound);
        }

        return Result.ok(optionalUser.get());
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.server.getOnlinePlayers()
            .stream()
            .map(Player::getName)
            .map(Suggestion::of)
            .toList();
    }
}
