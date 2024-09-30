package pl.crafthype.core.shared;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;

public final class RegionUtil {

    public static boolean isInRegion(Player player, String region) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(player.getLocation()));

        for (ProtectedRegion protectedRegion : set) {
            if (protectedRegion.getId().equalsIgnoreCase(region)) {
                return true;
            }
        }

        return false;
    }

}
