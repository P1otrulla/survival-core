package pl.crafthype.core.feature.statistic;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collection;

public class StatisticUpdateTask implements Runnable {

    private final StatisticRepository repository;
    private final StatisticRegistry registry;
    private final Server server;

    public StatisticUpdateTask(StatisticRepository repository, StatisticRegistry registry, Server server) {
        this.repository = repository;
        this.registry = registry;
        this.server = server;
    }

    @Override
    public void run() {
        for (Player player : this.server.getOnlinePlayers()) {
            Collection<StatisticEntry> entries = this.registry.getAll(player.getUniqueId());

            for (StatisticEntry entry : entries) {
                this.repository.updateEntry(entry);
            }
        }
    }
}
