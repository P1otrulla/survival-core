package pl.crafthype.core.feature.sex;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SexPlaceholder extends PlaceholderExpansion {

    private final SexRepository sexRepository;
    private final SexConfig config;

    public SexPlaceholder(SexRepository sexRepository, SexConfig config) {
        this.sexRepository = sexRepository;
        this.config = config;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "core-sex";
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

        String rawSex = this.sexRepository.findSexSync(player.getUniqueId());

        if (params.equalsIgnoreCase("sex")) {
            if (rawSex == null) {
                return SexPlaceholder.color(this.config.noSex);
            }

            Sex sex = this.config.sexes.get(rawSex);

            if (sex == null) {
                return SexPlaceholder.color(this.config.noSex);
            }

            return SexPlaceholder.color(sex.placeholderDisplay());
        }

        return "Nie odpowiedni parametr! %core-sex_parametr% (Parametry: sex)";
    }

    private static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}