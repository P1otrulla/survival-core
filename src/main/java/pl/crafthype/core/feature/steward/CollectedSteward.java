package pl.crafthype.core.feature.steward;

import net.dzikoysk.cdn.entity.Contextual;

import java.time.Instant;
import java.util.UUID;

@Contextual
public class CollectedSteward {

    private UUID uniqueId;
    private String name;
    private Instant collectedAt;

    public CollectedSteward(UUID uniqueId, String name, Instant collectedAt) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.collectedAt = collectedAt;
    }

    public CollectedSteward() {

    }

    public UUID uniqueId() {
        return this.uniqueId;
    }

    public String name() {
        return this.name;
    }

    public Instant collectedAt() {
        return this.collectedAt;
    }
}
