package pl.crafthype.core.feature.achievement;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.feature.statistic.Statistic;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AchievementConfig implements ReloadableConfig, AchievementService, AchievementSettings {

    private String gainedAchievement = "&cOdebrałeś już te osiągnięcie!";

    private String notAchivied = "&cNie ukończyłeś tego osiągnięcia!";

    private String achieved = "&fOdebrałeś nagrodę za osiągniecie: &a{ACHIEVEMENT}";

    private String achievementBrodcast = "&fGracz &a{PLAYER} &fukończył osiągnięcie: &a{ACHIEVEMENT}";

    private String placeholderTake = "&aKliknij, aby odebrać nagrodę!";
    private String placeholderAchieved = "&cOdebrałeś już tą nagrodę!";
    private String placeholderNotAchieved = "&cDokończ osiągnięcie aby odebrać nagrodę!";

    private String title = "&6Osiągnięcia";
    private int rows = 4;

    private List<String> gainedLore = Collections.singletonList("&aTe osiągnięcie zostało już przez Ciebie zdobyte!");

    private ConfigItem back = ConfigItem.builder()
        .material(Material.OAK_FENCE)
        .name("&cPowrot")
        .slot(31)
        .build();

    private ConfigItem close = ConfigItem.builder()
        .material(Material.BARRIER)
        .name("&cZamknij")
        .slot(31)
        .build();

    private Map<String, AchievementCategory> achievements = Map.of(
        "spentTime", new AchievementCategory(
            "Spedzony czas",

            ConfigItem.builder()
                .material(Material.CLOCK)
                .slot(10)
                .name("&aSpedzony czas")
                .lore("&7Spedzony czas, kliknij!")
                .build(),

            Statistic.SPENT_TIME,

            "&6Spedzony czas",
            4,

            Map.of("spentTime1", new Achievement(
                    "Spedzony czas I",
                    Statistic.SPENT_TIME,
                    60,
                    ConfigItem.builder()
                        .material(Material.CLOCK)
                        .slot(10)
                        .name("&aSpedzony czas I")
                        .lore("&6Twój postęp: {VALUE} &7({PROGRESS}%)")
                        .build(),
                    100,
                    Collections.singletonList("give {PLAYER} diamond 1"),
                    Collections.singletonList(ConfigItem.builder()
                        .material(Material.DIAMOND)
                        .build()
                    )
                )
            )),

        "harvestedCrops", new AchievementCategory(
            "Zebrane plony",

            ConfigItem.builder()
                .material(Material.WHEAT_SEEDS)
                .slot(11)
                .name("&aZebrane plony")
                .lore("&7Zebrane plony, kliknij!")
                .build(),

            Statistic.HARVESTED_CROPS,

            "&6Zebrane plony",
            4,

            Map.of("harvestedCrops1", new Achievement(
                    "Zebrane plony I",
                    Statistic.HARVESTED_CROPS,
                    10,
                    ConfigItem.builder()
                        .material(Material.CLOCK)
                        .slot(11)
                        .name("&aZebrane plony I")
                        .lore("&6Twój postęp: {VALUE}/{MAX_VALUE} &7({PROGRESS}%)")
                        .build(),
                    100,
                    Collections.singletonList("give {PLAYER} diamond 1"),
                    Collections.singletonList(ConfigItem.builder()
                        .material(Material.DIAMOND)
                        .build()
                    )
                )
            ))
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "achievements.yml");
    }

    @Override
    public int findAchievementsSize(Statistic statistic) {
        return this.categories()
            .stream()
            .filter(category -> category.statistic().equals(statistic))
            .mapToInt(category -> category.achievements().size())
            .sum();
    }

    @Override
    public Collection<AchievementCategory> categories() {
        return this.achievements.values();
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public int rows() {
        return this.rows;
    }

    @Override
    public List<String> gainedLore() {
        return this.gainedLore;
    }

    @Override
    public String gainedAchievement() {
        return this.gainedAchievement;
    }

    @Override
    public String notAchieved() {
        return this.notAchivied;
    }

    @Override
    public String achieved() {
        return this.achieved;
    }

    @Override
    public String achievementBroadcast() {
        return this.achievementBrodcast;
    }

    @Override
    public ConfigItem backItem() {
        return this.back;
    }

    @Override
    public ConfigItem closeItem() {
        return this.close;
    }

    @Override
    public int sizeOfAchievements() {
        int size = 0;

        for (AchievementCategory category : this.achievements.values()) {
            size += category.achievements().size();
        }

        return size;
    }

    @Override
    public String placeholderTake() {
        return this.placeholderTake;
    }

    @Override
    public String placeholderNotAchieved() {
        return this.placeholderNotAchieved;
    }

    @Override
    public String placeholderAchieved() {
        return this.placeholderAchieved;
    }
}
