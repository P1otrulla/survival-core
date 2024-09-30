package pl.crafthype.core.feature.customcommand;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import pl.crafthype.core.config.ReloadableConfig;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomCommandConfig implements ReloadableConfig {

    public Map<String, CustomCommand> customCommands = Map.of(
        "pomoc", new CustomCommand("pomoc", Collections.singletonList("help"), List.of("&6&lCrafthype &8&l>> &7Witaj na serwerze!"))
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "custom-commands.yml");
    }
}
