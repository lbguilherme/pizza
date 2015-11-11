/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzasystem.transfer;

import java.sql.ResultSet;
import java.sql.SQLException;
import pizzasystem.data.Pizza;
import pizzasystem.data.PizzaTaste;

/**
 *
 * @author guilherme
 */
public class PizzaDAO {
    
    /**
     *
     * @param result
     * @throws SQLException
     */
    protected static void setFromResultSet(ResultSet result, Pizza object) throws SQLException {
        object.setTaste1(result.getString("taste1"));
        object.setTaste2(result.getString("taste2"));
        object.setTaste3(result.getString("taste3"));
        object.setSize(PizzaTaste.Size.valueOf(result.getString("size")));
    }
}
