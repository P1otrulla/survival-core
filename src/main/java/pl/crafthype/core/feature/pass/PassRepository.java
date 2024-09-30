package pl.crafthype.core.feature.pass;

import java.util.UUID;

public interface PassRepository {

    boolean hasPass(UUID playerId, String passName);

    void addPass(UUID playerId, String passName);

}
