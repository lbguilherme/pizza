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
    
    /**
     *
     * @param result
     * @throws SQLException
     */
    protected void setFromResultSet(ResultSet result) throws SQLException {
        setName(result.getString("name"));
        setPrice(result.getFloat("price"));
    }

    /**
     *
     * @param db
     * @return Retorna a lista com todos os produtos disponiveis do database
     * @throws SQLException
     */
    public static List<OtherProductType> fetchAll(Connection db) throws SQLException {
        String query = "select * from OtherProductType;";
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        List<OtherProductType> list = new ArrayList<>();
        while (result.next()) {
            OtherProductType otherProductType = new OtherProductType();
            otherProductType.setFromResultSet(result);
            list.add(otherProductType);
        }

        return list;
    }

    /**
     *
     * @param db
     * @throws SQLException
     */
    public void save(Connection db) throws SQLException {
        String query = "INSERT INTO OtherProductType VALUES(?, ?) " +
            "ON DUPLICATE KEY UPDATE price=VALUES(price);";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, getName());
        stmt.setFloat(2, getPrice());
        stmt.executeUpdate();
    }
    
    
}
