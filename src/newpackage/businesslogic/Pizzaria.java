package newpackage.businesslogic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import pizzasystem.data.Client;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.DrinkType;
import pizzasystem.data.Employee;
import pizzasystem.data.Menu;
import pizzasystem.data.PizzaTaste;
import pizzasystem.utility.PasswordHasher;

public class Pizzaria {
    private static final ArrayList<Employee> employees = new ArrayList<>();
    private static final ArrayList<Client> clients = new ArrayList<>();
    private static final Menu menu = new Menu();
    private static final Queue<ClientRequest> requests = new ArrayDeque<>();
    
    private Employee currentUser;
    
    Pizzaria() {
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
        for (Employee emp : employees) {
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
    
    private void addClient(Client client) {
        if (client == null)
            throw new RuntimeException("Can't insert null Client");
        
        if (client.getAddress() == null ||
                client.getCep() == null ||
                client.getCpf() == null ||
        client.getPhoneNumber() == null ||
               client.getName() == null) {
            throw new RuntimeException("");
        }
        
        for (Client c : clients) {
            if (c.getPhoneNumber().equals(client.getPhoneNumber())) {
                clients.remove(c);
                break;
            }
        }
        clients.add(client);
    }
    
    public Client findClient(String phoneNumber) {
        for (Client client : clients) {
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
                employees.add(employee);
            }
        }
    }

    public static Menu getMenu() {
        return menu;
    }
}
