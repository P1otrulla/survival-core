package pl.crafthype.core.feature.statistic;

import org.bukkit.Server;

public class StatisticSpentTimeTask implements Runnable {

    private final StatisticRegistry statisticRegistry;
    private final Server server;

    public StatisticSpentTimeTask(StatisticRegistry statisticRegistry, Server server) {
        this.statisticRegistry = statisticRegistry;
        this.server = server;
    }

    @Override
    public void run() {
        this.server.getOnlinePlayers().forEach(player -> this.statisticRegistry.add(player.getUniqueId(), Statistic.SPENT_TIME, 1));
    }
}
