package pl.crafthype.core.feature.pass;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import pl.crafthype.core.config.ConfigService;
import pl.crafthype.core.config.ReloadableConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PassConfigRepository implements ReloadableConfig, PassRepository {

    @Exclude
    private final ConfigService configService;

    public Map<UUID, PassDataEntry> passes = new HashMap<>();

    public PassConfigRepository(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "data/pass-data.dat");
    }

    @Override
    public boolean hasPass(UUID playerId, String passName) {
        PassDataEntry entry = this.passes.get(playerId);

        if (entry == null) {
            return false;
        }

        return entry.hasPass(passName);
    }

    @Override
    public void addPass(UUID playerId, String passName) {
        PassDataEntry entry = this.passes.get(playerId);

        if (entry == null) {
            this.edit(passes -> passes.put(playerId, new PassDataEntry(Collections.singletonList(passName))));

            return;
        }

        List<String> newPasses = new ArrayList<>(entry.passes);
        newPasses.add(passName);

        this.edit(passes -> passes.put(playerId, new PassDataEntry(newPasses)));
    }

    private void edit(Consumer<Map<UUID, PassDataEntry>> editor) {
        Map<UUID, PassDataEntry> newPasses = new HashMap<>(this.passes);

        editor.accept(newPasses);

        this.passes = newPasses;

        this.configService.save(this);
    }

    @Contextual
    static class PassDataEntry {

        private List<String> passes = new ArrayList<>();

        public PassDataEntry(List<String> passes) {
            this.passes = passes;
        }

        public PassDataEntry() {
        }

        boolean hasPass(String pass) {
            return this.passes.contains(pass);
        }
    }
}
