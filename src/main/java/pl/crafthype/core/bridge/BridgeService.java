package pl.crafthype.core.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import pl.crafthype.core.bridge.vault.VaultMoneyService;
import pl.crafthype.core.feature.achievement.AchievementPlaceholder;
import pl.crafthype.core.feature.achievement.AchievementRepository;
import pl.crafthype.core.feature.achievement.AchievementSettings;
import pl.crafthype.core.feature.border.BorderPlaceholder;
import pl.crafthype.core.feature.border.BorderService;
import pl.crafthype.core.feature.sex.SexConfig;
import pl.crafthype.core.feature.sex.SexPlaceholder;
import pl.crafthype.core.feature.sex.SexRepository;
import pl.crafthype.core.feature.statistic.StatisticPlaceholder;
import pl.crafthype.core.feature.statistic.StatisticRegistry;
import pl.crafthype.core.feature.weding.WeddingConfig;
import pl.crafthype.core.feature.weding.WeddingPlaceholder;
import pl.crafthype.core.feature.weding.WeddingRepository;
import pl.crafthype.core.money.MoneyService;
import pl.crafthype.core.user.UserPlaceholder;
import pl.crafthype.core.user.UserService;

import java.util.logging.Logger;

public class BridgeService {

    private static final Logger LOGGER = Logger.getLogger(BridgeService.class.getName());

    private final AchievementRepository achievementRepository;
    private final AchievementSettings achievementSettings;
    private final StatisticRegistry statisticRegistry;
    private final WeddingRepository weddingRepository;
    private final ServicesManager servicesManager;
    private final BorderService borderService;
    private final PluginManager pluginManager;
    private final SexRepository sexRepository;
    private final WeddingConfig weddingConfig;
    private final UserService userService;
    private final SexConfig sexConfig;
    private MoneyService moneyService;


    public BridgeService(
        AchievementRepository achievementRepository,
        AchievementSettings achievementSettings,
        StatisticRegistry statisticRegistry,
        WeddingRepository weddingRepository,
        ServicesManager servicesManager,
        PluginManager pluginManager,
        BorderService borderService,
        SexRepository sexRepository,
        WeddingConfig weddingConfig,
        UserService userService,
        SexConfig sexConfig
    ) {

        this.achievementRepository = achievementRepository;
        this.achievementSettings = achievementSettings;
        this.statisticRegistry = statisticRegistry;
        this.weddingRepository = weddingRepository;
        this.servicesManager = servicesManager;
        this.pluginManager = pluginManager;
        this.borderService = borderService;
        this.sexRepository = sexRepository;
        this.weddingConfig = weddingConfig;
        this.userService = userService;
        this.sexConfig = sexConfig;
    }

    public void init() {
        this.init("PlaceholderAPI", () -> {
            new AchievementPlaceholder(this.achievementRepository, this.achievementSettings).register();
            new SexPlaceholder(this.sexRepository, this.sexConfig).register();
            new StatisticPlaceholder(this.statisticRegistry).register();
            new BorderPlaceholder(this.borderService).register();
            new WeddingPlaceholder(this.weddingRepository, this.weddingConfig).register();
            new UserPlaceholder(this.userService).register();
        });
        this.init("Vault", () -> {
            RegisteredServiceProvider<Economy> ecoProvider = this.servicesManager.getRegistration(Economy.class);

            if (ecoProvider == null) {
                LOGGER.warning("Found Vault plugin but can't handle any providers!");

                return;
            }

            this.moneyService = new VaultMoneyService(ecoProvider.getProvider());
        });
    }

    private void init(String pluginName, Bridge bridge) {
        if (this.pluginManager.isPluginEnabled(pluginName)) {
            bridge.initialize();

            LOGGER.info("Created bridge with " + pluginName + " plugin!");
        }
    }

    public MoneyService moneyService() {
        return this.moneyService;
    }
}
