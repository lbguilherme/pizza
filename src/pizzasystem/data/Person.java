package pizzasystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gabe
 */
public class Person {
    private String name;
    private String address;
    private String phoneNumber;
    private String cep;

    /**
     *
     * @return o numero de telefone da pessoa
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return retorna o cep da pessoa
     */
    public String getCep() {
        return cep;
    }

    /**
     *
     * @param cep
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     *
     * @return retorna o nome da pessoa
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
     * @return retorna o endereço da pessoa
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @param result
     * @throws SQLException
     */
    protected void setFromResultSet(ResultSet result) throws SQLException {
        setPhoneNumber(result.getString("phoneNumber"));
        setName(result.getString("name"));
        setAddress(result.getString("address"));
        setCep(result.getString("cep"));
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
        person.setFromResultSet(result);
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
        stmt.setString(1, getPhoneNumber());
        stmt.setString(2, getName());
        stmt.setString(3, getAddress());
        stmt.setString(4, getCep());
        stmt.executeUpdate();
    }

}
