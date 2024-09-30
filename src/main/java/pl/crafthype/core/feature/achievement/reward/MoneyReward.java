package pl.crafthype.core.feature.achievement.reward;

import org.bukkit.entity.Player;
import pl.crafthype.core.money.MoneyService;

public class MoneyReward implements Reward {

    private final MoneyService moneyService;
    private final double money;

    public MoneyReward(MoneyService moneyService, double money) {
        this.moneyService = moneyService;
        this.money = money;
    }

    @Override
    public void giveReward(Player player) {
        this.moneyService.deposit(player, this.money);
    }
}
