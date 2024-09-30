package pl.crafthype.core.feature.border;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import panda.utilities.text.Formatter;
import pl.crafthype.core.shared.DurationUtil;

import java.time.Duration;
import java.time.Instant;

public final class BorderUtils {

    private BorderUtils() {

    }

    public static void createWorldBorder(BukkitBorder border) {
        World world = Bukkit.getWorld(border.world());

        if (world == null) {
            return;
        }

        int size = border.currentSize() + border.expandSize();

        createWorldBorder(world, size);

        border.updateLastExpand(Instant.now());
        border.updateCurrentSize(size);
    }

    public static void removeWorldBorder(BukkitBorder border) {
        World world = Bukkit.getWorld(border.world());

        if (world == null) {
            return;
        }

        WorldBorder worldBorder = world.getWorldBorder();

        worldBorder.reset();
    }

    public static void createWorldBorder(World world, double size) {
        WorldBorder worldBorder = world.getWorldBorder();

        worldBorder.setCenter(world.getSpawnLocation());
        worldBorder.setSize(size * 2);
    }

    public static Formatter format(BukkitBorder border) {
        Instant expandTime = border.lastExpand().plus(border.duration());
        Duration expandDuration = Duration.between(Instant.now(), expandTime);

        return new Formatter()
            .register("{WORLD}", border.world())
            .register("{START-SIZE}", border.startSize())
            .register("{CURRENT-SIZE}", border.currentSize())
            .register("{EXPAND-SIZE}", border.expandSize())
            .register("{TIME}", DurationUtil.format(expandDuration))
            .register("{WAS-SIZE}", border.currentSize() - border.expandSize())
            .register("{NEXT-SIZE}", border.currentSize() + border.expandSize());
    }
}

