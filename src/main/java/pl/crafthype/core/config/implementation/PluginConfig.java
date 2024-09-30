package pl.crafthype.core.config.implementation;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.config.item.ConfigItem;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class PluginConfig implements ReloadableConfig {

    public ConfigItem autoLapis = ConfigItem.builder()
        .material(Material.LAPIS_LAZULI)
        .name("&aAutomatyczny lapis")
        .lore(
            "&7Lapis jest uzupełniany automatycznie",
            "&7nie zostanie on usunięty."
        )
        .amount(64)
        .build();

    public FirstTimeBook firstTimeBook = new FirstTimeBook();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }

    @Contextual
    public static class FirstTimeBook {

        public String title = "Witaj na serwerze!";
        public String author = "Crafthype";
        public List<String> pages = Collections.singletonList("<rgb>Witaj na serwerze! /n <red> D jak dycha");
    }
}
