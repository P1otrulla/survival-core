package pl.crafthype.core.command.implementation;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.upperlevel.spigot.book.BookUtil;

@Route(name = "zasil", aliases = { "doladuj", "doladowanie" })
public class TopUpCommand {

    @Execute
    void execute(Player player) {
        ItemStack book = BookUtil.writtenBook()
            .author(player.getName())
            .title("Doladowanie")
            .pages(new BookUtil.PageBuilder()
                .add(ChatColor.translateAlternateColorCodes('&', "    &6&lDOLADOWANIE"))
                .newLine()
                .newLine()
                .add(ChatColor.translateAlternateColorCodes('&', "&8Kliknij, w przycisk na dole"))
                .add(ChatColor.translateAlternateColorCodes('&', " &8aby przejść do sklepu."))
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .add(BookUtil.TextBuilder.of("     [Akceptuj]")
                    .color(ChatColor.GREEN)
                    .style(ChatColor.BOLD)
                    .onClick(BookUtil.ClickAction.openUrl("https://crafthype.pl/"))
                    .onHover(BookUtil.HoverAction.showText("Kliknij by przejść!"))
                    .build())

                .build())
            .build();
        BookUtil.openPlayer(player, book);
    }
}
