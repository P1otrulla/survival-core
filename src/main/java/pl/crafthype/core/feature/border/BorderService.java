package pl.crafthype.core.feature.border;

import java.util.Collection;
import java.util.Optional;

public interface BorderService {

    void addBorder(BukkitBorder border);

    void removeBorder(BukkitBorder world);

    void updateBorder(BukkitBorder border);

    Optional<BukkitBorder> findBorder(String world);

    Collection<BukkitBorder> borders();
}
