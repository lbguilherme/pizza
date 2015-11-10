package pizzasystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee extends Person{

    public enum Role {
        Admin,
        Attendant,
        Cook,
        Delivery
    }
    
    private String user;
    private String hashPass;
    private Role role;
    private String cpf;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHashPass() {
        return hashPass;
    }

    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    protected void setFromResultSet(ResultSet result) throws SQLException {
        super.setFromResultSet(result);
        setUser(result.getString("user"));
        setHashPass(result.getString("hashPass"));
        setRole(Role.valueOf(result.getString("role")));
    }
    
    public static Employee fetch(Connection db, String user) throws SQLException {
        String query = "select * from Employee " +
                "left join Person on Employee.phoneNumber=Person.phoneNumber where user=?;";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, user);
        ResultSet result = stmt.executeQuery();
        if (!result.first())
            return null;
        
        Employee employee = new Employee();
        employee.setFromResultSet(result);
        return employee;
    }

    public void save(Connection db) throws SQLException {
        super.save(db);
        String query = "INSERT INTO Employee VALUES(?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE phoneNumber=VALUES(phoneNumber), " +
            "hashPass=VALUES(hashPass), role=VALUES(role), cpf=VALUES(cpf);";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, getUser());
        stmt.setString(2, getPhoneNumber());
        stmt.setString(3, getHashPass());
        stmt.setString(4, getRole().name());
        stmt.setString(5, getCpf());
        stmt.executeUpdate();
    }

}
