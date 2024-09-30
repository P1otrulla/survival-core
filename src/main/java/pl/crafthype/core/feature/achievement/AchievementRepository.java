package pl.crafthype.core.feature.achievement;

import pl.crafthype.core.database.Refreshable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface AchievementRepository extends Refreshable {

    void add(UUID uniqueId, Achievement achievement);

    CompletableFuture<Boolean> isGained(UUID uniqueId, Achievement achievement);

    int findGained(UUID uniqueId);

}
