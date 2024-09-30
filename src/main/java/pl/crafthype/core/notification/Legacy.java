package pl.crafthype.core.notification;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;

public final class Legacy {

    public static final Component RESET_ITALIC = Component.text()
        .decoration(TextDecoration.ITALIC, false)
        .build();

    private static final LegacyComponentSerializer AMPERSAND_SERIALIZER = LegacyComponentSerializer.builder()
        .character('&')
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build();

    public static Component component(String text) {
        return AMPERSAND_SERIALIZER.deserialize(text);
    }

    public static List<Component> component(List<String> texts) {
        return texts.stream()
            .map(Legacy::component)
            .toList();
    }

}
