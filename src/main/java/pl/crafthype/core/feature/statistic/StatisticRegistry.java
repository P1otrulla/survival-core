package pl.crafthype.core.feature.statistic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class StatisticRegistry {

    private final Map<UUID, StatisticOwnedPart> statisticsByOwner = new HashMap<>();

    public void add(UUID owner, Statistic statistic, long value) {
        StatisticOwnedPart part = this.statisticsByOwner.computeIfAbsent(owner, uuid -> new StatisticOwnedPart(owner));

        long currentValue = part.get(statistic).value();
        long newValue = currentValue + value;

        part.add(statistic, new StatisticEntry(owner, statistic, newValue));
    }

    public void addCache(UUID owner, Statistic statistic, long value) {
        StatisticOwnedPart part = this.statisticsByOwner.computeIfAbsent(owner, uuid -> new StatisticOwnedPart(owner));

        part.add(statistic, new StatisticEntry(owner, statistic, value));
    }

    public StatisticEntry get(UUID owner, Statistic statistic) {
        StatisticOwnedPart part = this.statisticsByOwner.computeIfAbsent(owner, uuid -> new StatisticOwnedPart(owner));

        return part.get(statistic);
    }

    public Collection<StatisticEntry> getAll(UUID owner) {
        StatisticOwnedPart part = this.statisticsByOwner.computeIfAbsent(owner, uuid -> new StatisticOwnedPart(owner));
        List<Statistic> values = Statistic.values();

        Set<StatisticEntry> entries = new HashSet<>();

        for (Statistic statistic : values) {
            entries.add(part.get(statistic));
        }

        return Collections.unmodifiableCollection(entries);
    }

    public Collection<StatisticEntry> getAll(Statistic statistic) {
        return this.statisticsByOwner.values().stream().map(part -> part.get(statistic)).toList();
    }

    public void resetStatistic(UUID owner) {
        this.statisticsByOwner.remove(owner);
    }

    static class StatisticOwnedPart {
        private final UUID owner;
        private final Map<Statistic, StatisticEntry> statistics = new HashMap<>();

        StatisticOwnedPart(UUID owner) {
            this.owner = owner;
        }

        void add(Statistic statistic, StatisticEntry value) {
            this.statistics.put(statistic, value);
        }

        StatisticEntry get(Statistic statistic) {
            return this.statistics.getOrDefault(statistic, new StatisticEntry(this.owner, statistic, 0));
        }
    }
}
