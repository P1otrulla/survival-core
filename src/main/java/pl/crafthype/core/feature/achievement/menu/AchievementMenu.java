package pl.crafthype.core.feature.achievement.menu;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.utilities.text.Formatter;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.feature.achievement.Achievement;
import pl.crafthype.core.feature.achievement.AchievementCategory;
import pl.crafthype.core.feature.achievement.AchievementRepository;
import pl.crafthype.core.feature.achievement.AchievementSettings;
import pl.crafthype.core.feature.achievement.reward.Reward;
import pl.crafthype.core.feature.statistic.Statistic;
import pl.crafthype.core.feature.statistic.StatisticEntry;
import pl.crafthype.core.feature.statistic.StatisticRegistry;
import pl.crafthype.core.notification.Notification;
import pl.crafthype.core.notification.NotificationBroadcaster;
import pl.crafthype.core.scheduler.Scheduler;
import pl.crafthype.core.shared.MathUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AchievementMenu {

    private final StatisticRegistry statisticRegistry;
    private final AchievementRepository repository;
    private final NotificationBroadcaster announcer;
    private final AchievementSettings settings;
    private final MiniMessage miniMessage;
    private final Scheduler scheduler;

    private GuiItem backItem;

    public AchievementMenu(StatisticRegistry statisticRegistry, AchievementRepository repository, NotificationBroadcaster announcer, AchievementSettings settings, MiniMessage miniMessage, Scheduler scheduler) {
        this.statisticRegistry = statisticRegistry;
        this.repository = repository;
        this.announcer = announcer;
        this.settings = settings;
        this.miniMessage = miniMessage;
        this.scheduler = scheduler;
    }

    public static ItemStack createItem(ConfigItem item, Formatter formatter) {
        return item.toItemStack(formatter);
    }

    public void open(Player player, AchievementCategory category) {
        this.scheduler.async(() -> {
            Gui gui = prepareGui(category);

            Map<Statistic, StatisticEntry> playerStatistics = getPlayerStatistics(category, player);
            List<CompletableFuture<Void>> futures = processAchievements(gui, playerStatistics, player, category);

            CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).join();

            this.scheduler.sync(() -> gui.open(player));
        });
    }

    private Gui prepareGui(AchievementCategory category) {
        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(category.title()))
            .rows(category.rows())
            .disableAllInteractions()
            .create();

        gui.getFiller().fill(new GuiItem(Material.GRAY_STAINED_GLASS_PANE));
        return gui;
    }

    private Map<Statistic, StatisticEntry> getPlayerStatistics(AchievementCategory category, Player player) {
        return category.achievements().stream()
            .map(Achievement::statistic)
            .distinct()
            .collect(Collectors.toMap(
                Function.identity(),
                statistic -> this.statisticRegistry.get(player.getUniqueId(), statistic)
            ));
    }

    private List<CompletableFuture<Void>> processAchievements(Gui gui, Map<Statistic, StatisticEntry> playerStatistics, Player player, AchievementCategory category) {
        List<CompletableFuture<Void>> futures = new LinkedList<>();

        for (Achievement achievement : category.achievements()) {
            CompletableFuture<Void> future = processSingleAchievement(gui, playerStatistics, player, achievement);
            futures.add(future);
        }

        return futures;
    }

    private CompletableFuture<Void> processSingleAchievement(Gui gui, Map<Statistic, StatisticEntry> playerStatistics, Player player, Achievement achievement) {

        return this.repository.isGained(player.getUniqueId(), achievement).thenAcceptAsync(gained -> {

            StatisticEntry statisticEntry = playerStatistics.get(achievement.statistic());
            long value = statisticEntry.value();

            Formatter formatter = prepareFormatter(value, player, achievement, gained);

            gui.setItem(achievement.item().slot(), createGuiItem(achievement, formatter, value, gained, player, gui));

            gui.setItem(this.settings.backItem().slot(), this.backItem);

        }, this.scheduler::sync);
    }

    private Formatter prepareFormatter(long value, Player player, Achievement achievement, boolean gained) {

        return new Formatter()
            .register("{ACHIEVEMENT}", achievement.name())
            .register("{VALUE}", String.valueOf(value))
            .register("{MAX_VALUE}", achievement.value())
            .register("{PLAYER}", player.getName())
            .register("{REAMING}", MathUtil.calculateReaming(value, achievement.value()))
            .register("{PROGRESS}", MathUtil.calculatePercentage(value, achievement.value()))
            .register("{FOOTER}", prepareFooter(gained, value, achievement));

    }

    private GuiItem createGuiItem(Achievement achievement, Formatter formatter, long value, boolean gained, Player player, Gui gui) {

        return new GuiItem(AchievementMenu.createItem(achievement.item(), formatter), event -> {

            if (value < achievement.value()) {
                this.announcer.sendAnnounce(player, Notification.chat(this.settings.notAchieved()));
                return;
            }

            if (gained) {
                this.announcer.sendAnnounce(player, Notification.chat(this.settings.gainedAchievement()));
                return;
            }

            processReward(achievement, player, gui, formatter);
        });
    }

    private String prepareFooter(boolean gained, long value, Achievement achievement) {

        if (gained) {
            return this.settings.placeholderAchieved();
        }

        if (value < achievement.value()) {
            return this.settings.placeholderNotAchieved();
        }

        return this.settings.placeholderTake();
    }

    private void processReward(Achievement achievement, Player player, Gui gui, Formatter formatter) {

        this.repository.add(player.getUniqueId(), achievement);

        for (Reward reward : achievement.rewards()) {
            reward.giveReward(player);
        }

        gui.updateItem(achievement.item().slot(), new GuiItem(AchievementMenu.createItem(achievement.item(), formatter)));

        this.announcer.sendAnnounce(player, Notification.chat(this.settings.achieved()), formatter);
        this.announcer.sendAnnounceAll(Notification.chat(this.settings.achievementBroadcast()), formatter);
    }

    public void setBackItem(AchievementCategoryMenu menu) {
        this.backItem = new GuiItem(this.settings.backItem().toItemStack(), event -> menu.open((Player) event.getWhoClicked()));
    }
}