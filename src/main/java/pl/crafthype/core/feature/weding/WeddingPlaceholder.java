package pl.crafthype.core.feature.weding;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class WeddingPlaceholder extends PlaceholderExpansion {

    private final WeddingRepository weddingRepository;
    private final WeddingConfig config;

    public WeddingPlaceholder(WeddingRepository weddingRepository, WeddingConfig config) {
        this.weddingRepository = weddingRepository;
        this.config = config;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "core-weding";
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
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params == null) {
            return "Nie odpowiedni parametr! %core-weding_parametr% (Parametry: isIn, status)";
        }

        try {
            Wedding wedding = this.weddingRepository.findWedding(player.getUniqueId()).get();

            if (params.equalsIgnoreCase("isIn")) {
                if (wedding.isEmpty()) {
                    return WeddingPlaceholder.color(this.config.placeholderWedingNo);
                }

                return WeddingPlaceholder.color(this.config.placeholderWedingYes);
            }

            if (params.equalsIgnoreCase("status")) {
                if (wedding.isEmpty()) {
                    return "";
                }

                return WeddingPlaceholder.color(this.config.placeholderHeart);
            }

            if (params.equalsIgnoreCase("name")) {
                if (wedding.isEmpty()) {
                    return WeddingPlaceholder.color(this.config.placeholderWedingNon);
                }

                return Bukkit.getOfflinePlayer(wedding.playerTwo()).getName();
            }

        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }

        return null;
    }


    private static String color(String toColor) {
        return ChatColor.translateAlternateColorCodes('&', toColor);
    }
}
