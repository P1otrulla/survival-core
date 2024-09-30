package pl.crafthype.core.config.implementation;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.notification.Notification;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static pl.crafthype.core.notification.Notification.chat;

public class MessagesConfig implements ReloadableConfig {

    public Notification playerNotFound = chat("&4Blad:︎ &cNie znaleziono takiego gracza!");
    public Notification invalidAmount = chat("&4Blad:︎ &cPodano nieprawidlowa ilosc pieniedzy!");

    public Notification noPermission = Notification.chat("&cObawiam się kolego, że nie masz uprawnień! &f({PERMISSION})");
    public Notification correctUsage = Notification.chat("&ePoprawne użycie: &9{USAGE}");

    public Notification correctUsageHeader = Notification.chat("&ePoprawne użycie:");
    public Notification correctUsageEntry = Notification.chat("&9{USAGE}");

    public Notification autographAirMessage = Notification.chat("&cMusisz trzymać jakiś item!");
    public Notification autographAlreadySigned = Notification.chat("&cTen przedmiot jest już autografem!");
    public Notification autographSuccessMessage = Notification.chat("&aPomyślnie nadano autograf!");


    public List<Notification> nickNameInfo = Arrays.asList(
        Notification.chat("&7Aby zmienić swój nick użyj komendy:"),
        Notification.chat("&e/nick ustaw <nick>"),
        Notification.chat(""),
        Notification.chat("&7Aby usunąć swój nick użyj komendy:"),
        Notification.chat("&e/nick usun"),
        Notification.chat(""),
        Notification.chat("&cPamiętaj że nick musi być taki sam jak twoja nazwa użytkownika!"),
        Notification.chat("&eMożesz użyć kolorów z pomocą &c<click:open_url:'https://webui.advntr.dev/'><light_purple>[Kliknij]</light_purple></click>")
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "messages.yml");
    }
}
