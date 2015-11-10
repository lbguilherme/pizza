package pizzasystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {
    private String name;
    private String address;
    private String phoneNumber;
    private String cep;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    protected void setFromResultSet(ResultSet result) throws SQLException {
        setPhoneNumber(result.getString("phoneNumber"));
        setName(result.getString("name"));
        setAddress(result.getString("address"));
        setCep(result.getString("cep"));
    }

    public static Person fetch(Connection db, String phoneNumber) throws SQLException {
        String query = "select * from Person where phoneNumber=?;";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, phoneNumber);
        ResultSet result = stmt.executeQuery();
        if (!result.first())
            return null;

        Person person = new Person();
        person.setFromResultSet(result);
        return person;
    }

    public void save(Connection db) throws SQLException {
        String query = "INSERT INTO Person VALUES(?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE phoneNumber=VALUES(phoneNumber), "+
            "name=VALUES(name), address=VALUES(address), cep=VALUES(cep);";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, getPhoneNumber());
        stmt.setString(2, getName());
        stmt.setString(3, getAddress());
        stmt.setString(4, getCep());
        stmt.executeUpdate();
    }

}
