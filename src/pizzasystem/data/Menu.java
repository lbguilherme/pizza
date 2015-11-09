package pizzasystem.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Menu{
    
    private Connection db;
    
    public Menu(Connection db) {
        this.db = db;
    }
    
    public List<PizzaTaste> getPizzaTastes() throws SQLException {
        return PizzaTaste.fetchAll(db);
    }
    
    public List<OtherProductType> getOtherProductTypes() throws SQLException {
        return OtherProductType.fetchAll(db);
    }

}
