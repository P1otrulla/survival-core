package pl.crafthype.core.feature.sex;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "sex", aliases = { "płeć", "plec" })
public class SexCommand {

    private final SexMenu sexMenu;

    public SexCommand(SexMenu sexMenu) {
        this.sexMenu = sexMenu;
    }

    @Execute
    void execute(Player player) {
        this.sexMenu.open(player);
    }
}
