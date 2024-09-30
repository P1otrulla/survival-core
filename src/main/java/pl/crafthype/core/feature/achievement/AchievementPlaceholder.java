package pl.crafthype.core.feature.achievement;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AchievementPlaceholder extends PlaceholderExpansion {

    private final AchievementRepository repository;
    private final AchievementSettings settings;

    public AchievementPlaceholder(AchievementRepository repository, AchievementSettings settings) {
        this.repository = repository;
        this.settings = settings;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "core-achievements";
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
            return "Nie odpowiedni parametr! %core-achievements_parametr% (Parametry: gained, all)";
        }

        if (params.equalsIgnoreCase("gained")) {
            int gained = this.repository.findGained(player.getUniqueId());

            return String.valueOf(gained);
        }

        if (params.equalsIgnoreCase("all")) {
            return String.valueOf(this.settings.sizeOfAchievements());
        }

        return "Nie odpowiedni parametr! %core-achievements_parametr% (Parametry: gained, all)";
    }

}