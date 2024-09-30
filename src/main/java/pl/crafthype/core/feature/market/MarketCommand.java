package pl.crafthype.core.feature.market;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.utilities.text.Formatter;
import pl.crafthype.core.notification.NotificationBroadcaster;

import java.time.Instant;
import java.util.UUID;

@Route(name = "market", aliases = { "rynek", "ah", "bazar" })
public class MarketCommand {

    private final NotificationBroadcaster broadcaster;
    private final MarketService service;
    private final MarketConfig config;
    private final MarketMenu menu;

    public MarketCommand(NotificationBroadcaster broadcaster, MarketService service, MarketConfig config, MarketMenu menu) {
        this.broadcaster = broadcaster;
        this.service = service;
        this.config = config;
        this.menu = menu;
    }

    @Execute(required = 0)
    void execute(Player player) {
        this.menu.open(player);
    }

    @Execute(route = "wystaw", required = 2)
    void sell(Player player, @Arg @Name("ilość") int amount, @Arg @Name("cena") double price) {
        if (amount < 1) {
            this.broadcaster.sendAnnounce(player, this.config.negativeAmount);

            return;
        }

        if (amount > 64) {
            this.broadcaster.sendAnnounce(player, this.config.maxAmount);

            return;
        }

        if (price < 1) {
            this.broadcaster.sendAnnounce(player, this.config.negativeBalance);

            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack == null || itemStack.getType().isAir()) {
            this.broadcaster.sendAnnounce(player, this.config.notItemInHand);

            return;
        }

        if (itemStack.getAmount() < amount) {
            this.broadcaster.sendAnnounce(player, this.config.notEnoughtAmount);

            return;
        }

        if (itemStack.getAmount() != amount) {
            itemStack = player.getInventory().getItemInMainHand().clone();
            itemStack.setAmount(amount);
        }

        player.getInventory().removeItem(itemStack);

        MarketItem marketItem = new MarketItem(UUID.randomUUID(), itemStack, price, player.getUniqueId(), player.getName(), Instant.now());

        this.service.addItem(marketItem);

        Formatter formatter = new Formatter()
            .register("{AMOUNT}", amount)
            .register("{PRICE}", price)
            .register("{ITEM}", itemStack.getType().name());

        this.broadcaster.sendAnnounce(player, this.config.itemPlaced, formatter);
    }
}
