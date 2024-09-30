package pl.crafthype.core.scheduler;

public interface Task {

    void cancel();

    boolean isCanceled();

    boolean isAsync();

}