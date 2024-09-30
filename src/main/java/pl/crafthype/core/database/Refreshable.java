package pl.crafthype.core.database;

import java.util.UUID;

public interface Refreshable {

    void refresh(UUID uniqueId);
}
