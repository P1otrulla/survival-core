package pl.crafthype.core.feature.top;

import net.dzikoysk.cdn.entity.Contextual;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.feature.statistic.Statistic;

@Contextual
public class BukkitTop implements Top {

    private String name;
    private String statisticName;
    private ConfigItem item;

    public BukkitTop(String name, Statistic statistic, ConfigItem item) {
        this.name = name;
        this.statisticName = statistic.name();
        this.item = item;
    }

    public BukkitTop() {
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Statistic statistic() {
        return Statistic.valueOf(this.statisticName);
    }

    @Override
    public ConfigItem item() {
        return this.item;
    }
}
