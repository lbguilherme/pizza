package pizzasystem.transfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements IDatabase {
    private Connection db;

    private final String host;
    private final String port;
    private final String name;
    private final String user;
    private final String pass;

    public Database(String host, String port, String name, String user, String pass) {
        this.host = host;
        this.port = port;
        this.name = name;
        this.user = user;
        this.pass = pass;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (db == null) {
            String url = "jdbc:mysql://"+host+":"+port+"/"+name;
            db = DriverManager.getConnection(url, user, pass);
        }

        return db;
    }
}
