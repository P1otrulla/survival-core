package pl.crafthype.core.feature.achievement;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.feature.achievement.reward.Reward;
import pl.crafthype.core.feature.statistic.Statistic;

import java.util.ArrayList;
import java.util.List;

@Contextual
public class Achievement {

    private String statisticName;
    private String name;
    private int value;
    private ConfigItem item;

    private double rewardBalance = 0.00;
    private List<String> rewardCommands = new ArrayList<>();
    private List<ConfigItem> rewardItems = new ArrayList<>();

    @Exclude
    private final List<Reward> rewards = new ArrayList<>();

    public Achievement(String name, Statistic statistic, int value, ConfigItem item, double rewardBalance, List<String> rewardCommands, List<ConfigItem> rewardItems) {
        this.name = name;
        this.statisticName = statistic.name();
        this.value = value;
        this.item = item;
        this.rewardBalance = rewardBalance;
        this.rewardCommands = rewardCommands;
        this.rewardItems = rewardItems;
    }

    public Achievement() {

    }

    public Statistic statistic() {
        return Statistic.valueOf(this.statisticName);
    }

    public String id() {
        return this.statisticName + "-" + this.value;
    }

    public String name() {
        return this.name;
    }

    public int value() {
        return this.value;
    }

    public ConfigItem item() {
        return this.item;
    }

    public double rewardBalance() {
        return this.rewardBalance;
    }

    public List<String> rewardCommands() {
        return this.rewardCommands;
    }

    public List<ConfigItem> rewardItems() {
        return this.rewardItems;
    }

    public List<Reward> rewards() {
        return this.rewards;
    }

    public void addReward(Reward reward) {
        this.rewards.add(reward);
    }
}
