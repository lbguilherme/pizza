package pizzasystem.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientRequest {

    public enum Status {
        Requested,
        ReadyForDelivery,
        Delivered
    }

    private int id = -1;
    private List<Pizza> pizzas = new ArrayList<>();
    private Status status;
    private Person client;

    /**
     * @return the pizzas
     */
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * @param pizzas the pizzas to set
     */
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public Float getTotalPrice() {
        /*
        Float pizzaPrice = 0f;
        Float outroPrice = 0f;
        if (getPizzas() != null){
            for (PizzaTaste pizza : getPizzas()){
                pizzaPrice += pizza.getPrice();
            }
        }
        if (outros != null){
            for (OtherProduct outro :outros){
                outroPrice += outro.getPrice();
            }
        }
        return pizzaPrice + outroPrice;
        */
        return 0f;
    }
    
    public void addPizza(Pizza pizza) {
        if (pizzas.size() > 0 && !pizza.getSize().equals(pizzas.get(0).getSize()))
            throw new RuntimeException();
        
        pizzas.add(pizza);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
    }

    private void insert(Connection db) throws SQLException {
        String query = "INSERT INTO ClientRequest VALUES(default, ?, ?);";
        PreparedStatement stmt = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, getClient().getPhoneNumber());
        stmt.setString(2, getStatus().name());
        stmt.executeUpdate();
        
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        } else {
            throw new SQLException("Inserting failed, no ID obtained.");
        }
    }

    private void update(Connection db) throws SQLException {
        String query = "UPDATE Person SET phoneNumber=?, status=? WHERE id=?;";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, getClient().getPhoneNumber());
        stmt.setString(2, getStatus().name());
        stmt.setInt(3, id);
        stmt.executeUpdate();
    }
    
    private void savePizzas(Connection db) throws SQLException {
        String deleteQuery = "DELETE FROM Pizza WHERE idClientRequest=?;";
        PreparedStatement deleteStmt = db.prepareStatement(deleteQuery);
        deleteStmt.setInt(1, id);
        deleteStmt.executeUpdate();
        
        String query = "INSERT INTO Pizza VALUES(?, ?, ?, ?, ?, ?);";
        for (int idx = 0; idx < pizzas.size(); ++idx) {
            Pizza pizza = pizzas.get(idx);
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setInt(2, idx);
            stmt.setString(3, pizza.getTaste1());
            stmt.setString(4, pizza.getTaste2());
            stmt.setString(5, pizza.getTaste3());
            stmt.setString(6, pizza.getSize().name());
            stmt.executeUpdate();
        }
    }

    public void save(Connection db) throws SQLException {
        if (id == -1) {
            insert(db);
        } else {
            update(db);
        }
        savePizzas(db);
    }
}
