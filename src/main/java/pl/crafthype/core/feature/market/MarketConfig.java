package pl.crafthype.core.feature.market;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.notification.Notification;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MarketConfig implements ReloadableConfig {

    public Notification negativeAmount = Notification.chat("&cNie możesz wystawić przedmiotu z ujemną ilością!");
    public Notification negativeBalance = Notification.chat("&cNie możesz wystawić przedmiotu z ujemną ceną!");
    public Notification maxAmount = Notification.chat("&cNie możesz wystawić więcej niż 64!");
    public Notification notEnoughtAmount = Notification.chat("&cNie masz wystarczającej ilości tego przedmiotu!");
    public Notification notItemInHand = Notification.chat("&cMusisz trzymać przedmiot w ręce!");
    public Notification itemPlaced = Notification.chat("&7Wystawiłeś przedmiot &e{ITEM} &7na rynek w ilości &e{AMOUNT} &7za &e{PRICE}!");
    public Notification itemBought = Notification.chat("&7Kupiłeś przedmiot &e{ITEM} &7w ilości: &e{AMOUNT} &7za &e{PRICE} &7od &e{SELLER}&7!");
    public Notification yourItemBought = Notification.chat("&7Ktoś kupił twój przedmiot &e{ITEM} &7za &e{PRICE}&7!");
    public Notification notExpiredItems = Notification.chat("&cNie masz żadnych przedmiotów, które wygasły!");
    public Notification changedSort = Notification.chat("&7Zmieniłeś sortowanie na &e{SORT-TYPE}&7!");
    public Notification notEnoughtMoney = Notification.chat("&cNie masz wystarczającej ilości pieniędzy! &7(Brakuje: {MISSING}, Masz: {BALANCE}, Item kosztuje: {PRICE})");
    public Notification alreadySold = Notification.chat("&cTen przedmiot został właśnie sprzedany!");
    public Notification takenDownItem = Notification.chat("&aPomyślnie zdjełeś swój item z rynku!");
    public Notification itemExpired = Notification.chat("&cTwój przedmiot z rynku wygasł!");

    public Duration itemExpire = Duration.ofDays(3);

    public Menu menu = new Menu();

    public ExpiredMenu expiredMenu = new ExpiredMenu();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "market.yml");
    }

    @Contextual
    public static class ExpiredMenu {

        public String title = "&6&lMarket &8| &7Przedmioty wygasłe";
        public int rows = 5;

        public ConfigItem back = ConfigItem.builder()
            .material(Material.OAK_FENCE)
            .name("&cPowrót")
            .slot(40)
            .build();
    }

    @Contextual
    public static class Menu {

        public String title = "&6&lMarket &8| &7(&f{PAGE}/{PAGES}&7)";
        public int rows = 6;

        public int itemsPerPage = 21;

        public ConfigItem filler = ConfigItem.builder()
            .material(Material.BLACK_STAINED_GLASS_PANE)
            .name(" ")
            .build();

        public List<String> lore = Arrays.asList(
            "",
            "&8» &2Cena: &f{PRICE}",
            "&8» &2Sprzedawca: &f{SELLER}",
            "&8» &2Wygasa za: &f{EXPIRE}",
            "",
            "&7Kliknij aby kupić przedmiot!"
        );

        public List<String> confirmLore = Arrays.asList(
            "",
            "&8» &2Cena: &f{PRICE}",
            "&8» &2Sprzedawca: &f{SELLER}",
            "&8» &2Wygasa za: &f{EXPIRE}"
        );

        public String placeholderTake = "&aKliknij, aby usunąć swój przedmot!";
        public String placeholderBuy = "&eKliknij, aby kupić!";

        public List<Integer> fillerSlots = Arrays.asList(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 37, 38, 39, 40, 41, 42,
            43, 44, 45, 47, 51, 53
        );

        public Map<Integer, ConfigItem> decorations = Map.of(
            0, ConfigItem.builder().material(Material.RED_STAINED_GLASS_PANE).name(" ").build(),
            8, ConfigItem.builder().material(Material.RED_STAINED_GLASS_PANE).name(" ").build(),
            36, ConfigItem.builder().material(Material.RED_STAINED_GLASS_PANE).name(" ").build(),
            44, ConfigItem.builder().material(Material.RED_STAINED_GLASS_PANE).name(" ").build()
        );

        public Map<MarketSortType, String> sortTypes = Map.of(
            MarketSortType.MOST_EXPENSIVE, "&eNajdroższe",
            MarketSortType.CHEAPEST, "&eNajtańsze",
            MarketSortType.NEWEST, "&eNajnowsze",
            MarketSortType.OLDEST, "&eNajstarsze"
        );

        public ConfigItem expiredItems = ConfigItem.builder()
            .material(Material.CLOCK)
            .name("&ePrzedmioty wygasłe")
            .lore(
                "",
                "&8» &7Kliknij aby zobaczyć przedmioty,",
                "       &7które wygasły"
            )
            .slot(46)
            .build();

        public ConfigItem previousPage = ConfigItem.builder()
            .material(Material.RED_DYE)
            .name("&cPoprzednia strona")
            .slot(48)
            .build();

        public ConfigItem sorter = ConfigItem.builder()
            .material(Material.COMPARATOR)
            .name("&eSortowanie")
            .lore(
                "&7Kliknij aby zmienić sortowanie",
                "",
                "&7Obecne sortowanie: &e{SORT-TYPE}"
            )
            .slot(49)
            .build();

        public ConfigItem nextPage = ConfigItem.builder()
            .material(Material.LIME_DYE)
            .name("&aNastępna strona")
            .slot(50)
            .build();

        public ConfigItem info = ConfigItem.builder()
            .material(Material.NETHER_STAR)
            .name("&eInformacje")
            .lore(
                "",
                "&8» &7Aby wystawić przedmiot użyj komendy:",
                "       &e/market wystaw <ilość> <cena>"
            )
            .slot(52)
            .build();

    }
}
