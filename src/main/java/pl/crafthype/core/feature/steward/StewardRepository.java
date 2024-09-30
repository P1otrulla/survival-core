package pl.crafthype.core.feature.steward;

import java.time.Instant;
import java.util.UUID;

public interface StewardRepository {

    boolean isCollected(UUID uniqueId, Steward steward);

    Instant findCollectTime(UUID uniqueId, Steward steward);

    void addCollected(UUID uniqueId, Steward steward);

    void removeCollected(UUID uniqueId, Steward steward);
}
