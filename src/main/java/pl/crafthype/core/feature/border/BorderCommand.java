package pl.crafthype.core.feature.border;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.suggestion.Suggest;
import org.bukkit.World;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;
import pl.crafthype.core.notification.NotificationBroadcaster;
import pl.crafthype.core.scheduler.Scheduler;
import pl.crafthype.core.scheduler.Task;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Route(name = "border", aliases = "granica-mapy")
public class BorderCommand {

    private final NotificationBroadcaster announcer;
    private final BorderConfig borderConfig;
    private final Scheduler scheduler;

    public BorderCommand(NotificationBroadcaster announcer, BorderConfig borderConfig, Scheduler scheduler) {
        this.announcer = announcer;
        this.borderConfig = borderConfig;
        this.scheduler = scheduler;
    }

    @Execute(required = 0)
    void execute(Player sender) {
        World world = sender.getWorld();

        Optional<BukkitBorder> borderOptional = this.borderConfig.findBorder(world.getName());

        if (borderOptional.isEmpty()) {
            this.announcer.sendAnnounce(sender, this.borderConfig.borderNonSet);

            return;
        }

        this.borderConfig.borderInfo.forEach(notification -> this.announcer.sendAnnounce(sender, notification, BorderUtils.format(borderOptional.get())));
    }

    @Execute(route = "create", required = 3)
    @Permission("chCore.border.create")
    void create(Player sender, @Arg int startSize, @Arg int expandSize, @Arg Duration time) {
        if (this.borderConfig.findBorder(sender.getWorld().getName()).isPresent()) {
            this.announcer.sendAnnounce(sender, this.borderConfig.borderAlreadySet);
            return;
        }

        BukkitBorder border = new BukkitBorder(sender.getWorld().getName(), time, Instant.now(), startSize, expandSize, startSize);

        this.borderConfig.addBorder(border);

        BorderTask borderTask = new BorderTask(this.announcer, this.borderConfig, border);

        Task task = this.scheduler.timerSync(borderTask, Duration.ofSeconds(5), border.duration());

        border.updateBorderTask(task);

        BorderUtils.createWorldBorder(sender.getWorld(), border.startSize());

        this.borderConfig.updateBorder(border);

        this.announcer.sendAnnounce(sender, this.borderConfig.borderCreated, BorderUtils.format(border));
    }

    @Execute(route = "remove", required = 1)
    @Permission("chCore.border.remove")
    void remove(Player sender, @Arg @Suggest({ "world", "world_nether" }) String world) {
        Optional<BukkitBorder> bukkitBorder = this.borderConfig.findBorder(world);

        if (bukkitBorder.isEmpty()) {
            this.announcer.sendAnnounce(sender, this.borderConfig.borderNonSet);

            return;
        }

        BukkitBorder border = bukkitBorder.get();

        border.borderTask().cancel();

        BorderUtils.removeWorldBorder(border);

        this.borderConfig.removeBorder(border);

        this.announcer.sendAnnounce(sender, this.borderConfig.borderRemoved, new Formatter().register("{WORLD}", world));
    }
}
