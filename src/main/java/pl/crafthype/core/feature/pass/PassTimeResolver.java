package pl.crafthype.core.feature.pass;

import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PassTimeResolver {

    private static final LocalTime START_NETHER = LocalTime.of(20, 0);
    private static final LocalTime STOP_NETHER = LocalTime.of(23, 59);
    private static final LocalTime START_END = LocalTime.of(20, 0);
    private static final LocalTime STOP_END = LocalTime.of(21, 59);

    public boolean canJoin(Player player, String world) {
        if (player.isOp()) {
            return true;
        }

        LocalDateTime localTime = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(2023, 12, 29, 18, 0);

        if (localTime.isBefore(start)) {
            return false;
        }

        if (world.contains("end")) {
            return this.isEndBlocked();
        }

        if (world.contains("nether")) {
            return this.isNetherBlocked();
        }

        return true;
    }

    boolean isNetherBlocked() {
        LocalTime now = LocalTime.now();

        return !now.isBefore(START_NETHER) && !now.isAfter(STOP_NETHER);
    }

    boolean isEndBlocked() {
        LocalTime now = LocalTime.now();

        return !now.isBefore(START_END) && !now.isAfter(STOP_END);
    }
}
