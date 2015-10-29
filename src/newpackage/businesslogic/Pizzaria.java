package newpackage.businesslogic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import pizzasystem.data.Client;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.Employee;
import pizzasystem.data.Menu;
import pizzasystem.data.PizzaTaste;
import pizzasystem.utility.PasswordHasher;

public class Pizzaria {
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();
    private Menu menu = new Menu();
    private Queue<ClientRequest> requests = new ArrayDeque<>();
    
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
    
        public void registerPizza(String pizzaName, Float[] prices){
        if ((pizzaName == null) ||
                (prices == null)){
            throw new RuntimeException("Can't insert null pizza");
        }
        else{
            if (menu.getPizzas().containsKey(pizzaName)){
                throw new RuntimeException("Pizza already registered");
            }
            else{
            HashMap<String, Float[]> newMenu = (HashMap<String, Float[]>) menu.getPizzas();
            newMenu.put(pizzaName, prices);
            menu.setPizzas(newMenu);
            }    
        }
    }
        
        public void registerDrink(String drinkName, Float price){
        if ((drinkName == null) ||
                (price == null)){
            throw new RuntimeException("Can't insert null drink");
        }
        else{
            if (menu.getDrinks().containsKey(drinkName)){
                throw new RuntimeException("drink already registered");
            }
            else{
            HashMap<String, Float> newMenu = (HashMap<String, Float>) menu.getDrinks();
            newMenu.put(drinkName, price);
            menu.setDrinks(newMenu);
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
}
