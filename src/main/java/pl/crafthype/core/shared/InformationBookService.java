package pl.crafthype.core.shared;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import pl.crafthype.core.config.implementation.PluginConfig;
import pl.crafthype.core.scheduler.Scheduler;

import java.time.Duration;

public class InformationBookService {

    private final AudienceProvider provider;
    private final MiniMessage miniMessage;
    private final Scheduler scheduler;
    private final PluginConfig config;

    public InformationBookService(AudienceProvider provider, MiniMessage miniMessage, Scheduler scheduler, PluginConfig config) {
        this.provider = provider;
        this.miniMessage = miniMessage;
        this.scheduler = scheduler;
        this.config = config;
    }

    public void openBook(Player player) {
        Audience audience = this.provider.player(player.getUniqueId());

        Book book = Book.builder()
            .title(this.miniMessage.deserialize(this.config.firstTimeBook.title))
            .author(this.miniMessage.deserialize(this.config.firstTimeBook.author))
            .pages(this.config.firstTimeBook.pages.stream().map(this.miniMessage::deserialize).toList())
            .build();

        this.scheduler.laterSync(() -> audience.openBook(book), Duration.ofMillis(10));
    }
}
