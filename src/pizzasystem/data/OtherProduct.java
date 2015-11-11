/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzasystem.data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gabe
 */
public class OtherProduct {

    private String product;

    /**
     *
     * @return retorna o produto
     */
    public String getProduct() {
        return product;
    }

    /**
     *
     * @param product
     */
    public void setProduct(String product) {
        this.product = product;
    }
    
}
