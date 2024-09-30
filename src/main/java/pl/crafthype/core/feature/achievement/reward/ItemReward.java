package pl.crafthype.core.feature.achievement.reward;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.crafthype.core.shared.ItemUtil;

import java.util.List;

public class ItemReward implements Reward {

    private final List<ItemStack> itemStacks;

    public ItemReward(List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    @Override
    public void giveReward(Player player) {
        this.itemStacks.forEach(itemStack -> ItemUtil.giveItem(player, itemStack));
    }
}
