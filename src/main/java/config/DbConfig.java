package config;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

@Slf4j
public class DbConfig {

    public static Connection connection(String jdbcDriverPath, String driverClassName, String url, String username, String password) {
        try {
            var urlClassLoader = new URLClassLoader(new URL[] { new URL("jar:file:/" + jdbcDriverPath + "!/") });
            var driver = (Driver)Class.forName(driverClassName, true, urlClassLoader).getDeclaredConstructor().newInstance();
            DriverManager.registerDriver(new DriverShim(driver));
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    static class DriverShim implements Driver {
        private Driver driver;
        DriverShim(Driver d) {
            this.driver = d;
        }
        @Override
        public Connection connect(String url, Properties info) throws SQLException {
            return this.driver.connect(url, info);
        }

        @Override
        public boolean acceptsURL(String url) throws SQLException {
            return this.driver.acceptsURL(url);
        }

        @Override
        public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
            return this.driver.getPropertyInfo(url, info);
        }

        @Override
        public int getMajorVersion() {
            return this.driver.getMajorVersion();
        }

        @Override
        public int getMinorVersion() {
            return this.driver.getMinorVersion();
        }

        @Override
        public boolean jdbcCompliant() {
            return this.driver.jdbcCompliant();
        }

        @Override
        public Logger getParentLogger() {
            return null;
        }
    }
}

