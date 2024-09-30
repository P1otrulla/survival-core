package pl.crafthype.core.feature.top;

import pl.crafthype.core.feature.statistic.Statistic;
import pl.crafthype.core.shared.DurationUtil;

import java.time.Duration;

public class TopValidator {

    private TopValidator() {
    }

    public static String validate(TopEntry entry) {
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

    static String time(long value) {
        Duration duration = Duration.ofSeconds(value);

        return DurationUtil.format(duration);
    }
}
