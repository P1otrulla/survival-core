package pl.crafthype.core.feature.achievement;

import dev.rollczi.litecommands.command.async.Async;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;
import pl.crafthype.core.feature.achievement.menu.AchievementCategoryMenu;

@Route(name = "achievement", aliases = { "osiagniecia", "os" })
public class AchievementCommand {

    private final AchievementCategoryMenu menu;

    public AchievementCommand(AchievementCategoryMenu menu) {
        this.menu = menu;
    }

    @Execute(required = 0)
    @Async
    void open(Player player) {
        this.menu.open(player);
    }
}
