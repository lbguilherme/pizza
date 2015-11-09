package businesslogic;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import javax.swing.JOptionPane;
import pizzasystem.data.Client;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.ClientRequest.Status;
import pizzasystem.data.OtherProduct;
import pizzasystem.data.Employee;
import pizzasystem.data.Menu;
import pizzasystem.data.PizzaTaste;
import pizzasystem.utility.PasswordHasher;
import pizzasystem.ui.MainFrame;

public class Pizzaria{
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();
    private Menu menu = new Menu();
    private Queue<ClientRequest> requests = new ArrayDeque<>();
    
    private boolean ordering = false;
    private ClientRequest currentRequest = new ClientRequest();

    /**
     * @return the employees
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * @param aEmployees the employees to set
     */
    public void setEmployees(ArrayList<Employee> aEmployees) {
        employees = aEmployees;
    }

    /**
     * @return the clients
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * @param aClients the clients to set
     */
    public void setClients(ArrayList<Client> aClients) {
        clients = aClients;
    }
    
    private Employee currentUser;
    
    public void Pizzaria() {
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
    
    public int doLogin(String user, String pass) {
        if (getCurrentUser() != null){
            JOptionPane.showMessageDialog(null, "Já existe um usuario logado!");
            return 0;
        }
        PasswordHasher hasher = new PasswordHasher();
        String passHash = hasher.hash(pass);
        for (Employee emp : getEmployees()) {
            if (emp.getUser().equals(user) && emp.getHashPass().equals(passHash)) {
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
        }
        return 0;
    }
    
    public Employee.Role currentEmployeeRole() {
        if (getCurrentUser() == null)
            throw new RuntimeException("Not logged in");
        
        return getCurrentUser().getRole();
    }
    
    public void addClient(Client client) {
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
    
    public Client findClient(String phoneNumber) {
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
    
    public int registerPizza(PizzaTaste pizza){
        if (pizza.getTasteName()[0].equals("") ||
                pizza.getPrice() == null ||
                pizza.getSize() == null){
            JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar pizza, há informações faltando!");
            return 0;
        }
        for(PizzaTaste pizzainmenu : getMenu().getPizzas()){
            if (pizzainmenu.getTasteName()[0].equals(pizza.getTasteName()[0]) &&
                    pizzainmenu.getSize() == pizza.getSize()){
                pizzainmenu.setPrice(pizza.getPrice());
                JOptionPane.showMessageDialog(null, "Valor da pizza atualizado.");
                return 0;
            }
        }
        for(PizzaTaste pizzainmenu : getMenu().getPizzas()){
            if (pizzainmenu.getTasteName()[0].equals(pizza.getTasteName()[0])){
                ArrayList<PizzaTaste> newMenu = (ArrayList<PizzaTaste>) getMenu().getPizzas();
                newMenu.add(pizza);
                getMenu().setPizzas(newMenu);
                JOptionPane.showMessageDialog(null, "Pizza cadastrada com sucesso.");
                return 0;
            }
        }
        ArrayList<PizzaTaste> newMenu = (ArrayList<PizzaTaste>) getMenu().getPizzas();
        newMenu.add(pizza);
        getMenu().setPizzas(newMenu);
        JOptionPane.showMessageDialog(null, "Pizza cadastrada com sucesso.");
            return 1;
    }
        
    public int registerOutro(OtherProduct outro){
        if ((outro.getName() == null) ||
                (outro.getPrice() == null)){
            JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar o produto, há informações faltando!");
            return 0;
        }
        else{
            for(OtherProduct outroinmenu : getMenu().getOutros()){
                if (outroinmenu.getName().equals(outro.getName())){
                    outroinmenu.setPrice(outro.getPrice());
                    JOptionPane.showMessageDialog(null, "Valor do produto atualizado com sucesso.");
                    return 0;
                }
            }
            ArrayList<OtherProduct> newMenu = (ArrayList<OtherProduct>) getMenu().getOutros();
            newMenu.add(outro);
            getMenu().setOutros(newMenu);
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso.");
            return 1;
        }
    }
    
    public void registerEmployee(Employee employee){
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
                getEmployees().add(employee);
                JOptionPane.showMessageDialog(null, "Funcionario cadastrado com sucesso.");
            }
        }
    }
    
    public Float calculatePizzaPrice(PizzaTaste pizza) {
        Float maxPrice = 0f;
        for (String taste : pizza.getTasteName()){
            for (PizzaTaste pizzainmenu : getMenu().getPizzas()) {
                if ((pizzainmenu.getTasteName()[0].equals(taste)) &&
                        pizzainmenu.getSize() == pizza.getSize()){
                    maxPrice = Float.max(maxPrice, pizzainmenu.getPrice());
                    break;
                }
            }
        }
        return maxPrice;
    }
    
    public Float getOtherPrice(String name){
        for(OtherProduct outro : menu.getOutros()){
            if (outro.getName().equals(name)){
                return outro.getPrice();
            }
        }
        return 0f;
    }

    public Menu getMenu() {
        return menu;
    }


    public void setMenu(Menu aMenu) {
        menu = aMenu;
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
     * @return the ordering
     */
    public boolean isOrdering() {
        return ordering;
    }

    /**
     * @param ordering the ordering to set
     */
    public void setOrdering(boolean ordering) {
        this.ordering = ordering;
    }

    /**
     * @return the currentRequest
     */
    public ClientRequest getCurrentRequest() {
        return currentRequest;
    }

    /**
     * @param currentRequest the currentRequest to set
     */
    public void setCurrentRequest(ClientRequest currentRequest) {
        this.currentRequest = currentRequest;
    }

    /**
     * @return the requests
     */
    public Queue<ClientRequest> getRequests() {
        return requests;
    }

    /**
     * @param requests the requests to set
     */
    public void setRequests(Queue<ClientRequest> requests) {
        this.requests = requests;
    }
    
    public void finishPizza() {
        Iterator<ClientRequest> oi = this.getRequests().iterator();
        ClientRequest atual;
        
        while(oi.hasNext()) {
            atual = oi.next();
            if(atual.getStatus() == Status.Requested) {
                atual.setStatus(Status.ReadyForDelivery);
                break;
            }
        }
    }
    
    public void finishOrder() {
        if (this.getRequests().peek().getStatus() == Status.Delivered){
            ClientRequest removed = this.getRequests().poll();
        }
        Iterator<ClientRequest> oi = this.getRequests().iterator();
        ClientRequest atual;
        
        while(oi.hasNext()) {
            atual = oi.next();
            if(atual.getStatus() == Status.ReadyForDelivery) {
                atual.setStatus(Status.Delivered);
                break;
            }
        }
    }
}
