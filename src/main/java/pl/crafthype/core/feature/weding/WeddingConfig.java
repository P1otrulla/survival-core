package pl.crafthype.core.feature.weding;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.notification.Notification;

import java.io.File;

public class WeddingConfig implements ReloadableConfig {

    public Notification notWedding = Notification.chat("&cNie jesteś w związku małżeńskim!");
    public Notification alreadyWedding = Notification.chat("&cJesteś już w związku małżeńskim!");
    public Notification playerAlreadyWedding = Notification.chat("&cTen gracz jest już w związku małżeńskim!");
    public Notification weddingBetweenPlayers = Notification.chat("&cNie możesz wysłać prośby o ślub do gracza który wysłał tobie prośbe o ślub!");
    public Notification noSelf = Notification.chat("&cNie możesz się ożenić z samym sobą!");

    public Notification divorced = Notification.chat("&7Rozwiódłes się z &e{PLAYER_TWO}&a!");
    public Notification divorcedBroadcast = Notification.chat("&7{PLAYER_ONE} &7i &e{PLAYER_TWO} &7rozwiedli się!");

    public Notification requestExpiredSender = Notification.chat("&cTwoja prośba o małżeństwo wygasła!");
    public Notification requestExpiredReceiver = Notification.chat("&cProśba o małżeństwo od wygasła!");

    public Notification noRequest = Notification.chat("&cTen gracz nie wysłał ci propozycji małżeństwa!");
    public Notification sendWeding = Notification.chat("&7Wysłałeś propozycję małżeństwa do &e{PLAYER}&a!");
    public Notification receivedWeding = Notification.chat("&7Otrzymałeś propozycję małżeństwa od &e{PLAYER}&a!");
    public Notification youAcceptWeding = Notification.chat("&7Zaakceptowałeś propozycję małżeństwa od &e{PLAYER}&a!");
    public Notification acceptedWeding = Notification.chat("&7{PLAYER} &7zaakceptował twoją propozycję małżeństwa!");
    public Notification wedingBroadcast = Notification.chat("&7{PLAYER_ONE} &7i &e{PLAYER_TWO} &7wzięli ślub!");

    public Notification wedingNoCostMessage = Notification.chat("&cKsiądz musi dostać 3000$ za ślub!");
    public Notification divorceNoMoney = Notification.chat("&cPrawnik musi dostać 1500$ za rozwód!");

    public double wedingCost = 3000;
    public double divorceCost = 1500;

    public String placeholderWedingNo = "&cNie";
    public String placeholderWedingYes = "&aTak";
    public String placeholderHeart = "&c❤";
    public String placeholderWedingNon = "&cBrak";

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "weding.yml");
    }
}
