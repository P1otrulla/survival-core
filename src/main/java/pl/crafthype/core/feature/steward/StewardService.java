package pl.crafthype.core.feature.steward;

import pl.crafthype.core.config.ConfigService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class StewardService implements StewardRepository {

    private final ConfigService configService;
    private final StewardDataConfig data;

    public StewardService(ConfigService configService, StewardDataConfig data) {
        this.configService = configService;
        this.data = data;
    }

    @Override
    public boolean isCollected(UUID uniqueId, Steward steward) {
        for (CollectedSteward collectedSteward : this.data.collected) {
            if (collectedSteward.name().equals(steward.name()) && collectedSteward.uniqueId().equals(uniqueId)) {
                Instant now = Instant.now();

                Instant expiration = collectedSteward.collectedAt().plus(steward.coolDown());

                if (now.isAfter(expiration)) {
                    this.removeCollected(uniqueId, steward);

                    return false;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public Instant findCollectTime(UUID uniqueId, Steward steward) {
        for (CollectedSteward collectedSteward : this.data.collected) {
            if (collectedSteward.name().equals(steward.name()) && collectedSteward.uniqueId().equals(uniqueId)) {
                Instant now = Instant.now();

                Instant collect = collectedSteward.collectedAt().plus(steward.coolDown());

                if (now.isBefore(collect)) {
                    return collect;
                }
            }
        }

        return Instant.now();
    }

    @Override
    public void addCollected(UUID uniqueId, Steward steward) {
        this.edit(collected -> collected.add(new CollectedSteward(uniqueId, steward.name(), Instant.now())));
    }

    @Override
    public void removeCollected(UUID uniqueId, Steward steward) {
        this.edit(collected -> collected.removeIf(collectedSteward -> collectedSteward.uniqueId().equals(uniqueId)));
    }

    public void edit(Consumer<List<CollectedSteward>> editor) {
        List<CollectedSteward> newCollected = new ArrayList<>(this.data.collected);

        editor.accept(newCollected);

        this.data.collected = newCollected;
        this.configService.save(this.data);
    }
}
