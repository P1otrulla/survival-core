package pl.crafthype.core.shared.legacy;

import net.kyori.adventure.text.Component;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public final class LegacyColorProcessor implements UnaryOperator<Component> {

    @Override
    public Component apply(Component component) {
        return component.replaceText(builder -> builder.match(Pattern.compile(".*"))
            .replacement((matchResult, builder1) -> Legacy.component(matchResult.group())));
    }
}
