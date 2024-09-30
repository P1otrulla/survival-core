package pl.crafthype.core.feature.achievement;

import net.dzikoysk.cdn.entity.Contextual;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.feature.statistic.Statistic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Contextual
public class AchievementCategory {

    private String name;
    private ConfigItem item;
    private String statisticName;

    private String title;
    private int rows;

    private Map<String, Achievement> achievements = new HashMap<>();

    public AchievementCategory(String name, ConfigItem item, Statistic statistic, String title, int rows, Map<String, Achievement> achievements) {
        this.name = name;
        this.item = item;
        this.statisticName = statistic.name();
        this.title = title;
        this.rows = rows;
        this.achievements = achievements;
    }

    public AchievementCategory() {

    }

    public String name() {
        return this.name;
    }

    public ConfigItem item() {
        return this.item;
    }

    public Statistic statistic() {
        return Statistic.valueOf(this.statisticName);
    }

    public String title() {
        return this.title;
    }

    public int rows() {
        return this.rows;
    }

    public Collection<Achievement> achievements() {
        return Collections.unmodifiableCollection(this.achievements.values());
    }
}
