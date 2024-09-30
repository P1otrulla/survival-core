package pl.crafthype.core.command.implementation;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "klucz", aliases = "key")
@Permission("crafthype.key")
public class KeyGiveCommand {


    @Execute(min = 2)
    void execute(@Name("nick") Player player, @Arg @Name("rodzaj") String keyName, @Arg @Name("ilość") int amount) {
        player.performCommand("ecocreate give " + player.getName() + keyName + " psychic " + amount);
    }
}
