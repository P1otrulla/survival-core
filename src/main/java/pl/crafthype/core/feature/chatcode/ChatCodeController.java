package pl.crafthype.core.feature.chatcode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatCodeController implements Listener {

    private final ChatCodeService service;

    public ChatCodeController(ChatCodeService service) {
        this.service = service;
    }

    @EventHandler
    void onChat(AsyncPlayerChatEvent event) {
        if (!this.service.isCodeAvailable()) {
            return;
        }

        String message = event.getMessage();

        if (!this.service.isCode(message)) {
            return;
        }

        this.service.giveReward(event.getPlayer());
    }
}
