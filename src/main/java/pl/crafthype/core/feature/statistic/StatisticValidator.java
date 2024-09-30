package pl.crafthype.core.feature.statistic;

import pl.crafthype.core.shared.DurationUtil;

import java.time.Duration;

public class StatisticValidator {

    private StatisticValidator() {
    }

    public static String validate(StatisticEntry entry) {
        Statistic statistic = entry.statistic();

        if (statistic == Statistic.SPENT_TIME) {
            return time(entry.value());
        }

        if (statistic == Statistic.DISTANCE_TRAVELLED) {
            return distance(entry.value());
        }

        return String.valueOf(entry.value());
    }

    static String distance(long value) {
        if (value < 1000) {
            return value + "m";
        }

        return value / 1000.0D + "km";
    }

    static String time(long seconds) {
        Duration duration = Duration.ofSeconds(seconds);

        return String.valueOf(DurationUtil.formatToHours(duration));
    }
}
