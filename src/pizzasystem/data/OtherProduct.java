/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzasystem.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OtherProduct {

    private String product;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
    
    protected void setFromResultSet(ResultSet result) throws SQLException {
        setProduct(result.getString("product"));
    }
    
}
