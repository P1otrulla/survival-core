package pl.crafthype.core.feature.top;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "top", aliases = "topki")
public class TopCommand {

    private final TopMenu menu;

    public TopCommand(TopMenu menu) {
        this.menu = menu;
    }

    @Execute(required = 0)
    void execute(Player player) {
        this.menu.open(player);
    }
}
