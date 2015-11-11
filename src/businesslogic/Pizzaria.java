package businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pizzasystem.data.Person;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.OtherProductType;
import pizzasystem.data.Employee;
import pizzasystem.data.Menu;
import pizzasystem.data.Pizza;
import pizzasystem.data.PizzaTaste;
import pizzasystem.utility.PasswordHasher;

/**
 *
 * @author Gabe
 */
public class Pizzaria{
    private Connection db;

    private Connection getDb() throws SQLException {
        if (db == null) {
            String url = "jdbc:mysql://45.55.166.39:3306/pizza";
            String user = "root";
            String password = "pizza";
            try {  
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Pizzaria.class.getName()).log(Level.SEVERE, null, ex);
            }
            db = DriverManager.getConnection(url, user, password);
        }

        return db;
    }

    private Employee currentUser;

    /**
     *
     * @param user
     * @param pass
     * @return Faz login com o usuario e password fornecidos e retorna um inteiro representando que tipo de funcionario fez login
     * @throws SQLException
     */
    public int doLogin(String user, String pass) throws SQLException {
        if (getCurrentUser() != null) {
            JOptionPane.showMessageDialog(null, "Já existe um usuario logado!");
            return 0;
        }
        PasswordHasher hasher = new PasswordHasher();
        String passHash = hasher.hash(pass);

        Employee emp = Employee.fetch(getDb(), user);
        if (emp != null && emp.getHashPass().equals(passHash)) {
            setCurrentUser(emp);
            switch (getCurrentUser().getRole()){
                case Admin:
                    return 1;
                case Attendant:
                    return 2;
                case Cook:
                    return 3;
                case Delivery:
                    return 4;
                default:
                    return 0;
            }
        }
        return 0;
    }

    /**
     *
     * @return 
     */
    /*public Employee.Role currentEmployeeRole() {
        if (getCurrentUser() == null)
            throw new RuntimeException("Not logged in");

        return getCurrentUser().getRole();
    }*/

    /**
     *
     * @param client
     * @throws SQLException
     */
    public void addClient(Person client) throws SQLException {
        if (client == null){
            throw new RuntimeException("Can't insert null Client");
        }else{
            if (client.getAddress().equals("") ||
                    client.getCep().equals("") ||
            client.getPhoneNumber().equals("") ||
                   client.getName().equals("")) {
                throw new RuntimeException("Não foi possivel registrar ou atualizar cliente, informações incompletas!");
            }else{
                client.save(getDb());
                throw new RuntimeException("Cliente cadastrado/atualizado com sucesso");
            }
        }
    }

    /**
     *
     * @param phoneNumber
     * @return Retorna o cliente que tem o numero de telefone passado como argumento
     * @throws SQLException
     */
    public Person findClient(String phoneNumber) throws SQLException {
        if(Person.fetch(getDb(), phoneNumber) != null){
             return Person.fetch(getDb(), phoneNumber);
        }else{
            throw new RuntimeException("Cliente não encontrado.");
        }
       
    }

    /**
     *
     * @param request
     * @throws SQLException
     */
    public void addClientRequest(ClientRequest request) throws SQLException {
        if (request == null)
            throw new RuntimeException("Can't insert null ClientRequest");

        addClient(request.getClient());
    }

    /**
     *
     * @param pizza
     * @throws SQLException
     */
    public void registerPizza(PizzaTaste pizza) throws SQLException {
        // TODO: Checar todos os atributos
        pizza.save(getDb());
        throw new RuntimeException("Pizza cadastrada com sucesso.");
    }

    /**
     *
     * @param outro
     * @throws SQLException
     */
    public void registerOutro(OtherProductType outro) throws SQLException {
        // TODO: Checar todos os atributos
        outro.save(getDb());
        throw new RuntimeException("Produto cadastrado com sucesso.");
    }

    /**
     *
     * @param employee
     * @throws SQLException
     */
    public void registerEmployee(Employee employee) throws SQLException {
        if (employee == null){
            throw new RuntimeException("Can't insert null employee");
        }
        else{
            if (employee.getAddress() == null ||
            employee.getCep().equals("") ||
            employee.getCpf().equals("") ||
    employee.getPhoneNumber().equals("") ||
           employee.getName().equals("") ||
       employee.getHashPass().equals("") ||
           employee.getRole() == null ||
           employee.getUser().equals("")){
                throw new RuntimeException("Não foi possivel cadastrar funcionario, há informações faltando");
            }
            else{
                employee.save(getDb());
                throw new RuntimeException("Funcionario cadastrado com sucesso.");
            }
        }
    }
    
    /**
     *
     * @return Retorna o menu da pizzaria armazenado no database
     * @throws SQLException
     */
    public Menu getMenu() throws SQLException {
        return new Menu(getDb());
    }

    /**
     * @return the currentUser
     */
    public Employee getCurrentUser() {
        return currentUser;
    }

    /**
     * @param aCurrentUser the currentUser to set
     */
    public void setCurrentUser(Employee aCurrentUser) {
        currentUser = aCurrentUser;
    }
    
    /**
     *
     * @param otherName
     * @return retorna o produto que tem o nome passado como argumento
     * @throws SQLException
     */
    public OtherProductType findOtherProduct(String otherName) throws SQLException{
        for(OtherProductType otherInMenu : this.getMenu().getOtherProductTypes()){
            if (otherInMenu.getName().equals(otherName)){
                return otherInMenu;
            }
        }
        return null;
    }

    /**
     * @return the requests
     * @throws java.sql.SQLException
     */
    public List<ClientRequest> getRequests() throws SQLException {
        return ClientRequest.fetchAll(getDb());
    }

    /**
     *
     * @param request
     * @throws SQLException
     */
    public void addRequest(ClientRequest request) throws SQLException {
        if (request.getClient() != null && request.getStatus() != null){
            request.save(getDb());
        } else {
            throw new RuntimeException("Não foi possivel realizar o pedido.");
        }
    }
    
    /**
     *
     * @param pizza
     * @return Retorna o valor da pizza baseado nos 3 sabores pedidos, o valor da pizza será o maior valor entre as pedidas
     * @throws SQLException
     */
    public Float calculatePizzaPrice(Pizza pizza) throws SQLException{
        Float maxPrice = 0f;
        maxPrice = Float.max(maxPrice, getPizzaPrice(pizza.getTaste1(), pizza.getSize()));
        maxPrice = Float.max(maxPrice, getPizzaPrice(pizza.getTaste2(), pizza.getSize()));
        maxPrice = Float.max(maxPrice, getPizzaPrice(pizza.getTaste3(), pizza.getSize()));
        return maxPrice;
    } 
    
    /**
     *
     * @param pizzaName
     * @param size
     * @return Retorna o valor da pizza com o nome e tamanho passados como argumento 
     * @throws SQLException
     */
    public Float getPizzaPrice(String pizzaName, PizzaTaste.Size size) throws SQLException{
        for (PizzaTaste pizzaInMenu : getMenu().getPizzaTastes()) {
            if ((pizzaInMenu.getName().equals(pizzaName))){
                switch (size){
                    case Medium:
                        return pizzaInMenu.getPriceMedium();
                    case Big:
                        return pizzaInMenu.getPriceBig();
                    case Family:
                        return pizzaInMenu.getPriceFamily();
                }
            }
        }
        return 0f;
    }
    
    /**
     *
     * @throws SQLException
     */
    public void finishOrder() throws SQLException {
        for(ClientRequest request : getRequests()){
            if (request.getStatus() == ClientRequest.Status.ReadyForDelivery){
                request.setStatus(ClientRequest.Status.Delivered);
                request.save(getDb());
                return;               
            }
        }
        throw new RuntimeException("Não foi possivel finalizar o pedido, não há pedidos para finalizar.");
    }
    
    /**
     *
     * @throws SQLException
     */
    public void finishPizza() throws SQLException {
        for(ClientRequest request : getRequests()){
            if (request.getStatus() == ClientRequest.Status.Requested){
                request.setStatus(ClientRequest.Status.ReadyForDelivery);                
                request.save(getDb());
                return;
            }
        }
        throw new RuntimeException("Não foi possivel finalizar pizza, não há pizzas para finalizar.");
    }
    
    public void removeDelivered() throws SQLException{
        for (ClientRequest request : getRequests()){
            if (request.getStatus() == ClientRequest.Status.Delivered){
                request.deletePizzas(getDb());
                request.deleteOthers(getDb());
                request.deleteRequests(getDb());
            }                
        }
        throw new RuntimeException("Pizzas entregues removidas da lista com sucesso.");
    }
}
