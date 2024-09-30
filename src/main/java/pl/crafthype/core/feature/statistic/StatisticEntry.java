package pl.crafthype.core.feature.statistic;

import java.util.UUID;

public record StatisticEntry(UUID owner, Statistic statistic, long value) {
}
