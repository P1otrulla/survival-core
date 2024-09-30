package pl.crafthype.core.feature.rankjoinquit;

import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.notification.Notification;

import java.io.File;
import java.util.Map;

public class RankJoinQuitConfig implements ReloadableConfig {

    public Map<String, Notification> ranksJoin = new ImmutableMap.Builder<String, Notification>()
        .put("yt", Notification.chat("&8[&6YT&8]&6 &6{PLAYER} dołączył na serwer!"))
        .put("hype", Notification.chat("&8[&6HYPE&8] &a{PLAYER} dołączył na serwer!"))
        .put("legenda", Notification.chat("&8[&aLEGENDA&8] &a{PLAYER} dołączył na serwer!"))
        .put("elita", Notification.chat("&8[&dELITA&8] &d{PLAYER} dołączył na serwer!"))
        .build();
    public Map<String, Notification> ranksQuit = new ImmutableMap.Builder<String, Notification>()
        .put("yt", Notification.chat("&8[&6YT&8]&6 &6{PLAYER} wyszedł z serwera!"))
        .put("hype", Notification.chat("&8[&6HYPE&8] &a{PLAYER} wyszedł z serwera!"))
        .put("legenda", Notification.chat("&8[&aLEGENDA&8] &a{PLAYER} wyszedł z serwera!"))
        .put("elita", Notification.chat("&8[&dELITA&8] &d{PLAYER} wyszedł z serwera!"))
        .build();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "ranks-join-quit.yml");
    }
}
