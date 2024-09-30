package pl.crafthype.core.user;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.crafthype.core.database.Refreshable;

import java.util.List;

public class UserController implements Listener {

    private final UserRepository userRepository;
    private final List<Refreshable> refreshable;
    private final UserService userService;

    public UserController(UserRepository userRepository, List<Refreshable> refreshable, UserService userService) {
        this.userRepository = userRepository;
        this.refreshable = refreshable;
        this.userService = userService;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        User user = this.userService.findOrCreate(player.getUniqueId());

        user.updateName(player.getName());

        this.refreshable.forEach(repo -> repo.refresh(player.getUniqueId()));

        this.userRepository.save(user);
    }

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        this.userRepository.save(this.userService.findOrCreate(event.getPlayer().getUniqueId()));
    }
}
