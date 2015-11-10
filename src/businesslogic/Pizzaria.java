package businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import pizzasystem.data.Person;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.OtherProductType;
import pizzasystem.data.Employee;
import pizzasystem.data.Menu;
import pizzasystem.data.Pizza;
import pizzasystem.data.PizzaTaste;
import pizzasystem.utility.PasswordHasher;

public class Pizzaria{
    private Connection db;

    private Connection getDb() throws SQLException {
        if (db == null) {
            String url = "jdbc:mysql://45.55.166.39:3306/pizza";
            String user = "root";
            String password = "pizza";
            db = DriverManager.getConnection(url, user, password);
        }

        return db;
    }

    private Employee currentUser;

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

    public Employee.Role currentEmployeeRole() {
        if (getCurrentUser() == null)
            throw new RuntimeException("Not logged in");

        return getCurrentUser().getRole();
    }

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

    public Person findClient(String phoneNumber) throws SQLException {
        return Person.fetch(getDb(), phoneNumber);
    }

    public void addClientRequest(ClientRequest request) throws SQLException {
        if (request == null)
            throw new RuntimeException("Can't insert null ClientRequest");

        addClient(request.getClient());
    }

    public void registerPizza(PizzaTaste pizza) throws SQLException {
        // TODO: Checar todos os atributos
        pizza.save(getDb());
        throw new RuntimeException("Pizza cadastrada com sucesso.");
    }

    public void registerOutro(OtherProductType outro) throws SQLException {
        // TODO: Checar todos os atributos
        outro.save(getDb());
        JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso.");
    }

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
                JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar funcionario, há informações faltando");
            }
            else{
                employee.save(getDb());

                JOptionPane.showMessageDialog(null, "Funcionario cadastrado com sucesso.");
            }
        }
    }
    
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
     */
    public List<ClientRequest> getRequests() throws SQLException {
        return ClientRequest.fetchAll(getDb());
    }

    public void addRequest(ClientRequest request) throws SQLException {
        request.save(getDb());
    }
    
    public Float calculatePizzaPrice(Pizza pizza) throws SQLException{
        Float maxPrice = 0f;
        maxPrice = Float.max(maxPrice, getPizzaPrice(pizza.getTaste1(), pizza.getSize()));
        maxPrice = Float.max(maxPrice, getPizzaPrice(pizza.getTaste2(), pizza.getSize()));
        maxPrice = Float.max(maxPrice, getPizzaPrice(pizza.getTaste3(), pizza.getSize()));
        return maxPrice;
    } 
    
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
    
}
