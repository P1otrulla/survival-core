package pl.crafthype.core.shared.legacy;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Legacy {

    public static final LegacyComponentSerializer AMPERSAND_SERIALIZER = LegacyComponentSerializer.builder()
        .character('&')
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build();

    private Legacy() {
    }

    public static Component component(String text) {
        return AMPERSAND_SERIALIZER.deserialize(text);
    }
}
