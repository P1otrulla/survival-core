package pl.crafthype.core.feature.achievement;

import pl.crafthype.core.feature.statistic.Statistic;

import java.util.Collection;

public interface AchievementService {

    int findAchievementsSize(Statistic statistic);

    Collection<AchievementCategory> categories();
}
