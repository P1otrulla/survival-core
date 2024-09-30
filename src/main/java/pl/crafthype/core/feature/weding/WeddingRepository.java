package pl.crafthype.core.feature.weding;

import pl.crafthype.core.database.Refreshable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface WeddingRepository extends Refreshable {

    CompletableFuture<Wedding> findWedding(UUID uniqueId);

    CompletableFuture<Void> createWedding(Wedding wedding);

    CompletableFuture<Void> deleteWedding(UUID uniqueId);

    CompletableFuture<Wedding> findWeddingAsync(UUID uniqueId);
}
