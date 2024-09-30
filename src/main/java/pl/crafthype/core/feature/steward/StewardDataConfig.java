package pl.crafthype.core.feature.steward;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import pl.crafthype.core.config.ReloadableConfig;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class StewardDataConfig implements ReloadableConfig {

    public List<CollectedSteward> collected = new LinkedList<>();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "data/steward.yml");
    }
}
