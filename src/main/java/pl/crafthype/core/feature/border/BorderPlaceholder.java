package pl.crafthype.core.feature.border;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.crafthype.core.shared.DurationUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class BorderPlaceholder extends PlaceholderExpansion {

    private final BorderService borderService;

    public BorderPlaceholder(BorderService borderService) {
        this.borderService = borderService;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "core-border";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Piotrulla";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(final Player player, @NotNull final String params) {
        if (params == null) {
            return "Nie odpowiedni parametr! %core-border_parametr% (Parametry: world, startSzie, currentSize, expandSize, expandTime, wasSize, nextSize)";
        }

        Optional<BukkitBorder> bukkitBorder = this.borderService.findBorder(player.getWorld().getName());

        if (bukkitBorder.isEmpty()) {
            return "Nie ma granicy!";
        }

        BukkitBorder border = bukkitBorder.get();

        if (params.equalsIgnoreCase("world")) {
            return border.world();
        }

        if (params.equalsIgnoreCase("startSize")) {
            return String.valueOf(border.startSize());
        }

        if (params.equalsIgnoreCase("currentSize")) {
            return String.valueOf(border.currentSize());
        }

        if (params.equalsIgnoreCase("expandSize")) {
            return String.valueOf(border.expandSize());
        }

        if (params.equalsIgnoreCase("expandTime")) {
            Instant expandTime = border.lastExpand().plus(border.duration());
            Duration expandDuration = Duration.between(Instant.now(), expandTime);

            return DurationUtil.format(expandDuration);
        }

        if (params.equalsIgnoreCase("wasSize")) {
            int wasSize = border.currentSize() - border.expandSize();

            return String.valueOf(wasSize);
        }

        if (params.equalsIgnoreCase("nextSize")) {
            int nextSize = border.currentSize() + border.expandSize();

            return String.valueOf(nextSize);
        }

        return "Nie odpowiedni parametr! (world, startSize, currentSize, expandSize, expandTime, wasSize, nextSize)";
    }

}
