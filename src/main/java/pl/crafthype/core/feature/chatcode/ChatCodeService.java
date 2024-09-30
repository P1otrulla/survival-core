package pl.crafthype.core.feature.chatcode;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;
import pl.crafthype.core.money.MoneyService;
import pl.crafthype.core.notification.NotificationBroadcaster;
import pl.crafthype.core.scheduler.Scheduler;
import pl.crafthype.core.shared.CodeUtil;
import pl.crafthype.core.shared.RandomUtil;

import java.time.Duration;

public class ChatCodeService {

    private final NotificationBroadcaster broadcaster;
    private final MoneyService moneyService;
    private final ChatCodeSettings settings;
    private final Scheduler scheduler;
    private final Server server;
    private String currentCode;

    public ChatCodeService(NotificationBroadcaster broadcaster, MoneyService moneyService, ChatCodeSettings settings, Scheduler scheduler, Server server) {
        this.broadcaster = broadcaster;
        this.moneyService = moneyService;
        this.settings = settings;
        this.scheduler = scheduler;
        this.server = server;

        this.tick();
    }

    void generateCode() {
        this.currentCode = CodeUtil.generateCode(this.settings.codeLength());

        this.broadcaster.sendAnnounceAll(this.settings.generatedCode(), new Formatter()
            .register("{CODE}", this.currentCode));
    }

    void tick() {
        this.scheduler.laterSync(this::tick, this.settings.interval());

        this.generateCode();
    }

    boolean isCode(String code) {
        return this.currentCode.equals(code);
    }

    boolean isCodeAvailable() {
        return this.currentCode != null;
    }

    void giveReward(Player player) {
        Formatter formatter = new Formatter()
            .register("{PLAYER}", player.getName())
            .register("{CODE}", this.currentCode);

        this.broadcaster.sendAnnounceAll(this.settings.codePrescribed(), formatter);

        this.currentCode = null;

        this.scheduler.laterSync(() -> this.settings.rewards().forEach(rewardType -> {

            if (rewardType == ChatCodeSettings.RewardType.ALL_MONEY) {
                this.settings.money().forEach(money -> this.moneyService.deposit(player, money));
            }

            if (rewardType == ChatCodeSettings.RewardType.ALL_ITEMS) {
                this.settings.items().forEach(item -> player.getInventory().addItem(item));
            }

            if (rewardType == ChatCodeSettings.RewardType.ALL_COMMANDS) {
                this.settings.commands().forEach(command -> this.server.dispatchCommand(this.server.getConsoleSender(), formatter.format(command)));
            }

            if (rewardType == ChatCodeSettings.RewardType.ONE_MONEY) {
                RandomUtil.randomElement(this.settings.money()).peek(money -> this.moneyService.deposit(player, money));
            }

            if (rewardType == ChatCodeSettings.RewardType.ONE_ITEM) {
                RandomUtil.randomElement(this.settings.items()).peek(item -> player.getInventory().addItem(item));
            }

            if (rewardType == ChatCodeSettings.RewardType.ONE_COMMAND) {
                RandomUtil.randomElement(this.settings.commands()).peek(command -> this.server.dispatchCommand(this.server.getConsoleSender(), formatter.format(command)));
            }
        }), Duration.ofMillis(10));
    }
}