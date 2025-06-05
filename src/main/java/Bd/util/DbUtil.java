package Bd.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
    private static Connection connection;

    private DbUtil() {
    }

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
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
            connection = DriverManager.getConnection(url, user, pass);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
