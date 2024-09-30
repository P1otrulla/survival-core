package pl.crafthype.core.command.implementation;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.upperlevel.spigot.book.BookUtil;

@Route(name = "social", aliases = { "media" })
public class SocialMediaCommand {

    @Execute(route = "discord")
    void execute(Player player) {
        ItemStack book = BookUtil.writtenBook()
            .author(player.getName())
            .title("Discord")
            .pages(new BookUtil.PageBuilder()
                .add(ChatColor.translateAlternateColorCodes('&', "        &9&lDISCORD"))
                .newLine()
                .newLine()
                .add(ChatColor.translateAlternateColorCodes('&', "&8Kliknij, w przycisk na dole"))
                .add(ChatColor.translateAlternateColorCodes('&', "&8aby dołączyć do naszego forum."))
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .add(BookUtil.TextBuilder.of("     [Akceptuj]")
                    .color(ChatColor.GREEN)
                    .style(ChatColor.BOLD)
                    .onClick(BookUtil.ClickAction.openUrl("https://discord.gg/hWvpw92NuU"))
                    .onHover(BookUtil.HoverAction.showText("Kliknij by zaakceptować."))
                    .build())

                .build())
            .build();
        BookUtil.openPlayer(player, book);
    }

    @Execute(route = "youtube", aliases = "yt")
    void youtube(Player player) {
        ItemStack book = BookUtil.writtenBook()
            .author(player.getName())
            .title("Youtube")
            .pages(new BookUtil.PageBuilder()
                .add(ChatColor.translateAlternateColorCodes('&', "        &c&lYOUTUBE"))
                .newLine()
                .newLine()
                .add(ChatColor.translateAlternateColorCodes('&', "&8Kliknij, w przycisk na dole"))
                .add(ChatColor.translateAlternateColorCodes('&', " &8aby dołączyć do naszego youtub'a."))
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .add(BookUtil.TextBuilder.of("     [Akceptuj]")
                    .color(ChatColor.GREEN)
                    .style(ChatColor.BOLD)
                    .onClick(BookUtil.ClickAction.openUrl("https://www.youtube.com/c/nerumii"))
                    .onHover(BookUtil.HoverAction.showText("Kliknij by zaakceptować."))
                    .build())

                .build())
            .build();
        BookUtil.openPlayer(player, book);

    }

    @Execute(route = "instagram", aliases = "ig")
    void instagram(Player player) {
        ItemStack book = BookUtil.writtenBook()
            .author(player.getName())
            .title("Instagram")
            .pages(new BookUtil.PageBuilder()
                .add(ChatColor.translateAlternateColorCodes('&', "      &6&lINSTAGRAM"))
                .newLine()
                .newLine()
                .add(ChatColor.translateAlternateColorCodes('&', "&8Kliknij, w przycisk na dole"))
                .add(ChatColor.translateAlternateColorCodes('&', "&8aby dołączyć do naszego instagrama."))
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .add(BookUtil.TextBuilder.of("     [Akceptuj]")
                    .color(ChatColor.GREEN)
                    .style(ChatColor.BOLD)
                    .onClick(BookUtil.ClickAction.openUrl("https://instagram.com/crafthypepl"))
                    .onHover(BookUtil.HoverAction.showText("Kliknij by zaakceptować."))
                    .build())

                .build())
            .build();
        BookUtil.openPlayer(player, book);
    }

    @Execute(route = "tiktok", aliases = "tt")
    void tiktok(Player player) {
        ItemStack book = BookUtil.writtenBook()
            .author(player.getName())
            .title("TitTok")
            .pages(new BookUtil.PageBuilder()
                .add(ChatColor.translateAlternateColorCodes('&', "        &c&lTIKTOK"))
                .newLine()
                .newLine()
                .add(ChatColor.translateAlternateColorCodes('&', "&8Kliknij, w przycisk na dole"))
                .add(ChatColor.translateAlternateColorCodes('&', "&8aby dołączyć do naszego tiktok'a."))
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .add(BookUtil.TextBuilder.of("     [Akceptuj]")
                    .color(ChatColor.GREEN)
                    .style(ChatColor.BOLD)
                    .onClick(BookUtil.ClickAction.openUrl("https://www.tiktok.com/@neruumii"))
                    .onHover(BookUtil.HoverAction.showText("Kliknij by zaakceptować."))
                    .build())

                .build())
            .build();
        BookUtil.openPlayer(player, book);
    }

    @Execute(route = "facebook", aliases = "fb")
    void facebook(Player player) {
        ItemStack book = BookUtil.writtenBook()
            .author(player.getName())
            .title("Facebook")
            .pages(new BookUtil.PageBuilder()
                .add(ChatColor.translateAlternateColorCodes('&', "        &9&lFACEBOOK"))
                .newLine()
                .newLine()
                .add(ChatColor.translateAlternateColorCodes('&', "&8Kliknij, w przycisk na dole"))
                .add(ChatColor.translateAlternateColorCodes('&', "&8aby dołączyć do naszego facebook'a."))
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .add(BookUtil.TextBuilder.of("     [Akceptuj]")
                    .color(ChatColor.GREEN)
                    .style(ChatColor.BOLD)
                    .onClick(BookUtil.ClickAction.openUrl("https://www.facebook.com/crafthypepl"))
                    .onHover(BookUtil.HoverAction.showText("Kliknij by zaakceptować."))
                    .build())

                .build())
            .build();
        BookUtil.openPlayer(player, book);
    }

    @Execute(route = "twitch", aliases = "tw")
    void twitter(Player player) {
        ItemStack book = BookUtil.writtenBook()
            .author(player.getName())
            .title("Twitch")
            .pages(new BookUtil.PageBuilder()
                .add(ChatColor.translateAlternateColorCodes('&', "        &9&lTWITCH"))
                .newLine()
                .newLine()
                .add(ChatColor.translateAlternateColorCodes('&', "&8Kliknij, w przycisk na dole"))
                .add(ChatColor.translateAlternateColorCodes('&', "&8aby dołączyć do naszego twitter'a."))
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .add(BookUtil.TextBuilder.of("     [Akceptuj]")
                    .color(ChatColor.GREEN)
                    .style(ChatColor.BOLD)
                    .onClick(BookUtil.ClickAction.openUrl("https://www.twitch.tv/crafthypepl"))
                    .onHover(BookUtil.HoverAction.showText("Kliknij by zaakceptować."))
                    .build())

                .build())
            .build();
        BookUtil.openPlayer(player, book);
    }

    @Execute(route = "itemshop", aliases = "it")
    void itemshop(Player player) {
        ItemStack book = BookUtil.writtenBook()
            .author(player.getName())
            .title("ItemShop")
            .pages(new BookUtil.PageBuilder()
                .add(ChatColor.translateAlternateColorCodes('&', "        &6&lITEMSHOP"))
                .newLine()
                .newLine()
                .add(ChatColor.translateAlternateColorCodes('&', "&8Kliknij, w przycisk na dole"))
                .add(ChatColor.translateAlternateColorCodes('&', "&8aby odwiedzić nasz itemshop."))
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .newLine()
                .add(BookUtil.TextBuilder.of("     [Akceptuj]")
                    .color(ChatColor.GREEN)
                    .style(ChatColor.BOLD)
                    .onClick(BookUtil.ClickAction.openUrl("https://crafthype.pl/#shop"))
                    .onHover(BookUtil.HoverAction.showText("Kliknij by zaakceptować."))
                    .build())

                .build())
            .build();
        BookUtil.openPlayer(player, book);
    }
}