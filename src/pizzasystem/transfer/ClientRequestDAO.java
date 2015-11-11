package pizzasystem.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.OtherProduct;
import pizzasystem.data.Person;
import pizzasystem.data.Pizza;

public class ClientRequestDAO {
    
    private ClientRequest object;

    public ClientRequest getObject() {
        return object;
    }

    public ClientRequestDAO(ClientRequest object) {
        this.object = object;
    }
    
    
    /**
     *
     * @param result
     * @throws SQLException
     */
    protected static void setFromResultSet(ResultSet result, ClientRequest object) throws SQLException {
        object.setStatus(ClientRequest.Status.valueOf(result.getString("status")));
        Person client = new Person();
        PersonDAO.setFromResultSet(result, client);
        object.setClient(client);
        object.setId(result.getInt("idClientRequest"));
    }
    
    /**
     *
     * @param db
     * @param requests
     * @throws SQLException
     */
    protected static void fillPizzas(Connection db, List<ClientRequest> requests) throws SQLException {
        String query = "select * from Pizza;";
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        HashMap<Integer, ClientRequest> idToRequestTable = new HashMap<>();
        for (ClientRequest request : requests) {
            idToRequestTable.put(request.getId(), request);
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
            request.setPizzas(new ArrayList<>());
            if (idToPizzasSize.containsKey(request.getId())) {
                int size = idToPizzasSize.get(request.getId());
                for (int i = 0; i < size; ++i)
                    request.getPizzas().add(null);
            }
        }
        
        while (result.next()) {
            int id = result.getInt("idClientRequest");
            int idx = result.getInt("idx");
            Pizza pizza = new Pizza();
            PizzaDAO.setFromResultSet(result, pizza);
            ClientRequest request = idToRequestTable.get(id);
            request.getPizzas().set(idx, pizza);
        }
    }
    
    /**
     *
     * @param db
     * @param requests
     * @throws SQLException
     */
    protected static void fillOthers(Connection db, List<ClientRequest> requests) throws SQLException {
        String query = "select * from OtherProduct;";
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        HashMap<Integer, ClientRequest> idToRequestTable = new HashMap<>();
        for (ClientRequest request : requests) {
            idToRequestTable.put(request.getId(), request);
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
            request.setOthers(new ArrayList<>());
            if (idToOtherSize.containsKey(request.getId())) {
                int size = idToOtherSize.get(request.getId());
                for (int i = 0; i < size; ++i)
                    request.getOthers().add(null);
            }
        }
        
        while (result.next()) {
            int id = result.getInt("idClientRequest");
            int idx = result.getInt("idx");
            OtherProduct other = new OtherProduct();
            OtherProductDAO.setFromResultSet(result, other);
            ClientRequest request = idToRequestTable.get(id);
            request.getOthers().set(idx, other);
        }
    }

    /**
     *
     * @param db
     * @return Retorna a lista de pedidos que esta no database
     * @throws SQLException
     */
    public static List<ClientRequest> fetchAll(Connection db) throws SQLException {
        String query = "select * from ClientRequest " +
                "left join Person on ClientRequest.phoneNumber=Person.phoneNumber;";
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        List<ClientRequest> list = new ArrayList<>();
        while (result.next()) {
            ClientRequest request = new ClientRequest();
            setFromResultSet(result, request);
            list.add(request);
        }
        
        fillPizzas(db, list);
        fillOthers(db, list);

        return list;
    }

    private void insert(Connection db) throws SQLException {
        String query = "INSERT INTO ClientRequest VALUES(default, ?, ?);";
        PreparedStatement stmt = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, object.getClient().getPhoneNumber());
        stmt.setString(2, object.getStatus().name());
        stmt.executeUpdate();
        
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            object.setId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Inserting failed, no ID obtained.");
        }
    }

    private void update(Connection db) throws SQLException {
        String query = "UPDATE ClientRequest SET phoneNumber=?, status=? WHERE idClientRequest=?;";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, object.getClient().getPhoneNumber());
        stmt.setString(2, object.getStatus().name());
        stmt.setInt(3, object.getId());
        stmt.executeUpdate();
    }
    
    private void savePizzas(Connection db) throws SQLException {
        String deleteQuery = "DELETE FROM Pizza WHERE idClientRequest=?;";
        PreparedStatement deleteStmt = db.prepareStatement(deleteQuery);
        deleteStmt.setInt(1, object.getId());
        deleteStmt.executeUpdate();
        
        String query = "INSERT INTO Pizza VALUES(?, ?, ?, ?, ?, ?);";
        for (int idx = 0; idx < object.getPizzas().size(); ++idx) {
            Pizza pizza = object.getPizzas().get(idx);
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, object.getId());
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
        deleteStmt.setInt(1, object.getId());
        deleteStmt.executeUpdate();
        
        String query = "INSERT INTO OtherProduct VALUES(?, ?, ?);";
        for (int idx = 0; idx < object.getOthers().size(); ++idx) {
            OtherProduct other = object.getOthers().get(idx);
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, object.getId());
            stmt.setInt(2, idx);
            stmt.setString(3, other.getProduct());
            stmt.executeUpdate();
        }
    }

    /**
     *
     * @param db
     * @throws SQLException
     */
    public void save(Connection db) throws SQLException {
        if (object.getId() == -1) {
            insert(db);
        } else {
            update(db);
        }
        savePizzas(db);
        saveOthers(db);
    }
    
    public void deleteRequests(Connection db) throws SQLException{
        String deleteQuery = "DELETE FROM ClientRequest WHERE idClientRequest=?;";
        PreparedStatement deleteStmt = db.prepareStatement(deleteQuery);
        deleteStmt.setInt(1, object.getId());
        deleteStmt.executeUpdate();
        
        deletePizzas(db);
        deleteOthers(db);
    }
    
    private void deletePizzas(Connection db) throws SQLException{
        String deleteQuery = "DELETE FROM Pizza WHERE idClientRequest=?;";
        PreparedStatement deleteStmt = db.prepareStatement(deleteQuery);
        deleteStmt.setInt(1, object.getId());
        deleteStmt.executeUpdate();
    }
    
    private void deleteOthers(Connection db) throws SQLException{
        String deleteQuery = "DELETE FROM OtherProduct WHERE idClientRequest=?;";
        PreparedStatement deleteStmt = db.prepareStatement(deleteQuery);
        deleteStmt.setInt(1, object.getId());
        deleteStmt.executeUpdate();
    }
}
