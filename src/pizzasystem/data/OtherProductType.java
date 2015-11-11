package pizzasystem.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabe
 */
public class OtherProductType{
    private String name;
    private Float price;

    /**
     *
     * @return retorna o nome do produto
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return retorna o valor do produto
     */
    public Float getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(Float price) {
        this.price = price;
    }
    
}
