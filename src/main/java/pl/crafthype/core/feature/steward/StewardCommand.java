package pl.crafthype.core.feature.steward;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "steward", aliases = { "klucznik", "klucze" })
public class StewardCommand {

    private final StewardMenu stewardMenu;

    public StewardCommand(StewardMenu stewardMenu) {
        this.stewardMenu = stewardMenu;
    }

    @Execute
    void execute(Player player) {
        this.stewardMenu.openInventory(player);
    }
}
