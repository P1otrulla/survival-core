package pl.crafthype.core.feature.achievement.menu;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.crafthype.core.feature.achievement.AchievementCategory;
import pl.crafthype.core.feature.achievement.AchievementService;
import pl.crafthype.core.feature.achievement.AchievementSettings;
import pl.crafthype.core.scheduler.Scheduler;

public class AchievementCategoryMenu {

    private final AchievementMenu achievementMenu;
    private final AchievementSettings settings;
    private final AchievementService service;
    private final MiniMessage miniMessage;
    private final Scheduler scheduler;

    public AchievementCategoryMenu(AchievementMenu achievementMenu, AchievementSettings settings, AchievementService service, MiniMessage miniMessage, Scheduler scheduler) {
        this.achievementMenu = achievementMenu;
        this.settings = settings;
        this.service = service;
        this.miniMessage = miniMessage;
        this.scheduler = scheduler;
    }

    public void open(Player player) {
        this.scheduler.async(() -> {
            Gui gui = Gui.gui()
                .title(this.miniMessage.deserialize(this.settings.title()))
                .rows(this.settings.rows())
                .disableAllInteractions()
                .create();

            gui.getFiller().fill(new GuiItem(Material.GRAY_STAINED_GLASS_PANE));

            for (AchievementCategory category : this.service.categories()) {
                gui.setItem(category.item().slot(), new GuiItem(category.item().toItemStack(), event -> this.achievementMenu.open(player, category)));
            }

            gui.setItem(this.settings.closeItem().slot(), new GuiItem(this.settings.closeItem().toItemStack(), event -> gui.close(player)));

            this.scheduler.sync(() -> gui.open(player));
        });
    }
}
