package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mengf on 2017/6/9 0009.
 */
public class JDBCUtil {

    private static String driverName;
    private static String url;
    private static String username;
    private static String password;
    private static Properties properties;


    static {
        try {
            InputStream inputStream = Thread.currentThread().getClass().getResourceAsStream("database.properties");
            properties = new Properties();
            properties.load(inputStream);

            driverName = properties.getProperty("driver");
            username = properties.getProperty("username");
            url = properties.getProperty("url");
            password = properties.getProperty("password");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
