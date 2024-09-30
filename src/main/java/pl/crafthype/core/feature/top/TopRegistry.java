package pl.crafthype.core.feature.top;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.crafthype.core.feature.statistic.Statistic;
import pl.crafthype.core.feature.statistic.StatisticRegistry;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopRegistry.class);

    private final Map<Statistic, List<TopEntry>> topEntries = new HashMap<>();
    private final StatisticRegistry statisticRegistry;
    private final TopSettings topSettings;
    private final TopService topService;

    private Instant lastUpdate = Instant.now();

    public TopRegistry(StatisticRegistry statisticRegistry, TopSettings topSettings, TopService topService) {
        this.statisticRegistry = statisticRegistry;
        this.topSettings = topSettings;
        this.topService = topService;
    }

    void sort() {
        this.topEntries.clear();

        this.topService.tops().forEach(top -> {
            Statistic statistic = top.statistic();

            if (statistic == null) {
                LOGGER.warn("Can't create top with name {}!", top.name());
                LOGGER.warn("Statistic not found!");

                return;
            }

            this.statisticRegistry.getAll(statistic).forEach(entry -> this.topEntries.computeIfAbsent(statistic, i -> new ArrayList<>())
                .add(TopEntry.create(statistic, entry.owner(), entry.value(), -1)));
        });
    }

    public List<TopEntry> findBestOf(Statistic statistic, int count) {
        if (!this.topEntries.containsKey(statistic)) {
            return Collections.emptyList();
        }

        return this.topEntries.get(statistic)
            .stream()
            .sorted((o1, o2) -> Long.compare(o2.value(), o1.value()))
            .limit(count)
            .toList();
    }

    public void update() {
        this.lastUpdate = Instant.now();

        this.sort();
    }

    Instant nextUpdateTime() {
        return this.lastUpdate.plus(this.topSettings.topUpdateInterval());
    }

    Duration nextUpdate() {
        return Duration.between(Instant.now(), this.nextUpdateTime());
    }
}
