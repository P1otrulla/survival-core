package pl.crafthype.core.command.contextual;

import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Result;
import pl.crafthype.core.config.implementation.MessagesConfig;
import pl.crafthype.core.user.User;
import pl.crafthype.core.user.UserService;

public class UserContextual implements Contextual<CommandSender, User> {

    private final MessagesConfig messages;
    private final UserService userService;

    public UserContextual(MessagesConfig messages, UserService userService) {
        this.messages = messages;
        this.userService = userService;
    }

    @Override
    public Result<User, ?> extract(CommandSender commandSender, Invocation<CommandSender> invocation) {
        if (commandSender instanceof Player player) {
            return Result.ok(this.userService.findOrCreate(player.getUniqueId()));
        }

        return Result.error(this.messages.playerNotFound);
    }
}
