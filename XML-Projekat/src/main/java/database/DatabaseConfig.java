package database;

import org.apache.tomcat.util.net.jsse.openssl.Authentication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Dorian on 31.5.2016.
 */
public class DatabaseConfig {

    static public class ConnectionProperties {

        public String host;
        public int port = -1;
        public String user;
        public String password;
        public String database;
        public Authentication authType;

        public ConnectionProperties(Properties props) {
            host = props.getProperty("conn.host").trim();
            port = Integer.parseInt(props.getProperty("conn.port"));
            user = props.getProperty("conn.user").trim();
            password = props.getProperty("conn.pass").trim();
            database = props.getProperty("conn.database").trim();
            //authType = Authentication.valueOf(props.getProperty("conn.authentication_type").toUpperCase().trim());
            System.out.print(database);
        }

    }

    public static ConnectionProperties loadProperties() throws IOException {
        String propsName = "application.properties";

        InputStream propsStream = openStream(propsName);
        if (propsStream == null)
            throw new IOException("Could not read properties " + propsName);

        Properties props = new Properties();
        props.load(propsStream);

        System.out.print("Vratio conn properties");

        return new ConnectionProperties(props);
    }

    public static InputStream openStream(String fileName) throws IOException {
        return DatabaseConfig.class.getClassLoader().getResourceAsStream(fileName);
    }

}
