package pl.crafthype.core.shared;

import java.time.Duration;

public final class DurationUtil {

    private DurationUtil() {

    }

    public static String format(Duration duration) {
        return format(duration, true);
    }

    public static String format(Duration duration, boolean removeMillis) {
        if (removeMillis) {
            duration = Duration.ofSeconds(duration.toSeconds());
        }

        return duration.toString()
            .substring(2)
            .replaceAll("(\\d[HMS])(?!$)", "$1")
            .toLowerCase();
    }

    public static int formatToHours(Duration duration) {
        return (int) duration.toHours();
    }
}
