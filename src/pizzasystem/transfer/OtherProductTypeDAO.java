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
import pizzasystem.data.OtherProductType;

public class OtherProductTypeDAO {
    
    private OtherProductType object;

    public OtherProductType getObject() {
        return object;
    }

    public OtherProductTypeDAO(OtherProductType object) {
        this.object = object;
    }
    
    /**
     *
     * @param result
     * @throws SQLException
     */
    protected static void setFromResultSet(ResultSet result, OtherProductType object) throws SQLException {
        object.setName(result.getString("name"));
        object.setPrice(result.getFloat("price"));
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
            setFromResultSet(result, otherProductType);
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
        stmt.setString(1, object.getName());
        stmt.setFloat(2, object.getPrice());
        stmt.executeUpdate();
    }
    
}
