package pizzasystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientRequest {

    public enum Status {
        Requested,
        ReadyForDelivery,
        Delivered
    }

    private int id = -1;
    private ArrayList<Pizza> pizzas = new ArrayList<>();
    private ArrayList<OtherProduct> others = new ArrayList<>();
    private Status status;
    private Person client;

    /**
     * @return the pizzas
     */
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * @return the products
     */
    public List<OtherProduct> getOthers() {
        return others;
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
        pizzas.add(pizza);
    }
    
    public void addOther(OtherProduct other) {
        others.add(other);
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
    
    protected void setFromResultSet(ResultSet result) throws SQLException {
        setStatus(Status.valueOf(result.getString("status")));
        client = new Person();
        client.setFromResultSet(result);
        id = result.getInt("idClientRequest");
    }
    
    protected static void fillPizzas(Connection db, List<ClientRequest> requests) throws SQLException {
        String query = "select * from Pizza;";
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        HashMap<Integer, ClientRequest> idToRequestTable = new HashMap<>();
        for (ClientRequest request : requests) {
            idToRequestTable.put(request.id, request);
        }
        
        HashMap<Integer, Integer> idToPizzasSize = new HashMap<>();
        while (result.next()) {
            int id = result.getInt("idClientRequest");
            int idx = result.getInt("idx");
            if (idToPizzasSize.containsKey(id)) {
                idToPizzasSize.put(id, Math.max(idToPizzasSize.get(id), idx+1));
            } else {
                idToPizzasSize.put(id, idx+1);
            }
        }
        result.beforeFirst();
        
        for (ClientRequest request : requests) {
            request.pizzas = new ArrayList<Pizza>();
            if (idToPizzasSize.containsKey(request.id)) {
                int size = idToPizzasSize.get(request.id);
                for (int i = 0; i < size; ++i)
                    request.pizzas.add(null);
            }
        }
        
        while (result.next()) {
            int id = result.getInt("idClientRequest");
            int idx = result.getInt("idx");
            Pizza pizza = new Pizza();
            pizza.setFromResultSet(result);
            ClientRequest request = idToRequestTable.get(id);
            request.pizzas.set(idx, pizza);
        }
    }
    
    protected static void fillOthers(Connection db, List<ClientRequest> requests) throws SQLException {
        String query = "select * from OtherProduct;";
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        HashMap<Integer, ClientRequest> idToRequestTable = new HashMap<>();
        for (ClientRequest request : requests) {
            idToRequestTable.put(request.id, request);
        }
        
        HashMap<Integer, Integer> idToOtherSize = new HashMap<>();
        while (result.next()) {
            int id = result.getInt("idClientRequest");
            int idx = result.getInt("idx");
            if (idToOtherSize.containsKey(id)) {
                idToOtherSize.put(id, Math.max(idToOtherSize.get(id), idx+1));
            } else {
                idToOtherSize.put(id, idx+1);
            }
        }
        result.beforeFirst();
        
        for (ClientRequest request : requests) {
            request.others = new ArrayList<OtherProduct>();
            if (idToOtherSize.containsKey(request.id)) {
                int size = idToOtherSize.get(request.id);
                for (int i = 0; i < size; ++i)
                    request.others.add(null);
            }
        }
        
        while (result.next()) {
            int id = result.getInt("idClientRequest");
            int idx = result.getInt("idx");
            OtherProduct other = new OtherProduct();
            other.setFromResultSet(result);
            ClientRequest request = idToRequestTable.get(id);
            request.others.set(idx, other);
        }
    }

    public static List<ClientRequest> fetchAll(Connection db) throws SQLException {
        String query = "select * from ClientRequest " +
                "left join Person on ClientRequest.phoneNumber=Person.phoneNumber;";
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        List<ClientRequest> list = new ArrayList<>();
        while (result.next()) {
            ClientRequest request = new ClientRequest();
            request.setFromResultSet(result);
            list.add(request);
        }
        
        fillPizzas(db, list);
        fillOthers(db, list);

        return list;
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
        String query = "UPDATE ClientRequest SET phoneNumber=?, status=? WHERE idClientRequest=?;";
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
    
    private void saveOthers(Connection db) throws SQLException {
        String deleteQuery = "DELETE FROM OtherProduct WHERE idClientRequest=?;";
        PreparedStatement deleteStmt = db.prepareStatement(deleteQuery);
        deleteStmt.setInt(1, id);
        deleteStmt.executeUpdate();
        
        String query = "INSERT INTO OtherProduct VALUES(?, ?, ?);";
        for (int idx = 0; idx < others.size(); ++idx) {
            OtherProduct other = others.get(idx);
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setInt(2, idx);
            stmt.setString(3, other.getProduct());
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
        saveOthers(db);
    }
}
