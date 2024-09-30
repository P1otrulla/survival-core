package pl.crafthype.core.controller;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.crafthype.core.scheduler.Scheduler;

import java.time.Duration;

public class PlayerDeathController implements Listener {

    private final Scheduler scheduler;

    public PlayerDeathController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @EventHandler
    void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        this.scheduler.laterSync(() -> player.spigot().respawn(), Duration.ofMillis(10));
    }
}
