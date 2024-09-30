package pl.crafthype.core.user;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UserPlaceholder extends PlaceholderExpansion {

    private final UserService userService;

    public UserPlaceholder(UserService userService) {
        this.userService = userService;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "core-user";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Piotrulla";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(final Player player, @NotNull final String params) {
        if (params == null) {
            return "Nie odpowiedni parametr! %core-user_parametr% (Parametry: name)";
        }

        Optional<User> userOptional = this.userService.find(player.getUniqueId());

        if (userOptional.isEmpty()) {
            return "Brak gracza!";
        }

        User user = userOptional.get();

        if (params.equalsIgnoreCase("name")) {
            return user.name();
        }

        return "Nie odpowiedni parametr! %core-user_parametr% (Parametry: name)";
    }
}
