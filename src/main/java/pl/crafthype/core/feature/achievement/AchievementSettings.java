package pl.crafthype.core.feature.achievement;

import pl.crafthype.core.config.item.ConfigItem;

import java.util.List;

public interface AchievementSettings {

    String title();

    int rows();

    List<String> gainedLore();

    String gainedAchievement();

    String notAchieved();

    String achieved();

    String achievementBroadcast();

    ConfigItem backItem();

    ConfigItem closeItem();

    int sizeOfAchievements();

    String placeholderTake();

    String placeholderNotAchieved();

    String placeholderAchieved();

}
