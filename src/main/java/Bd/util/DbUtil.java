package Bd.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
    private DbUtil() {
    }

    /**
     * Opens a new JDBC connection using the parameters from {@code db.properties}.
     * <p>
     * Each call creates a fresh {@link Connection} to avoid reusing a potentially
     * closed one. Callers are responsible for closing the returned connection.
     */
    public static Connection getConnection() {
        Properties props = new Properties();
        try (InputStream is = DbUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is != null) {
                props.load(is);
            }
            String driver = props.getProperty("driverClassName");
            String url = props.getProperty("url");
            String user = props.getProperty("username");
            String pass = props.getProperty("password");
            Class.forName(driver);
            return DriverManager.getConnection(url, user, pass);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
