package pl.crafthype.core.feature.statistic;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class StatisticPlaceholder extends PlaceholderExpansion {

    private final StatisticRegistry registry;

    public StatisticPlaceholder(StatisticRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String getIdentifier() {
        return "core-statistic";
    }

    @Override
    public String getAuthor() {
        return "Piotrulla";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String params) {
        String invalidParametr = "Nie odpowiedni parametr! %core-statistic_parametr% (Parametry: breakStone, harvestedCrops, spentTime, placedBlocks, killedMobs, deaths, playersKilled, damageDealt, distanceTravelled, joins)";

        if (params == null) {
            return invalidParametr;
        }

        Collection<StatisticEntry> entries = this.registry.getAll(player.getUniqueId());

        for (StatisticEntry entry : entries) {
            String name = entry.statistic().name();

            if (name.equalsIgnoreCase(params)) {
                return StatisticValidator.validate(entry);
            }
            else if (params.equalsIgnoreCase(name + "-int")) {
                return String.valueOf(entry.value());
            }
        }

        return "0";
    }
}
