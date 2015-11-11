/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzasystem.transfer;

import java.sql.ResultSet;
import java.sql.SQLException;
import pizzasystem.data.OtherProduct;

public class OtherProductDAO {
    
    /**
     *
     * @param result
     * @throws SQLException
     */
    protected static void setFromResultSet(ResultSet result, OtherProduct object) throws SQLException {
        object.setProduct(result.getString("product"));
    }
}
