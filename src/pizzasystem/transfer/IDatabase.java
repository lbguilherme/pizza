package pizzasystem.transfer;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabase {
    public Connection getConnection() throws SQLException;
}
