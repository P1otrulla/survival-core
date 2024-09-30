package pl.crafthype.core;

import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.schematic.Schematic;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.crafthype.core.bridge.BridgeService;
import pl.crafthype.core.command.InvalidUsageHandler;
import pl.crafthype.core.command.NotificationHandler;
import pl.crafthype.core.command.PermissionHandler;
import pl.crafthype.core.command.argument.UserArgument;
import pl.crafthype.core.command.contextual.UserContextual;
import pl.crafthype.core.command.implementation.*;
import pl.crafthype.core.config.ConfigService;
import pl.crafthype.core.config.implementation.DatabaseConfig;
import pl.crafthype.core.config.implementation.MessagesConfig;
import pl.crafthype.core.config.implementation.PluginConfig;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.controller.*;
import pl.crafthype.core.database.DatabaseService;
import pl.crafthype.core.database.Refreshable;
import pl.crafthype.core.feature.achievement.AchievementCommand;
import pl.crafthype.core.feature.achievement.AchievementConfig;
import pl.crafthype.core.feature.achievement.AchievementRepository;
import pl.crafthype.core.feature.achievement.database.DatabaseAchievementRepository;
import pl.crafthype.core.feature.achievement.menu.AchievementCategoryMenu;
import pl.crafthype.core.feature.achievement.menu.AchievementMenu;
import pl.crafthype.core.feature.achievement.reward.CommandReward;
import pl.crafthype.core.feature.achievement.reward.ItemReward;
import pl.crafthype.core.feature.achievement.reward.MoneyReward;
import pl.crafthype.core.feature.border.BorderCommand;
import pl.crafthype.core.feature.border.BorderConfig;
import pl.crafthype.core.feature.border.BorderTask;
import pl.crafthype.core.feature.border.BorderUtils;
import pl.crafthype.core.feature.border.BukkitBorder;
import pl.crafthype.core.feature.chatcode.ChatCodeConfig;
import pl.crafthype.core.feature.chatcode.ChatCodeController;
import pl.crafthype.core.feature.chatcode.ChatCodeService;
import pl.crafthype.core.feature.customcommand.CustomCommandConfig;
import pl.crafthype.core.feature.customcommand.CustomCommandRegistry;
import pl.crafthype.core.feature.market.MarketCommand;
import pl.crafthype.core.feature.market.MarketConfig;
import pl.crafthype.core.feature.market.MarketExpireItemTask;
import pl.crafthype.core.feature.market.MarketExpiredRepository;
import pl.crafthype.core.feature.market.MarketExpiredService;
import pl.crafthype.core.feature.market.MarketMenu;
import pl.crafthype.core.feature.market.MarketRepository;
import pl.crafthype.core.feature.market.MarketService;
import pl.crafthype.core.feature.market.database.DatabaseMarketRepository;
import pl.crafthype.core.feature.market.database.expired.DatabaseMarketExpiredRepository;
import pl.crafthype.core.feature.pass.PassCommand;
import pl.crafthype.core.feature.pass.PassConfig;
import pl.crafthype.core.feature.pass.PassConfigRepository;
import pl.crafthype.core.feature.pass.PassController;
import pl.crafthype.core.feature.pass.PassMenu;
import pl.crafthype.core.feature.rankjoinquit.RankJoinQuitConfig;
import pl.crafthype.core.feature.rankjoinquit.RankJoinQuitController;
import pl.crafthype.core.feature.sex.DatabaseSexRepository;
import pl.crafthype.core.feature.sex.SexCommand;
import pl.crafthype.core.feature.sex.SexConfig;
import pl.crafthype.core.feature.sex.SexMenu;
import pl.crafthype.core.feature.sex.SexRepository;
import pl.crafthype.core.feature.statistic.DatabaseStatisticRepository;
import pl.crafthype.core.feature.statistic.StatisticController;
import pl.crafthype.core.feature.statistic.StatisticRegistry;
import pl.crafthype.core.feature.statistic.StatisticRepository;
import pl.crafthype.core.feature.statistic.StatisticResetCommand;
import pl.crafthype.core.feature.statistic.StatisticSpentTimeTask;
import pl.crafthype.core.feature.statistic.StatisticUpdateTask;
import pl.crafthype.core.feature.steward.StewardCommand;
import pl.crafthype.core.feature.steward.StewardConfig;
import pl.crafthype.core.feature.steward.StewardDataConfig;
import pl.crafthype.core.feature.steward.StewardMenu;
import pl.crafthype.core.feature.steward.StewardRepository;
import pl.crafthype.core.feature.steward.StewardService;
import pl.crafthype.core.feature.top.TopCommand;
import pl.crafthype.core.feature.top.TopConfig;
import pl.crafthype.core.feature.top.TopMenu;
import pl.crafthype.core.feature.top.TopRegistry;
import pl.crafthype.core.feature.top.TopTask;
import pl.crafthype.core.feature.weding.DatabaseWeddingRepository;
import pl.crafthype.core.feature.weding.DivorceCommand;
import pl.crafthype.core.feature.weding.WeddingCommand;
import pl.crafthype.core.feature.weding.WeddingConfig;
import pl.crafthype.core.feature.weding.WeddingRepository;
import pl.crafthype.core.feature.weding.request.WeddingRequestService;
import pl.crafthype.core.feature.weding.request.WeddingRequestTask;
import pl.crafthype.core.money.MoneyService;
import pl.crafthype.core.notification.Notification;
import pl.crafthype.core.notification.NotificationBroadcaster;
import pl.crafthype.core.scheduler.BukkitSchedulerImpl;
import pl.crafthype.core.scheduler.Scheduler;
import pl.crafthype.core.scheduler.Task;
import pl.crafthype.core.shared.DurationUtil;
import pl.crafthype.core.shared.InformationBookService;
import pl.crafthype.core.shared.legacy.LegacyColorProcessor;
import pl.crafthype.core.user.DatabaseUserRepository;
import pl.crafthype.core.user.User;
import pl.crafthype.core.user.UserController;
import pl.crafthype.core.user.UserRepository;
import pl.crafthype.core.user.UserSaveTask;
import pl.crafthype.core.user.UserService;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Stream;

public final class CorePlugin extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger(CorePlugin.class.getName());

    private BorderConfig borderConfig;
    private TopConfig topConfig;

    private DatabaseService databaseService;

    private Scheduler scheduler;
    private AudienceProvider audienceProvider;

    private UserService userService;
    private Task userSaveTask;

    private StatisticRegistry statisticRegistry;
    private Task statisticSpentTimeTask;

    private TopRegistry topRegistry;
    private TopMenu topMenu;
    private Task topTask;

    private MarketExpiredService marketExpiredService;
    private MarketService marketService;

    private MoneyService moneyService;

    private NotificationBroadcaster broadcaster;

    private LiteCommands<CommandSender> liteCommands;

    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        LOGGER.info("Trying to load Plugin...");

        Server server = this.getServer();

        ConfigService configService = new ConfigService(this.getDataFolder());

        this.luckPerms = this.getServer().getServicesManager().load(LuckPerms.class);


        PassConfigRepository passConfigRepository = configService.load(new PassConfigRepository(configService));
        this.borderConfig = configService.load(new BorderConfig(configService));
        CustomCommandConfig customCommandConfig = configService.load(new CustomCommandConfig());
        AchievementConfig achievementConfig = configService.load(new AchievementConfig());
        StewardDataConfig stewardDataConfig = configService.load(new StewardDataConfig());

        ChatCodeConfig chatCodeConfig = configService.load(new ChatCodeConfig());
        MessagesConfig messagesConfig = configService.load(new MessagesConfig());
        DatabaseConfig databaseConfig = configService.load(new DatabaseConfig());
        StewardConfig stewardConfig = configService.load(new StewardConfig());
        WeddingConfig weddingConfig = configService.load(new WeddingConfig());
        PluginConfig pluginConfig = configService.load(new PluginConfig());
        MarketConfig marketConfig = configService.load(new MarketConfig());
        PassConfig passConfig = configService.load(new PassConfig());

        this.topConfig = configService.load(new TopConfig());

        SexConfig sexConfig = configService.load(new SexConfig());
        RankJoinQuitConfig rankJoinQuitConfig = configService.load(new RankJoinQuitConfig());

        this.databaseService = new DatabaseService(databaseConfig);
        this.databaseService.connect();

        this.scheduler = new BukkitSchedulerImpl(this);
        MiniMessage miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();
        this.audienceProvider = BukkitAudiences.create(this);

        this.broadcaster = new NotificationBroadcaster(this.audienceProvider, miniMessage, server);

        UserRepository userRepository = DatabaseUserRepository.create(this.databaseService);
        this.userService = new UserService();

        userRepository.loadUsers().whenComplete((users, throwable) -> {
            if (throwable != null) {
                this.getLogger().severe("Cannot load users from database");

                throwable.printStackTrace();

                return;
            }

            this.userService.addAll(users);
        });

        this.userSaveTask = this.scheduler.timerSync(new UserSaveTask(userRepository, this.userService),
            Duration.ofMinutes(1), Duration.ofMinutes(1));

        StatisticRepository statisticRepository = DatabaseStatisticRepository.create(this.databaseService);
        this.statisticRegistry = new StatisticRegistry();

        this.loadStatisticFromRepositoryDatabase(statisticRepository);

        this.scheduler.timerSync(new StatisticUpdateTask(statisticRepository, this.statisticRegistry, server),
            Duration.ofMinutes(1), Duration.ofMinutes(1));

        this.statisticSpentTimeTask = this.scheduler.timerSync(new StatisticSpentTimeTask(this.statisticRegistry, server),
            Duration.ofSeconds(1), Duration.ofSeconds(1));

        AchievementRepository achievementRepository = DatabaseAchievementRepository.create(this.databaseService);
        AchievementMenu achievementMenu = new AchievementMenu(this.statisticRegistry, achievementRepository,
            this.broadcaster, achievementConfig, miniMessage, scheduler);

        AchievementCategoryMenu achievementCategoryMenu = new AchievementCategoryMenu(achievementMenu, achievementConfig,
            achievementConfig, miniMessage, scheduler);

        achievementMenu.setBackItem(achievementCategoryMenu);

        StewardRepository stewardRepository = new StewardService(configService, stewardDataConfig);
        StewardMenu stewardMenu = new StewardMenu(stewardRepository, miniMessage, stewardConfig, server);

        this.topRegistry = new TopRegistry(this.statisticRegistry, this.topConfig, this.topConfig);
        this.topMenu = new TopMenu(statisticRegistry, this.userService, miniMessage, this.topRegistry, this.topConfig,
            this.scheduler, this.topConfig);

        this.topMenu.startUpdateTask();

        this.scheduler.laterSync(() -> this.topTask = this.scheduler.timerSync(new TopTask(this.topRegistry, this.topMenu),
            Duration.ofSeconds(10), this.topConfig.topUpdateInterval()), Duration.ofSeconds(20));

        this.borderConfig.borders().forEach(border -> {
            Instant lastExpand = border.lastExpand();
            Instant now = Instant.now();
            Instant nextExpand = lastExpand.plus(border.duration());
            Duration taskDeley = now.isBefore(nextExpand) ? Duration.between(now, nextExpand) : Duration.ofMillis(10);

            LOGGER.info("Border " + border.world() + " will expand in " + DurationUtil.format(taskDeley));

            BorderUtils.createWorldBorder(server.getWorld(border.world()), border.currentSize());

            BorderTask borderTask = new BorderTask(this.broadcaster, this.borderConfig, border);
            Task task = this.scheduler.timerSync(borderTask, taskDeley, border.duration());

            border.updateBorderTask(task);
        });

        SexRepository sexRepository = DatabaseSexRepository.create(this.databaseService);
        SexMenu sexMenu = new SexMenu(this.broadcaster, sexRepository, miniMessage, sexConfig);

        WeddingRepository weddingRepository = DatabaseWeddingRepository.create(this.databaseService);
        WeddingRequestService weddingRequestService = new WeddingRequestService();

        this.scheduler.timerSync(new WeddingRequestTask(weddingRequestService, this.broadcaster, weddingConfig, server),
            Duration.ofSeconds(5), Duration.ofSeconds(5));

        BridgeService bridgeService = new BridgeService(
            achievementRepository, achievementConfig, this.statisticRegistry, weddingRepository, server.getServicesManager(),
            server.getPluginManager(), this.borderConfig, sexRepository, weddingConfig, this.userService, sexConfig);
        bridgeService.init();
        this.moneyService = bridgeService.moneyService();

        ChatCodeService chatCodeService = new ChatCodeService(this.broadcaster, this.moneyService, chatCodeConfig, this.scheduler, server);

        achievementConfig.categories().forEach(achievementCategory -> achievementCategory.achievements().forEach(achievement -> {
            if (!achievement.rewardItems().isEmpty()) {
                achievement.addReward(
                    new ItemReward(achievement.rewardItems().stream()
                    .map(ConfigItem::toItemStack)
                    .toList()));
            }

            if (achievement.rewardBalance() > 0 && this.moneyService != null) {
                achievement.addReward(new MoneyReward(this.moneyService, achievement.rewardBalance()));
            }

            if (!achievement.rewardCommands().isEmpty()) {
                achievement.addReward(new CommandReward(server, achievement.rewardCommands()));
            }
        }));

        MarketExpiredRepository marketExpiredRepository = DatabaseMarketExpiredRepository.create(this.databaseService);
        this.marketExpiredService = new MarketExpiredService(marketExpiredRepository);
        marketExpiredRepository.findAll().whenComplete((expiredItems, throwable) -> {
            if (throwable != null) {
                this.getLogger().severe("Cannot load expired market items from database");

                throwable.printStackTrace();

                return;
            }

            expiredItems.forEach(expiredItem -> this.marketExpiredService.addExpired(expiredItem));
        });
        MarketRepository marketRepository = DatabaseMarketRepository.create(this.databaseService);
        this.marketService = new MarketService(marketRepository);
        marketRepository.findItems().whenComplete((items, throwable) -> {
            if (throwable != null) {
                this.getLogger().severe("Cannot load market items from database");

                throwable.printStackTrace();

                return;
            }

            this.marketService.addAllItems(items);
        });

        MarketMenu marketMenu = new MarketMenu(this.marketExpiredService, this.broadcaster, this.moneyService,
            miniMessage, this.marketService, marketConfig, server);

        this.scheduler.timerSync(new MarketExpireItemTask(this.broadcaster, this.marketExpiredService, this.marketService, marketConfig, server),
            Duration.ofSeconds(5), Duration.ofSeconds(5));

        CustomCommandRegistry customCommandRegistry = new CustomCommandRegistry(this.broadcaster, server);
        customCommandConfig.customCommands.values().forEach(customCommandRegistry::addCommand);

        PassMenu passMenu = new PassMenu(this.broadcaster, passConfigRepository, this.moneyService, miniMessage, passConfig);


        InformationBookService informationBookService = new InformationBookService(this.audienceProvider, miniMessage, this.scheduler, pluginConfig);

        this.liteCommands = LiteBukkitFactory.builder(server, "ch-core", true)
            .resultHandler(RequiredPermissions.class, new PermissionHandler(this.broadcaster, messagesConfig))
            .resultHandler(Schematic.class, new InvalidUsageHandler(this.broadcaster, messagesConfig))
            .resultHandler(Notification.class, new NotificationHandler(this.broadcaster))

            .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("Only player"))
            .contextualBind(User.class, new UserContextual(messagesConfig, this.userService))

            .argument(User.class, new UserArgument(this.userService, messagesConfig, server))
            .argument(Player.class, new BukkitPlayerArgument<>(server, "&cGracz jest offline!"))

            .commandInstance(
                new CoreCommand(informationBookService, this.broadcaster, configService),
                new WeddingCommand(this.broadcaster, weddingRequestService, weddingRepository, this.moneyService, weddingConfig),
                new DivorceCommand(this.broadcaster, weddingRepository, moneyService, weddingConfig, server),
                new MarketCommand(this.broadcaster, this.marketService, marketConfig, marketMenu),
                new BorderCommand(this.broadcaster, this.borderConfig, this.scheduler),
                new NickCommand(this.broadcaster, messagesConfig, miniMessage),
                new KeyGiveCommand(),
                new AchievementCommand(achievementCategoryMenu),
                new StatisticResetCommand(this.statisticRegistry),
                new StewardCommand(stewardMenu),
                new PassCommand(passConfigRepository, passMenu),
                new SexCommand(sexMenu),
                new TopCommand(this.topMenu),
                new AutographCommand(this.broadcaster, messagesConfig, miniMessage, this),
                new SocialMediaCommand(),
                new TopUpCommand(),
                new ShopCommand(),
                new JobCommand(),
                new DeathBountyCommand()
            )
            .register();

        List<Refreshable> refreshables = Arrays.asList(achievementRepository, sexRepository, weddingRepository);

        Stream.of(
            new UserController(userRepository, refreshables, this.userService),
            new StatisticController(statisticRepository, this.statisticRegistry),
            new AutoLapisController(pluginConfig.autoLapis.toItemStack()),
            new PlayerFirstTimeController(informationBookService),
            new PassController(this.broadcaster, passConfigRepository, passConfig, passConfig, this, server),
            new ChatCodeController(chatCodeService),
            new PlayerDeathController(this.scheduler),
            new VoidController(server),
            new RankJoinQuitController(rankJoinQuitConfig, this.broadcaster)
            //new AfkZoneController(this.afkZoneConfig, broadcaster, this.afkZoneService)
        ).forEach(controller -> server.getPluginManager().registerEvents(controller, this));

        LOGGER.info("Plugin enabled in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms -> "
            + stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000.0 + "s");
    }

    private void loadStatisticFromRepositoryDatabase(StatisticRepository statisticRepository) {
        statisticRepository.getAll().whenComplete((statistics, throwable) -> {
            if (throwable != null) {
                this.getLogger().severe("Cannot load statistics from database");

                throwable.printStackTrace();

                return;
            }

            statistics.forEach(statistic -> this.statisticRegistry.addCache(statistic.owner(), statistic.statistic(), statistic.value()));
        });
    }

    @Override
    public void onDisable() {
        this.borderConfig.borders()
            .stream()
            .map(BukkitBorder::borderTask)
            .filter(Objects::nonNull)
            .forEach(Task::cancel);

        if (this.userSaveTask != null) {
            this.userSaveTask.cancel();
        }

        if (this.statisticSpentTimeTask != null) {
            this.statisticSpentTimeTask.cancel();
        }

        if (this.audienceProvider != null) {
            this.audienceProvider.close();
        }

        if (this.topTask != null) {
            this.topTask.cancel();
        }

        if (this.topMenu != null) {
            this.topMenu.stopUpdateTask();
        }

        if (this.liteCommands != null) {
            this.liteCommands.getPlatform().unregisterAll();
        }

        if (this.databaseService != null) {
            this.databaseService.close();
        }
    }
}
