package pl.crafthype.core.shared;

public final class MathUtil {

    private MathUtil() {
    }

    public static int calculatePercentage(long gained, long value) {
        if (gained == 0 || value == 0) {
            return 0;
        }

        int percentage = (int) (gained * 100L / value);

        if (percentage > 100) {
            percentage = 100;
        }

        return percentage;
    }

    public static long calculateReaming(long gained, long value) {
        if (gained == 0 || value == 0) {
            return 0;
        }

        long reaming = value - gained;

        if (reaming < 0) {
            reaming = 0;
        }

        return reaming;
    }
}
