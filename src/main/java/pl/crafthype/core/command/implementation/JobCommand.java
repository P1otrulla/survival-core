package pl.crafthype.core.command.implementation;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "praca", aliases = "prace")
public class JobCommand {

    @Execute
    void execute(Player player) {
        player.performCommand("jobs");
    }
}
