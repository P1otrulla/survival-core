package pl.crafthype.core.shared;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public final class AdventureUtil {

    private static final Component RESET_ITALIC = Component.text()
        .decoration(TextDecoration.ITALIC, false)
        .build();

    private AdventureUtil() {

    }

    public static Component reset(Component component) {
        return RESET_ITALIC.append(component);
    }
}
