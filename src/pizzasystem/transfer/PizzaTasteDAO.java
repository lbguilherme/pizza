
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzasystem.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pizzasystem.data.PizzaTaste;

public class PizzaTasteDAO {
    
    private PizzaTaste object;

    public PizzaTaste getObject() {
        return object;
    }

    public PizzaTasteDAO(PizzaTaste object) {
        this.object = object;
    }
    
    /**
     *
     * @param result
     * @throws SQLException
     */
    protected static void setFromResultSet(ResultSet result, PizzaTaste object) throws SQLException {
        object.setName(result.getString("name"));
        object.setPriceMedium(result.getFloat("priceMedium"));
        object.setPriceBig(result.getFloat("priceBig"));
        object.setPriceFamily(result.getFloat("priceFamily"));
    }

    /**
     *
     * @param db
     * @return retorna a lista de todas as pizzas que estão no database
     * @throws SQLException
     */
    public static List<PizzaTaste> fetchAll(Connection db) throws SQLException {
        String query = "select * from PizzaTaste;";
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        List<PizzaTaste> list = new ArrayList<>();
        while (result.next()) {
            PizzaTaste pizzaTaste = new PizzaTaste();
            setFromResultSet(result, pizzaTaste);
            list.add(pizzaTaste);
        }

        return list;
    }

    /**
     *
     * @param db
     * @throws SQLException
     */
    public void save(Connection db) throws SQLException {
        String query = "INSERT INTO PizzaTaste VALUES(?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE priceMedium=VALUES(priceMedium), " +
            "priceBig=VALUES(priceBig), priceFamily=VALUES(priceFamily);";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, object.getName());
        stmt.setFloat(2, object.getPriceMedium());
        stmt.setFloat(3, object.getPriceBig());
        stmt.setFloat(4, object.getPriceFamily());
        stmt.executeUpdate();
    }
}
