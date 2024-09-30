package pl.crafthype.core.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.support.ConnectionSource;
import com.zaxxer.hikari.HikariDataSource;
import pl.crafthype.core.config.implementation.DatabaseConfig;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class DatabaseService {

    private static final Logger LOGGER = Logger.getLogger(DatabaseService.class.getName());

    private final Map<Class<?>, Dao<?, ?>> cachedDao = new ConcurrentHashMap<>();
    private final DatabaseConfig dbConfig;
    private ConnectionSource connectionSource;

    public DatabaseService(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public void connect() {
        LOGGER.info("Connecting to database...");

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://" + this.dbConfig.getHost() + ":" + this.dbConfig.getPort() + "/" + this.dbConfig.getBase() + "?useSSL=" + this.dbConfig.isSSL());

        dataSource.setUsername(this.dbConfig.getUser());
        dataSource.setPassword(this.dbConfig.getPass());
        dataSource.setConnectionTimeout(this.dbConfig.getTimeOut());
        dataSource.setMaximumPoolSize(this.dbConfig.getPoolSize());

        dataSource.addDataSourceProperty("cachePrepStmts", true);
        dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        dataSource.addDataSourceProperty("useServerPrepStmts", true);
        dataSource.addDataSourceProperty("serverTimezone", "GMT");

        com.j256.ormlite.logger.Logger.setGlobalLogLevel(Level.TRACE);

        try {
            this.connectionSource = new DataSourceConnectionSource(dataSource, dataSource.getJdbcUrl());
        }
        catch (Exception exception) {
            exception.printStackTrace();

            LOGGER.warning("Can't connect to database!");
        }
    }

    public void close() {
        try {
            this.connectionSource.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public <T, ID> Dao<T, ID> getDao(Class<T> type) {
        try {
            Dao<?, ?> dao = this.cachedDao.get(type);

            if (dao == null) {
                dao = DaoManager.createDao(this.connectionSource, type);
                this.cachedDao.put(type, dao);
            }

            return (Dao<T, ID>) dao;
        }
        catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ConnectionSource connection() {
        return this.connectionSource;
    }
}

