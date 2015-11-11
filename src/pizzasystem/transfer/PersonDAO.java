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
import pizzasystem.data.Person;

public class PersonDAO {
    
    private Person object;

    public Person getObject() {
        return object;
    }

    public PersonDAO(Person object) {
        this.object = object;
    }

    /**
     *
     * @param result
     * @throws SQLException
     */
    protected static void setFromResultSet(ResultSet result, Person object) throws SQLException {
        object.setPhoneNumber(result.getString("phoneNumber"));
        object.setName(result.getString("name"));
        object.setAddress(result.getString("address"));
        object.setCep(result.getString("cep"));
    }

    /**
     *
     * @param db
     * @param phoneNumber
     * @return retorna a lista de pessoa que esta no database
     * @throws SQLException
     */
    public static Person fetch(Connection db, String phoneNumber) throws SQLException {
        String query = "select * from Person where phoneNumber=?;";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, phoneNumber);
        ResultSet result = stmt.executeQuery();
        if (!result.first())
            return null;

        Person person = new Person();
        setFromResultSet(result, person);
        return person;
    }

    /**
     *
     * @param db
     * @throws SQLException
     */
    public void save(Connection db) throws SQLException {
        String query = "INSERT INTO Person VALUES(?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE phoneNumber=VALUES(phoneNumber), "+
            "name=VALUES(name), address=VALUES(address), cep=VALUES(cep);";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, object.getPhoneNumber());
        stmt.setString(2, object.getName());
        stmt.setString(3, object.getAddress());
        stmt.setString(4, object.getCep());
        stmt.executeUpdate();
    }
}
