package pl.crafthype.core.feature.top;

public class TopTask implements Runnable {

    private final TopRegistry registry;
    private final TopMenu menu;

    public TopTask(TopRegistry registry, TopMenu menu) {
        this.registry = registry;
        this.menu = menu;
    }

    @Override
    public void run() {
        this.registry.update();
        this.menu.updateMenu();
    }
}
