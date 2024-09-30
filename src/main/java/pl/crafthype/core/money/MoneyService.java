package pl.crafthype.core.money;

import org.bukkit.OfflinePlayer;

public interface MoneyService {

    void deposit(OfflinePlayer player, double money);

    void withdraw(OfflinePlayer player, double money);

    double getBalance(OfflinePlayer player);

}
