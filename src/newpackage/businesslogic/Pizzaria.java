package newpackage.businesslogic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import javax.swing.JOptionPane;
import pizzasystem.data.Client;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.DrinkType;
import pizzasystem.data.Employee;
import pizzasystem.data.Menu;
import pizzasystem.data.PizzaTaste;
import pizzasystem.utility.PasswordHasher;

public class Pizzaria {
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static ArrayList<Client> clients = new ArrayList<>();
    private static Menu menu = new Menu();
    private static Queue<ClientRequest> requests = new ArrayDeque<>();

    /**
     * @return the employees
     */
    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * @param aEmployees the employees to set
     */
    public static void setEmployees(ArrayList<Employee> aEmployees) {
        employees = aEmployees;
    }

    /**
     * @return the clients
     */
    public static ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * @param aClients the clients to set
     */
    public static void setClients(ArrayList<Client> aClients) {
        clients = aClients;
    }
    
    private Employee currentUser;
    
    public static void Pizzaria() {
        if (employees.isEmpty()) {
            Employee admin = new Employee();
            admin.setName("admin");
            admin.setUser("admin");
            admin.setHashPass(new PasswordHasher().hash("admin"));
            admin.setAddress("");
            admin.setCep("");
            admin.setPhoneNumber("");
            admin.setCpf("");
            employees.add(admin);
        }
    }
    
    public boolean doLogin(String user, String pass) {
        if (currentUser != null)
            throw new RuntimeException("Already logged in");
        
        PasswordHasher hasher = new PasswordHasher();
        String passHash = hasher.hash(pass);
        for (Employee emp : getEmployees()) {
            if (emp.getUser().equals(user) && emp.getHashPass().equals(passHash)) {
                currentUser = emp;
                return true;
            }
        }
        return false;
    }
    
    public Employee.Role currentEmployeeRole() {
        if (currentUser == null)
            throw new RuntimeException("Not logged in");
        
        return currentUser.getRole();
    }
    
    public static void addClient(Client client) {
        if (client == null){
            throw new RuntimeException("Can't insert null Client");
        }else{
            if (client.getAddress().equals("") ||
                    client.getCep().equals("") ||
            client.getPhoneNumber().equals("") ||
                   client.getName().equals("")) {
                JOptionPane.showMessageDialog(null, "Não foi possivel registrar ou atualizar cliente, informações incompletas!");
            }else{
                for (Client c : getClients()) {
                    if (c.getPhoneNumber().equals(client.getPhoneNumber())) {
                        getClients().remove(c);
                        break;
                    }
                }
                getClients().add(client);
                JOptionPane.showMessageDialog(null, "Cliente cadastrado/atualizado com sucesso");
            }
        }
    }   
    
    public static Client findClient(String phoneNumber) {
        for (Client client : getClients()) {
            if (client.getPhoneNumber().equals(phoneNumber)) {
                return client;
            }
        }
        return null;
    }
    
    public void addClientRequest(ClientRequest request) {
        if (request == null)
            throw new RuntimeException("Can't insert null ClientRequest");
        
        addClient(request.getClient());
    }
    
    public void registerPizza(PizzaTaste pizza){
        if (pizza.getTasteName() == null ||
                pizza.getPrice() == null ||
                pizza.getSize() == null){
            throw new RuntimeException("Couldn't register new pizza, this pizza is missing some information");
        }
        else{
            if (getMenu().getPizzas().contains(pizza)){
                throw new RuntimeException("Pizza already registered");
            }
            else{
            ArrayList<PizzaTaste> newMenu = (ArrayList<PizzaTaste>) getMenu().getPizzas();
            newMenu.add(pizza);
            getMenu().setPizzas(newMenu);
            }    
        }
    }
        
    public void registerDrink(DrinkType drink){
        if ((drink.getName() == null) ||
                (drink.getPrice() == null)){
            throw new RuntimeException("Couldn't register new drink, this drink is missing some information");
        }
        else{
            if (getMenu().getDrinks().contains(drink)){
                throw new RuntimeException("drink already registered");
            }
            else{
            ArrayList<DrinkType> newMenu = (ArrayList<DrinkType>) getMenu().getDrinks();
            newMenu.add(drink);
            getMenu().setDrinks(newMenu);
            }    
        }
    }
    
    public void registerEmployee(Employee employee){
        if (employee == null){
            throw new RuntimeException("Can't insert null drink");
        }
        else{
            if (employee.getAddress() == null ||
            employee.getCep() == null ||
            employee.getCpf() == null ||
    employee.getPhoneNumber() == null ||
           employee.getName() == null ||
       employee.getHashPass() == null ||
           employee.getRole() == null ||
           employee.getUser() == null){
                throw new RuntimeException("Unable to register employee, employee data missing.");
            }
            else{
                getEmployees().add(employee);
            }
        }
    }

    public static Menu getMenu() {
        return menu;
    }
}
