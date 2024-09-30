package pl.crafthype.core.controller;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.crafthype.core.shared.InformationBookService;

public class PlayerFirstTimeController implements Listener {

    private final InformationBookService bookService;

    public PlayerFirstTimeController(InformationBookService bookService) {
        this.bookService = bookService;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) {
            return;
        }

        //this.bookService.openBook(player);

        Location spawn = player.getWorld().getSpawnLocation();

        player.teleport(spawn);
    }
}
