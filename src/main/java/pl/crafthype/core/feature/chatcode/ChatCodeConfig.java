package pl.crafthype.core.feature.chatcode;

import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.inventory.ItemStack;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.notification.Notification;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ChatCodeConfig implements ReloadableConfig, ChatCodeSettings {

    private int codeLength = 8;
    private Duration interval = Duration.ofMinutes(10);

    @Description("# Typy: ONE_COMMAND, ALL_COMMANDS, ONE_ITEM, ALL_ITEMS, ONE_MONEY, ALL_MONEY")
    private List<RewardType> rewards = Arrays.asList(RewardType.ALL_COMMANDS, RewardType.ALL_ITEMS, RewardType.ALL_MONEY);

    private List<String> commands = Collections.singletonList("give {PLAYER} diamond 1");
    private Map<String, ConfigItem> items = Map.of("1", new ConfigItem());
    private List<Double> money = Collections.singletonList(100.0);

    private Notification generatedCode = Notification.chat("&6Wygenerowano nowy kod: &e{CODE} do przepisania na chacie!");
    private Notification codePrescribed = Notification.chat("&6Kod &e{CODE} &6został przepisany na chacie przez {PLAYER}!");

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "chat-code.yml");
    }

    @Override
    public int codeLength() {
        return this.codeLength;
    }

    @Override
    public Duration interval() {
        return this.interval;
    }

    @Override
    public Notification generatedCode() {
        return this.generatedCode;
    }

    @Override
    public Notification codePrescribed() {
        return this.codePrescribed;
    }

    @Override
    public List<String> commands() {
        return this.commands;
    }

    @Override
    public List<ItemStack> items() {
        return this.items.values()
            .stream()
            .map(ConfigItem::toItemStack)
            .toList();
    }

    @Override
    public List<Double> money() {
        return this.money;
    }

    @Override
    public List<RewardType> rewards() {
        return this.rewards;
    }
}
