package pl.crafthype.core.feature.sex;

import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class Sex {

    private String name;
    private String placeholderDisplay;

    private Sex(String name, String placeholderDisplay) {
        this.name = name;
        this.placeholderDisplay = placeholderDisplay;
    }

    public Sex() {

    }

    public static Sex create(String name, String displayName) {
        return new Sex(name, displayName);
    }

    public String name() {
        return this.name;
    }

    public String placeholderDisplay() {
        return this.placeholderDisplay;
    }
}
