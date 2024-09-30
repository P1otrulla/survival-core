package pl.crafthype.core.feature.statistic;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface StatisticRepository {

    void updateEntry(StatisticEntry entry);

    CompletableFuture<Collection<StatisticEntry>> getAll();

}
