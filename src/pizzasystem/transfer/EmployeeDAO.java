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
import pizzasystem.data.Employee;

public class EmployeeDAO {
    
    private Employee object;

    public Employee getObject() {
        return object;
    }

    public EmployeeDAO(Employee object) {
        this.object = object;
    }

    /**
     *
     * @param result
     * @throws SQLException
     */
    protected static void setFromResultSet(ResultSet result, Employee object) throws SQLException {
        PersonDAO.setFromResultSet(result, object);
        object.setUser(result.getString("user"));
        object.setHashPass(result.getString("hashPass"));
        object.setRole(Employee.Role.valueOf(result.getString("role")));
    }
    
    /**
     *
     * @param db
     * @param user
     * @return Retorna todos os funcionarios que estão no database
     * @throws SQLException
     */
    public static Employee fetch(Connection db, String user) throws SQLException {
        String query = "select * from Employee " +
                "left join Person on Employee.phoneNumber=Person.phoneNumber where user=?;";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, user);
        ResultSet result = stmt.executeQuery();
        if (!result.first())
            return null;
        
        Employee employee = new Employee();
        setFromResultSet(result, employee);
        return employee;
    }

    /**
     *
     * @param db
     * @throws SQLException
     */
    public void save(Connection db) throws SQLException {
        new PersonDAO(object).save(db);
        String query = "INSERT INTO Employee VALUES(?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE phoneNumber=VALUES(phoneNumber), " +
            "hashPass=VALUES(hashPass), role=VALUES(role), cpf=VALUES(cpf);";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, object.getUser());
        stmt.setString(2, object.getPhoneNumber());
        stmt.setString(3, object.getHashPass());
        stmt.setString(4, object.getRole().name());
        stmt.setString(5, object.getCpf());
        stmt.executeUpdate();
    }
}
