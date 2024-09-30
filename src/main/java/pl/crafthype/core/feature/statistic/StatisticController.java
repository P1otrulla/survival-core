package pl.crafthype.core.feature.statistic;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.UUID;

public class StatisticController implements Listener {

    private final StatisticRepository statisticRepository;
    private final StatisticRegistry statisticRegistry;

    public StatisticController(StatisticRepository statisticRepository, StatisticRegistry statisticRegistry) {
        this.statisticRepository = statisticRepository;
        this.statisticRegistry = statisticRegistry;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void onBreak(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        if (player.isOp()) {
            return;
        }

        UUID uniqueId = player.getUniqueId();

        this.statisticRegistry.add(uniqueId, Statistic.PLACED_BLOCKS, 1);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        if (player.isOp()) {
            return;
        }

        UUID uniqueId = player.getUniqueId();
        Block block = event.getBlock();

        if (block.getType() == Material.STONE || block.getType() == Material.DEEPSLATE) {
            this.statisticRegistry.add(uniqueId, Statistic.BREAK_STONE, 1);
        }

        if (block.getState() == null) {
            return;
        }

        this.statisticRegistry.add(uniqueId, Statistic.HARVESTED_CROPS, 1);
    }

    @EventHandler
    void onMonsterDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) {
            return;
        }

        if (event.getEntity() instanceof Monster) {
            Player player = event.getEntity().getKiller();
            UUID uniqueId = player.getUniqueId();
            this.statisticRegistry.add(uniqueId, Statistic.MONSTER_KILLED, 1);
        }
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        this.statisticRegistry.add(player.getUniqueId(), Statistic.DEATHS, 1);

        if (killer != null) {
            this.statisticRegistry.add(killer.getUniqueId(), Statistic.PLAYERS_KILLED, 1);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player player) {
            if (player.isOp()) {
                return;
            }

            this.statisticRegistry.add(player.getUniqueId(), Statistic.DAMAGE_DEALT, (int) event.getDamage());
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        if (player.isInsideVehicle()) {
            return;
        }

        Location from = event.getFrom();
        Location eventTo = event.getTo();

        if (eventTo == null) {
            return;
        }

        if (from.getBlockX() != eventTo.getBlockX() ||
            from.getBlockY() != eventTo.getBlockY() ||
            from.getBlockZ() != eventTo.getBlockZ()) {
            this.statisticRegistry.add(player.getUniqueId(), Statistic.DISTANCE_TRAVELLED, 1);
        }
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.isOp()) {
            return;
        }

        this.statisticRegistry.add(player.getUniqueId(), Statistic.JOINS, 1);
    }

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Collection<StatisticEntry> entries = this.statisticRegistry.getAll(player.getUniqueId());

        for (StatisticEntry entry : entries) {
            this.statisticRepository.updateEntry(entry);
        }
    }
}
