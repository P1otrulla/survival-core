package pl.crafthype.core.feature.top;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import panda.utilities.text.Formatter;
import pl.crafthype.core.feature.statistic.StatisticRegistry;
import pl.crafthype.core.feature.statistic.StatisticValidator;
import pl.crafthype.core.scheduler.Scheduler;
import pl.crafthype.core.scheduler.Task;
import pl.crafthype.core.shared.DurationUtil;
import pl.crafthype.core.user.User;
import pl.crafthype.core.user.UserService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TopMenu {

    private final StatisticRegistry statisticRegistry;
    private final UserService userService;
    private final MiniMessage miniMessage;
    private final TopRegistry registry;
    private final TopSettings settings;
    private final Scheduler scheduler;
    private final TopService service;

    private final List<HumanEntity> currentlyViewing = new ArrayList<>();

    private Task updateTask;
    private Gui gui;

    public TopMenu(StatisticRegistry statisticRegistry, UserService userService, MiniMessage miniMessage, TopRegistry registry, TopSettings settings, Scheduler scheduler, TopService service) {
        this.statisticRegistry = statisticRegistry;
        this.userService = userService;
        this.miniMessage = miniMessage;
        this.registry = registry;
        this.settings = settings;
        this.scheduler = scheduler;
        this.service = service;
    }

    public void startUpdateTask() {
        this.updateTask = this.scheduler.timerSync(() -> {
            if (this.gui == null) {
                return;
            }

            Formatter formatter = new Formatter()
                .register("{TIME}", DurationUtil.format(this.registry.nextUpdate()));

            int slot = this.settings.nextUpdate().slot();
            ItemStack itemStack = this.settings.nextUpdate().toItemStack(formatter);

            this.gui.updateItem(slot, itemStack);

        }, Duration.ofSeconds(10), Duration.ofSeconds(1));
    }

    public void stopUpdateTask() {
        if (this.updateTask == null) {
            return;
        }

        this.updateTask.cancel();
    }

    void updateMenu() {
        for (HumanEntity humanEntity : new HashSet<>(this.currentlyViewing)) {
            humanEntity.closeInventory();

            this.open(humanEntity);
        }
    }

    String generateEntry(List<TopEntry> entries, int index) {
        if (entries.size() <= index) {
            return this.settings.entryEmptyFormat();
        }

        TopEntry entry = entries.get(index);

        if (entry == null) {
            return this.settings.entryEmptyFormat();
        }

        Optional<User> userOptional = this.userService.find(entry.owner());

        if (userOptional.isEmpty()) {
            return this.settings.entryEmptyFormat();
        }

        User user = userOptional.get();
        int currentIndex = index + 1;

        if (Objects.isNull(currentIndex)) {
            return this.settings.entryEmptyFormat();
        }

        Formatter userFormatter = new Formatter()
            .register("{POSITION}", currentIndex)
            .register("{NAME}", user.name())
            .register("{VALUE}", TopValidator.validate(entry));

        return userFormatter.format(this.settings.entryFormat());
    }

    public void open(HumanEntity humanEntity) {
        this.gui = Gui.gui()
            .title(this.miniMessage.deserialize(this.settings.topTitle()))
            .rows(this.settings.topRows())
            .disableAllInteractions()
            .create();

        for (Top top : this.service.tops()) {
            int i1 = this.settings.topSize();

            List<TopEntry> entries = this.registry.findBestOf(top.statistic(), i1);

            Formatter formatter = new Formatter();

            for (int i = 0; i < i1 + 1; i++) {
                formatter.register("{TOP-" + (i + 1) + "}", this.generateEntry(entries, i));
            }

            ItemStack item = top.item().toItemStack(formatter);

            this.gui.setItem(top.item().slot(), new GuiItem(item));
        }

        Formatter statisticFormatter = new Formatter();

        this.statisticRegistry.getAll(humanEntity.getUniqueId()).forEach(statisticEntry -> {
            statisticFormatter.register("{" + statisticEntry.statistic().name() + "}", StatisticValidator.validate(statisticEntry));
        });

        ItemStack info = this.settings.info().toItemStack(statisticFormatter);

        this.gui.setItem(this.settings.info().slot(), new GuiItem(info));
        this.gui.getFiller().fill(new GuiItem(this.settings.filler().toItemStack()));
        this.gui.setItem(this.settings.close().slot(), new GuiItem(this.settings.close().toItemStack(), event -> event.getWhoClicked().closeInventory()));
        this.gui.setCloseGuiAction(event -> this.currentlyViewing.remove(event.getPlayer()));

        this.currentlyViewing.add(humanEntity);
        this.gui.open(humanEntity);
    }
}
