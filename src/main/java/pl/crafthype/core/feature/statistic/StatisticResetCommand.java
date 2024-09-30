package pl.crafthype.core.feature.statistic;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import pl.crafthype.core.user.User;

@Route(name = "statistic-reset")
@Permission("crafthype.statistic-reset")
public class StatisticResetCommand {

    private final StatisticRegistry registry;

    public StatisticResetCommand(StatisticRegistry registry) {
        this.registry = registry;
    }

    @Execute(required = 1)
    void reset(CommandSender sender, @Arg User user) {
        this.registry.resetStatistic(user.uniqueId());

        sender.sendMessage("Statistics reseted!");
    }
}
