package pl.crafthype.core.feature.chatcode;

import org.bukkit.inventory.ItemStack;
import pl.crafthype.core.notification.Notification;

import java.time.Duration;
import java.util.List;

public interface ChatCodeSettings {

    int codeLength();

    Duration interval();

    Notification generatedCode();

    Notification codePrescribed();

    List<String> commands();

    List<ItemStack> items();

    List<Double> money();

    List<RewardType> rewards();

    enum RewardType {
        ONE_COMMAND,
        ALL_COMMANDS,
        ONE_ITEM,
        ALL_ITEMS,
        ONE_MONEY,
        ALL_MONEY,
    }
}
