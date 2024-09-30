package pl.crafthype.core.feature.customcommand;

import net.dzikoysk.cdn.entity.Contextual;

import java.util.List;

@Contextual
public class CustomCommand {

    private String name;
    private List<String> aliases;
    private List<String> messages;

    public CustomCommand(String name, List<String> aliases, List<String> messages) {
        this.name = name;
        this.aliases = aliases;
        this.messages = messages;
    }

    public CustomCommand() {

    }

    public String name() {
        return this.name;
    }

    public List<String> aliases() {
        return this.aliases;
    }

    public List<String> messages() {
        return this.messages;
    }
}
