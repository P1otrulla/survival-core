package pl.crafthype.core.feature.pass;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.crafthype.core.user.User;

@Route(name = "pass", aliases = { "wejściówka", "wejsciowka" })
public class PassCommand {

    private final PassRepository passRepository;
    private final PassMenu passMenu;

    public PassCommand(PassRepository passRepository, PassMenu passMenu) {
        this.passRepository = passRepository;
        this.passMenu = passMenu;
    }

    @Execute(required = 0)
    void open(Player player) {
        this.passMenu.open(player);
    }

    @Execute(required = 2, route = "add")
    @Permission("crafthype.pass.add")
    void add(CommandSender commandSender, @Arg User user, @Arg String pass) {
        this.passRepository.addPass(user.uniqueId(), pass);

        commandSender.sendMessage("done! -> " + user.name() + " -> " + pass);

    }
}
