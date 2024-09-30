package pl.crafthype.core.feature.border;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import pl.crafthype.core.scheduler.Task;

import java.time.Duration;
import java.time.Instant;

@Contextual
public class BukkitBorder {

    private String world;
    private Duration duration;
    private Instant lastExpand;
    private int startSize;
    private int expandSize;
    private int currentSize;

    @Exclude
    private Task borderTask;

    public BukkitBorder(String world, Duration duration, Instant lastExpand, int startSize, int expandSize, int currentSize) {
        this.world = world;
        this.duration = duration;
        this.lastExpand = lastExpand;
        this.startSize = startSize;
        this.expandSize = expandSize;
        this.currentSize = currentSize;
    }

    public BukkitBorder() {

    }

    public String world() {
        return this.world;
    }

    public Duration duration() {
        return this.duration;
    }

    public Instant lastExpand() {
        return this.lastExpand;
    }

    public void updateLastExpand(Instant lastExpand) {
        this.lastExpand = lastExpand;
    }

    public int startSize() {
        return this.startSize;
    }

    public int expandSize() {
        return this.expandSize;
    }

    public int currentSize() {
        return this.currentSize;
    }

    public void updateCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public Task borderTask() {
        return this.borderTask;
    }

    public void updateBorderTask(Task borderTask) {
        this.borderTask = borderTask;
    }
}
