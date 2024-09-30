package pl.crafthype.core.feature.top;

import pl.crafthype.core.config.item.ConfigItem;

import java.time.Duration;

public interface TopSettings {

    int topSize();

    String topTitle();

    int topRows();

    Duration topUpdateInterval();

    String entryFormat();

    String entryEmptyFormat();

    ConfigItem filler();

    ConfigItem close();

    ConfigItem nextUpdate();

    ConfigItem info();
}
