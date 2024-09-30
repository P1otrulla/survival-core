package pl.crafthype.core.feature.border;

import panda.utilities.text.Formatter;
import pl.crafthype.core.notification.NotificationBroadcaster;
import pl.crafthype.core.shared.DurationUtil;

import java.time.Duration;
import java.time.Instant;

public class BorderTask implements Runnable {

    private final NotificationBroadcaster announcer;
    private final BorderConfig borderConfig;
    private final BukkitBorder border;

    public BorderTask(NotificationBroadcaster announcer, BorderConfig borderConfig, BukkitBorder border) {
        this.announcer = announcer;
        this.borderConfig = borderConfig;
        this.border = border;
    }

    @Override
    public void run() {
        BorderUtils.createWorldBorder(this.border);

        this.borderConfig.updateBorder(this.border);

        Formatter formatter = new Formatter()
            .register("{START-SIZE}", this.border.startSize())
            .register("{CURRENT-SIZE}", this.border.currentSize())
            .register("{EXPAND-SIZE}", this.border.expandSize())
            .register("{TIME}", DurationUtil.format(Duration.between(this.border.lastExpand(), Instant.now())))
            .register("{WAS-SIZE}", this.border.currentSize() - this.border.expandSize())
            .register("{NEXT-SIZE}", this.border.currentSize() + this.border.expandSize());

        this.announcer.sendAnnounceAll(this.borderConfig.borderExpanded, formatter);
    }
}
