package pl.crafthype.core.command.implementation;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.root.RootRoute;
import org.bukkit.entity.Player;

@RootRoute
public class DeathBountyCommand {

    @Execute(route = "ofiary")
    void bounties(Player player) {
        player.performCommand("bounties");
    }

    @Execute(route = "ofiara")
    void bonty(Player player) {
        player.performCommand("bounty");
    }

    @Execute(route = "odkup")
    void pay(Player player) {
        player.performCommand("redeembountyheads");
    }
}
