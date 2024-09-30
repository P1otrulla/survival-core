package pl.crafthype.core.feature.market;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.utilities.text.Formatter;
import pl.crafthype.core.money.MoneyService;
import pl.crafthype.core.notification.NotificationBroadcaster;
import pl.crafthype.core.shared.DurationUtil;
import pl.crafthype.core.shared.ItemUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class MarketMenu {

    private final Map<UUID, MarketSortType> sortByUuids = new HashMap<>();

    private final MarketExpiredService expiredService;
    private final NotificationBroadcaster broadcaster;
    private final MoneyService moneyService;
    private final MiniMessage miniMessage;
    private final MarketService service;
    private final MarketConfig config;
    private final Server server;

    public MarketMenu(MarketExpiredService expiredService, NotificationBroadcaster broadcaster, MoneyService moneyService, MiniMessage miniMessage, MarketService service, MarketConfig config, Server server) {
        this.expiredService = expiredService;
        this.broadcaster = broadcaster;
        this.moneyService = moneyService;
        this.miniMessage = miniMessage;
        this.service = service;
        this.config = config;
        this.server = server;
    }

    public void open(Player player) {
        this.open(player, this.sortByUuids.getOrDefault(player.getUniqueId(), MarketSortType.NEWEST));
    }

    public void open(Player player, MarketSortType sortType) {
        MarketConfig.Menu menu = this.config.menu;

        PaginatedGui gui = Gui.paginated()
            .title(this.miniMessage.deserialize(menu.title))
            .rows(menu.rows)
            .pageSize(menu.itemsPerPage)
            .disableAllInteractions()
            .create();

        GuiItem filler = new GuiItem(menu.filler.toItemStack());

        for (int slot : menu.fillerSlots) {
            gui.setItem(slot, filler);
        }

        menu.decorations.forEach((slot, item) -> gui.setItem(slot, new GuiItem(item.toItemStack())));

        gui.setItem(menu.expiredItems.slot(), new GuiItem(menu.expiredItems.toItemStack(), event -> this.openExpired(player)));

        gui.setItem(menu.previousPage.slot(), new GuiItem(menu.previousPage.toItemStack(), event -> {
            gui.previous();

            this.updateTitle(gui, menu.title);
        }));

        Formatter sorterFormatter = new Formatter()
            .register("{SORT-TYPE}", this.config.menu.sortTypes.get(this.sortByUuids.getOrDefault(player.getUniqueId(), MarketSortType.NEWEST)));

        gui.setItem(menu.sorter.slot(), new GuiItem(menu.sorter.toItemStack(sorterFormatter), event -> {
            MarketSortType sort = this.changeSortType(player);

            this.open(player, sort);
        }));

        gui.setItem(menu.nextPage.slot(), new GuiItem(menu.nextPage.toItemStack(), event -> {
            gui.next();

            this.updateTitle(gui, menu.title);
        }));

        gui.setItem(menu.info.slot(), new GuiItem(menu.info.toItemStack()));

        this.loadItems(gui, player, sortType);

        this.updateTitle(gui, menu.title);

        gui.open(player);
    }

    private void openExpired(Player player) {
        List<MarketItem> items = this.expiredService.findExpiredItems(player.getUniqueId());

        if (items.isEmpty()) {
            this.broadcaster.sendAnnounce(player, this.config.notExpiredItems);

            return;
        }

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(this.config.expiredMenu.title))
            .rows(this.config.expiredMenu.rows)
            .disableAllInteractions()
            .create();

        for (MarketItem item : items) {
            ItemStack itemStack = item.itemStack().clone();

            GuiItem guiItem = new GuiItem(itemStack);
            guiItem.setAction(event -> {
                ItemUtil.giveItem(player, itemStack);
                this.expiredService.removeItem(item);

                gui.removeItem(guiItem);
                gui.updateItem(event.getSlot(), new GuiItem(Material.AIR));
            });

            gui.addItem(guiItem);
        }

        gui.setItem(this.config.expiredMenu.back.slot(), new GuiItem(this.config.expiredMenu.back.toItemStack(), event -> this.open(player)));

        gui.open(player);
    }

    private void updateTitle(PaginatedGui gui, String title) {
        int pages = gui.getPagesNum();

        if (pages == 0) {
            pages = 1;
        }

        Formatter formatter = new Formatter()
            .register("{PAGE}", gui.getCurrentPageNum())
            .register("{PAGES}", pages);

        gui.updateTitle(ChatColor.translateAlternateColorCodes('&', formatter.format(title)));
    }

    private void loadItems(PaginatedGui gui, Player player, MarketSortType sortType) {
        gui.clearPageItems();

        for (MarketItem marketItem : this.sortItems(sortType)) {
            List<String> lore = new ArrayList<>();
            ItemStack itemStack = marketItem.itemStack().clone();

            if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
                lore.addAll(itemStack.getItemMeta().getLore());
            }

            lore.addAll(this.config.menu.lore);

            ItemBuilder item = ItemBuilder.from(marketItem.itemStack().clone())
                .setLore(lore.stream()
                    .map(line -> new Formatter()
                        .register("{PRICE}", String.valueOf(marketItem.price()))
                        .register("{SELLER}", marketItem.seller())
                        .register("{EXPIRE}", DurationUtil.format(Duration.between(Instant.now(), marketItem.issuedAt().plus(this.config.itemExpire))))
                        .register("{FOOTER}", () -> {
                            if (marketItem.uniqueId().equals(player.getUniqueId())) {
                                return this.config.menu.placeholderTake;
                            }

                            return this.config.menu.placeholderBuy;
                        })
                        .format(line))
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .toList());

            GuiItem guiItem = new GuiItem(item.build());
            guiItem.setAction(event -> {
                if (player.getUniqueId().equals(marketItem.uniqueId())) {
                    this.service.removeItem(marketItem);

                    ItemUtil.giveItem(player, marketItem.itemStack());

                    gui.removePageItem(guiItem);
                    gui.update();

                    this.broadcaster.sendAnnounce(player, this.config.takenDownItem);

                    return;
                }
                if (!this.service.containsItem(marketItem)) {
                    this.broadcaster.sendAnnounce(player, this.config.alreadySold);

                    gui.removePageItem(marketItem.itemStack());
                    gui.update();

                    return;
                }

                double balance = this.moneyService.getBalance(player);

                if (marketItem.price() > balance) {
                    Formatter formatter = new Formatter()
                        .register("{PRICE}", String.valueOf(marketItem.price()))
                        .register("{BALANCE}", String.valueOf(balance))
                        .register("{MISSING}", String.valueOf(marketItem.price() - balance));

                    this.broadcaster.sendAnnounce(player, this.config.notEnoughtMoney, formatter);

                    return;
                }

                this.openConfirm(player, gui, guiItem, marketItem);
            });

            gui.addItem(guiItem);
        }
    }

    void openConfirm(Player player, PaginatedGui marketGui, GuiItem guiItem, MarketItem marketItem) {
        Gui gui = Gui.gui()
            .rows(3)
            .title(this.miniMessage.deserialize("&6Potwierdz zakup"))
            .disableAllInteractions()
            .create();

        GuiItem yes = ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE)
            .name(this.miniMessage.deserialize("&aKupuje!"))
            .asGuiItem(event -> {
                marketGui.removePageItem(guiItem);
                marketGui.update();

                this.service.removeItem(marketItem);

                this.moneyService.withdraw(player, marketItem.price());

                ItemUtil.giveItem(player, marketItem.itemStack());

                OfflinePlayer offlineSeller = this.server.getOfflinePlayer(marketItem.uniqueId());
                this.moneyService.deposit(offlineSeller, marketItem.price());

                Formatter formatter = new Formatter()
                    .register("{PRICE}", String.valueOf(marketItem.price()))
                    .register("{AMOUNT}", marketItem.itemStack().getAmount())
                    .register("{SELLER}", marketItem.seller())
                    .register("{ITEM}", marketItem.itemStack().getType().name());

                this.broadcaster.sendAnnounce(player, this.config.itemBought, formatter);
                this.broadcaster.sendAnnounce(offlineSeller, this.config.yourItemBought, formatter);

                this.open(player);
            });

        int[] yesSlots = {
            0, 1, 2,
            9, 10, 11,
            18, 19, 20
        };

        GuiItem no = ItemBuilder.from(Material.RED_STAINED_GLASS_PANE)
            .name(this.miniMessage.deserialize("&cNie kupuje."))
            .asGuiItem(event -> this.open(player));

        int[] noSlots = {
            6, 7, 8,
            15, 16, 17,
            24, 25, 26
        };

        List<String> lore = new ArrayList<>();
        ItemStack itemStack = marketItem.itemStack().clone();

        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
            lore.addAll(itemStack.getItemMeta().getLore());
        }

        lore.addAll(this.config.menu.confirmLore);

        ItemBuilder item = ItemBuilder.from(marketItem.itemStack().clone())
            .setLore(lore.stream()
                .map(line -> new Formatter()
                    .register("{PRICE}", String.valueOf(marketItem.price()))
                    .register("{SELLER}", marketItem.seller())
                    .register("{EXPIRE}", DurationUtil.format(Duration.between(Instant.now(), marketItem.issuedAt().plus(this.config.itemExpire))))
                    .format(line))
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList()));

        gui.setItem(13, item.asGuiItem());

        for (int slot : yesSlots) {
            gui.setItem(slot, yes);
        }

        for (int slot : noSlots) {
            gui.setItem(slot, no);
        }

        gui.open(player);
    }

    private List<MarketItem> sortItems(MarketSortType type) {
        List<MarketItem> items = new ArrayList<>(this.service.items());

        switch (type) {
            case OLDEST -> items.sort(Comparator.comparing(MarketItem::issuedAt));
            case NEWEST -> items.sort(Comparator.comparing(MarketItem::issuedAt).reversed());
            case MOST_EXPENSIVE -> items.sort(Comparator.comparingDouble(MarketItem::price).reversed());
            case CHEAPEST -> items.sort(Comparator.comparingDouble(MarketItem::price));
        }

        return items;
    }

    private MarketSortType changeSortType(Player player) {
        UUID uniqueId = player.getUniqueId();
        MarketSortType type = this.sortByUuids.getOrDefault(uniqueId, MarketSortType.NEWEST);

        if (type == MarketSortType.NEWEST) {
            type = MarketSortType.OLDEST;
        } else if (type == MarketSortType.OLDEST) {
            type = MarketSortType.MOST_EXPENSIVE;
        } else if (type == MarketSortType.MOST_EXPENSIVE) {
            type = MarketSortType.CHEAPEST;
        } else if (type == MarketSortType.CHEAPEST) {
            type = MarketSortType.NEWEST;
        }

        this.sortByUuids.put(uniqueId, type);

        Formatter formatter = new Formatter()
            .register("{SORT-TYPE}", this.config.menu.sortTypes.get(type));

        this.broadcaster.sendAnnounce(player, this.config.changedSort, formatter);

        return type;
    }
}
