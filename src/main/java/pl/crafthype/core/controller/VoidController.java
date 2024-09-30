package pl.crafthype.core.controller;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidController implements Listener {

    private static final int VOID_Y = 40;
    private static final String TELEPORT_WORLD = "Spawn";

    private final Server server;

    public VoidController(Server server) {
        this.server = server;
    }

    @EventHandler
    void onPlayerVoid(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getName().equals(TELEPORT_WORLD) && (player.getLocation().getY() < VOID_Y)) {
            player.setFallDistance(0);
            this.server.dispatchCommand(this.server.getConsoleSender(), "spawn " + player.getName());
        }
    }
}