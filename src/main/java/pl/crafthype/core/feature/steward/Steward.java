package pl.crafthype.core.feature.steward;

import net.dzikoysk.cdn.entity.Contextual;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Contextual
public class Steward {

    private String name = "none";
    private int slot = 0;
    private String permission = "none";
    private Duration coolDown = Duration.ofSeconds(0);
    private List<String> commands = new ArrayList<>();

    public Steward(String name, int slot, String permission, Duration coolDown, List<String> commands) {
        this.name = name;
        this.slot = slot;
        this.permission = permission;
        this.coolDown = coolDown;
        this.commands = commands;
    }

    public Steward() {

    }

    public String name() {
        return this.name;
    }

    public int slot() {
        return this.slot;
    }

    public String permission() {
        return this.permission;
    }

    public Duration coolDown() {
        return this.coolDown;
    }

    public List<String> commands() {
        return this.commands;
    }
}