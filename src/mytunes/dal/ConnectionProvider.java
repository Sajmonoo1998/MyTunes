package mytunes.dal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class ConnectionProvider
{
    private final SQLServerDataSource ds;
    private static final String PROP_FILE = "src\\mytunes\\dal\\credentials.txt";

    public ConnectionProvider() throws FileNotFoundException, IOException
    {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(PROP_FILE));
        ds = new SQLServerDataSource();
        ds.setServerName(databaseProperties.getProperty("ServerName"));
        ds.setDatabaseName(databaseProperties.getProperty("DatabaseName"));
        ds.setUser(databaseProperties.getProperty("Login"));
        ds.setPassword(databaseProperties.getProperty("Password"));
    }

    public Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }
}
