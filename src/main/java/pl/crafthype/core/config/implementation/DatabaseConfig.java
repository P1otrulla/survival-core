package pl.crafthype.core.config.implementation;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import pl.crafthype.core.config.ReloadableConfig;

import java.io.File;

public class DatabaseConfig implements ReloadableConfig {

    private String host = "localhost";
    private int port = 3306;
    private String base = "test";
    private String user = "root";
    private String pass = "";
    private boolean useSSL = false;
    private int timeOut = 30000;
    private int poolSize = 16;

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getBase() {
        return this.base;
    }

    public String getUser() {
        return this.user;
    }

    public String getPass() {
        return this.pass;
    }

    public boolean isSSL() {
        return this.useSSL;
    }

    public int getTimeOut() {
        return this.timeOut;
    }

    public int getPoolSize() {
        return this.poolSize;
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "database.yml");
    }
}
