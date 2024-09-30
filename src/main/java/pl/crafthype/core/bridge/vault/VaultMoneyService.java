package pl.crafthype.core.bridge.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import pl.crafthype.core.money.MoneyService;

public class VaultMoneyService implements MoneyService {

    private final Economy economy;

    public VaultMoneyService(Economy economy) {
        this.economy = economy;
    }

    @Override
    public void deposit(OfflinePlayer player, double money) {
        this.economy.depositPlayer(player, money);
    }

    @Override
    public void withdraw(OfflinePlayer player, double money) {
        this.economy.withdrawPlayer(player, money);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return this.economy.getBalance(player);
    }
}
