package pl.crafthype.core.feature.achievement.reward;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;

import java.util.List;

public class CommandReward implements Reward {

    private final Server server;
    private final List<String> commands;

    public CommandReward(Server server, List<String> commands) {
        this.server = server;
        this.commands = commands;
    }

    @Override
    public void giveReward(Player player) {
        Formatter formatter = new Formatter()
            .register("{PLAYER}", player.getName());

        this.commands.forEach(command -> this.server.dispatchCommand(this.server.getConsoleSender(), formatter.format(command)));
    }
}
