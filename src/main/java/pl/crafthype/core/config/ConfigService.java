package pl.crafthype.core.config;

import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.reflect.Visibility;
import org.bukkit.enchantments.Enchantment;
import pl.crafthype.core.config.composer.DurationComposer;
import pl.crafthype.core.config.composer.EnchantmentComposer;
import pl.crafthype.core.config.composer.InstantComposer;
import pl.crafthype.core.config.composer.UUIDComposer;
import pl.crafthype.core.notification.Notification;
import pl.crafthype.core.notification.NotificationComposer;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ConfigService {

    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withMemberResolver(Visibility.PRIVATE)
        .withComposer(Notification.class, new NotificationComposer())
        .withComposer(Enchantment.class, new EnchantmentComposer())
        .withComposer(Duration.class, new DurationComposer())
        .withComposer(Instant.class, new InstantComposer())
        .withComposer(UUID.class, new UUIDComposer())
        .build();

    private final Set<ReloadableConfig> configs = new HashSet<>();
    private final File dataFolder;

    public ConfigService(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public <T extends ReloadableConfig> T load(T config) {
        cdn.load(config.resource(this.dataFolder), config)
            .orThrow(RuntimeException::new);

        cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);

        this.configs.add(config);

        return config;
    }

    public <T extends ReloadableConfig> T save(T config) {
        cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);

        this.configs.add(config);

        return config;
    }

    public void reload() {
        for (ReloadableConfig config : this.configs) {
            load(config);
        }
    }
}