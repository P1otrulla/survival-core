package pl.crafthype.core.feature.border;

import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import pl.crafthype.core.config.ConfigService;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.notification.Notification;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BorderConfig implements ReloadableConfig, BorderService {

    @Exclude
    private final ConfigService configService;

    public List<Notification> borderInfo = Arrays.asList(
        Notification.chat("&6Początkowy border mapy wynosi: &c{START-SIZE} &6kratek"),
        Notification.chat("&6Aktualny border mapy wynosi: &c{CURRENT-SIZE} &6kratek"),
        Notification.chat("&6Border powiekszy się o &c{EXPAND-SIZE} &6kratek za &c{TIME}"),
        Notification.chat("&6Border powiekszy się do &c{NEXT-SIZE} &6kratek")
    );
    public Notification borderExpanded = Notification.chat("&6Border powiekszył się do &c{CURRENT-SIZE} &6kratek!");
    public Notification borderNonSet = Notification.chat("&cGranica nie jest ustawiona na tej mapie!");
    public Notification borderAlreadySet = Notification.chat("&cGranica jest juz ustawiona na tej mapie!");
    public Notification borderCreated = Notification.chat("&aUstawiono granice na swiecie &f{WORLD}&a, na &f{START-SIZE}&a, powieksza sie o &f{EXPAND-SIZE}&a, co &f{TIME}&a!");
    public Notification borderRemoved = Notification.chat("&aUsunieto granice na swiecie &f{WORLD}&a!");
    public List<BukkitBorder> borders = new ArrayList<>();

    public BorderConfig(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void addBorder(BukkitBorder border) {
        List<BukkitBorder> borderList = new ArrayList<>(this.borders);

        borderList.add(border);

        this.saveBorder(borderList);
    }

    @Override
    public void removeBorder(BukkitBorder world) {
        List<BukkitBorder> borderList = new ArrayList<>(this.borders);

        borderList.remove(world);

        this.saveBorder(borderList);
    }

    @Override
    public void updateBorder(BukkitBorder border) {
        List<BukkitBorder> borderList = new ArrayList<>(this.borders);

        this.borders.removeIf(b -> b.world().equals(border.world()));
        this.borders.add(border);

        this.saveBorder(borderList);
    }

    @Override
    public Optional<BukkitBorder> findBorder(String world) {
        return this.borders.stream().filter(border -> border.world().equals(world)).findFirst();
    }

    @Override
    public Collection<BukkitBorder> borders() {
        return Collections.unmodifiableCollection(this.borders);
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "border.yml");
    }

    void saveBorder(List<BukkitBorder> borders) {
        this.borders = new ArrayList<>(borders);

        this.configService.save(this);
    }

}
