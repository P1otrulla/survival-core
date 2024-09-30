package pl.crafthype.core.feature.top;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.feature.statistic.Statistic;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TopConfig implements ReloadableConfig, TopService, TopSettings {

    private Menu menu = new Menu();

    private Duration updateInterval = Duration.ofMinutes(5);

    private int topSize = 10;

    @Description("# {TOP-INDEX} - miejsce w rankingu (INDEX max do topSize)")
    @Description("# Statystki: playersKilled, deaths, spentTime, harvestedCrops, breakStone, placedBlocks, killedMobs, damageDealt, distanceTravelled, joins")
    private Map<String, BukkitTop> tops = Map.of(
        "kills", new BukkitTop("Zabójstwa", Statistic.PLAYERS_KILLED, ConfigItem.builder()
            .material(Material.DIAMOND_SWORD)
            .slot(11)
            .name("&6Topka Zabójstw")
            .lore(Arrays.asList(
                "&7Taka se kurwa topka ten tego",
                "{TOP-1}",
                "{TOP-2}",
                "{TOP-3}",
                "{TOP-4}",
                "{TOP-5}",
                "{TOP-6}",
                "{TOP-7}",
                "{TOP-8}",
                "{TOP-9}",
                "{TOP-10}",
                "&cCzy wiedziałeś że... &7takie topki są fajne? :D ~Copilot",
                ""
            )).build()),
        "deaths", new BukkitTop("Śmierci", Statistic.DEATHS, ConfigItem.builder()
            .material(Material.RED_DYE)
            .slot(12)
            .name("&6Topka Śmierci")
            .lore(Arrays.asList(
                "&7Taka se kurwa topka ten tego",
                "{TOP-1}",
                "{TOP-2}",
                "{TOP-3}",
                "{TOP-4}",
                "{TOP-5}",
                "{TOP-6}",
                "{TOP-7}",
                "{TOP-8}",
                "{TOP-9}",
                "{TOP-10}",
                "&cCzy wiedziałeś że... &7takie topki są fajne? :D ~Copilot"
            )).build()),
        "playtime", new BukkitTop("Czas gry", Statistic.SPENT_TIME, ConfigItem.builder()
            .material(Material.CLOCK)
            .slot(13)
            .name("&6Topka spędzonego czasu")
            .lore(Arrays.asList(
                "&7Taka se kurwa topka ten tego",
                "{TOP-1}",
                "{TOP-2}",
                "{TOP-3}",
                "{TOP-4}",
                "{TOP-5}",
                "{TOP-6}",
                "{TOP-7}",
                "{TOP-8}",
                "{TOP-9}",
                "{TOP-10}",
                "&cCzy wiedziałeś że... &7takie topki są fajne? :D ~Copilot"
            )).build())
    );

    @Override
    public Collection<Top> tops() {
        return Collections.unmodifiableCollection(this.tops.values());
    }

    @Override
    public int topSize() {
        return this.topSize;
    }

    @Override
    public String topTitle() {
        return this.menu.title;
    }

    @Override
    public int topRows() {
        return this.menu.rows;
    }

    @Override
    public Duration topUpdateInterval() {
        return this.updateInterval;
    }

    @Override
    public String entryFormat() {
        return this.menu.entryFormat;
    }

    @Override
    public String entryEmptyFormat() {
        return this.menu.entryEmptyFormat;
    }

    @Override
    public ConfigItem filler() {
        return this.menu.filler;
    }

    @Override
    public ConfigItem close() {
        return this.menu.close;
    }

    @Override
    public ConfigItem nextUpdate() {
        return this.menu.nextUpdate;
    }

    @Override
    public ConfigItem info() {
        return this.menu.info;
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "tops.yml");
    }

    @Contextual
    public static class Menu {

        private String title = "&6Topki serwerowe";
        private int rows = 4;

        private ConfigItem filler = ConfigItem.builder()
            .material(Material.GRAY_STAINED_GLASS_PANE)
            .name("&8-")
            .build();

        private ConfigItem close = ConfigItem.builder()
            .material(Material.BARRIER)
            .slot(26)
            .name("&cZamknij!")
            .lore(List.of("&7Kliknij aby zamknąć mainMenu"))
            .build();

        private ConfigItem info = ConfigItem.builder()
            .material(Material.PAPER)
            .name("&eTwoje statystyki:")
            .lore(
                "",
                "&7Dołączenia do serwera: &e{playersKilled}",
                "&7Przebyty dystans: &e{distanceTravelled}",
                "&7Postawione bloki: &e{placedBlocks}",
                "&7Zadane obrażenia: &e{damageDealt}",
                "&7Wykopany kamień: &e{breakStone}",
                "&7Zabici gracze: &e{playersKilled}",
                "&7Spędzony czas: &e{spentTime}",
                "&7Zebrane plony: &e{harvestedCrops}",
                "&7Zabite moby: &e{killedMobs}",
                "&7Śmierci: &e{deaths}",
                ""
            )
            .slot(24)
            .build();

        private ConfigItem nextUpdate = ConfigItem.builder()
            .material(Material.CLOCK)
            .slot(25)
            .name("&6Następna aktualizacja")
            .lore(List.of("&7Nastepna aktualizacja: &e{TIME}"))
            .build();

        private String entryFormat = "&7{POSITION}. &f{NAME} &7- &e{VALUE}";
        private String entryEmptyFormat = "&7Brak danych!";
    }

}
