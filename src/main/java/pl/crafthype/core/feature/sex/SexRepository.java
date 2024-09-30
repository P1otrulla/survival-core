package pl.crafthype.core.feature.sex;

import pl.crafthype.core.database.Refreshable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface SexRepository extends Refreshable {

    void updateSex(UUID uniqueId, String sex);

    CompletableFuture<String> findSex(UUID uniqueId);

    String findSexSync(UUID uniqueId);
}
