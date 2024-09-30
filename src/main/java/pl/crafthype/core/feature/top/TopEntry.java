package pl.crafthype.core.feature.top;

import pl.crafthype.core.feature.statistic.Statistic;

import java.util.UUID;

class TopEntry {

    private final Statistic statistic;
    private final UUID owner;
    private final long value;
    private final int position;

    private TopEntry(Statistic statistic, UUID owner, long value, int position) {
        this.statistic = statistic;
        this.owner = owner;
        this.value = value;
        this.position = position;
    }

    public static TopEntry create(Statistic statistic, UUID owner, long value, int position) {
        return new TopEntry(statistic, owner, value, position);
    }

    public Statistic statistic() {
        return this.statistic;
    }

    public UUID owner() {
        return this.owner;
    }

    public int position() {
        return this.position;
    }

    public long value() {
        return this.value;
    }
}
