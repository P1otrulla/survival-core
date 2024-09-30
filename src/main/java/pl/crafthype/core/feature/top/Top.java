package pl.crafthype.core.feature.top;

import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.feature.statistic.Statistic;

public interface Top {

    String name();

    Statistic statistic();

    ConfigItem item();

}
