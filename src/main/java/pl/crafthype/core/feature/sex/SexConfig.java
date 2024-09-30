package pl.crafthype.core.feature.sex;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.notification.Notification;

import java.io.File;
import java.util.Map;

public class SexConfig implements ReloadableConfig {

    public Notification choosedSex = Notification.chat("&7Wybrano płeć!");

    public String noSex = "&cBrak";

    public Map<String, Sex> sexes = Map.of(
        "male", Sex.create("male", "&9M"),
        "female", Sex.create("female", "&dK")
    );

    public Menu menu = new Menu();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "sex.yml");
    }

    @Contextual
    public static class Menu {

        public String title = "&6Wybierz płeć";
        public int rows = 3;

        public ConfigItem filler = ConfigItem.builder()
            .material(Material.GRAY_STAINED_GLASS_PANE)
            .name(" ")
            .build();

        public Map<String, ConfigItem> icons = Map.of(
            "male", ConfigItem.builder()
                .material(Material.PLAYER_HEAD)
                .name("&6Płeć: &9Mężczyzna")
                .texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxNDczNGNjMzE4NzZmODYyOTI4N2IzYTZlOWI2YzliMGMxYmQxYmIzNmU0YzcxMzRiZjEyY2Q4NjU1YzFkYiJ9fX0=")
                .slot(13)
                .build(),

            "female", ConfigItem.builder()
                .material(Material.PLAYER_HEAD)
                .name("&6Płeć: &dKobieta")
                .texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDZjZTBlNjcyMjEwNWQxYmIxMDVjMTg1MDM0NGRiZGUzY2Q2OTU0Y2M2NWE2YzVjMzk4OGVhOTc4MjczYThlZCJ9fX0=")
                .slot(15)
                .build()
        );

        public Map<Integer, ConfigItem> decorations = Map.of(
            0, ConfigItem.builder().material(Material.RED_STAINED_GLASS_PANE).name(" ").build(),
            8, ConfigItem.builder().material(Material.RED_STAINED_GLASS_PANE).name(" ").build(),
            18, ConfigItem.builder().material(Material.RED_STAINED_GLASS_PANE).name(" ").build(),
            26, ConfigItem.builder().material(Material.RED_STAINED_GLASS_PANE).name(" ").build()
        );
    }
}
