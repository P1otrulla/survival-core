package pl.crafthype.core.feature.statistic;

import net.dzikoysk.cdn.entity.Contextual;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

@Contextual
public record Statistic(String name) {

    public static final Statistic BREAK_STONE = new Statistic("breakStone");
    public static final Statistic HARVESTED_CROPS = new Statistic("harvestedCrops");
    public static final Statistic SPENT_TIME = new Statistic("spentTime");
    public static final Statistic PLACED_BLOCKS = new Statistic("placedBlocks");
    public static final Statistic MONSTER_KILLED = new Statistic("killedMobs");
    public static final Statistic DEATHS = new Statistic("deaths");
    public static final Statistic PLAYERS_KILLED = new Statistic("playersKilled");
    public static final Statistic DAMAGE_DEALT = new Statistic("damageDealt");
    public static final Statistic DISTANCE_TRAVELLED = new Statistic("distanceTravelled");
    public static final Statistic JOINS = new Statistic("joins");

    public static List<Statistic> values() {
        return Arrays.stream(Statistic.class.getDeclaredFields()).filter(field -> Modifier.isStatic(field.getModifiers())).map(field -> {
            try {
                return (Statistic) field.get(Statistic.class);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    public static Statistic valueOf(String name) {
        return values().stream().filter(statistic -> statistic.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
