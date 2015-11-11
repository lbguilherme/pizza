package pizzasystem.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabe
 */
public class Menu{
    
    private Connection db;
    
    /**
     *
     * @param db
     */
    public Menu(Connection db) {
        this.db = db;
    }
    
    /**
     *
     * @return retorna a lista de pizzas disponiveis que estão no database
     * @throws SQLException
     */
    public List<PizzaTaste> getPizzaTastes() throws SQLException {
        return PizzaTaste.fetchAll(db);
    }
    
    /**
     *
     * @return retorna a lista de outros produtos disponiveis que estão no database
     * @throws SQLException
     */
    public List<OtherProductType> getOtherProductTypes() throws SQLException {
        return OtherProductType.fetchAll(db);
    }

}
