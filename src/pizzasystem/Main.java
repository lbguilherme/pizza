package pizzasystem;

import businesslogic.Pizzaria;
import pizzasystem.data.Menu;
import java.lang.NullPointerException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.Employee;
import pizzasystem.data.PizzaTaste;
import pizzasystem.utility.PasswordHasher;

public class Main {
    
    private static Pizzaria pizzaria = new Pizzaria();
    
    public static void main(String[] args) {
        if (pizzaria.getEmployees().isEmpty()) {
            Employee admin = new Employee();
            admin.setName("admin");
            admin.setUser("admin");
            admin.setHashPass(new PasswordHasher().hash("admin"));
            admin.setAddress("");
            admin.setCep("");
            admin.setPhoneNumber("");
            admin.setCpf("");
            admin.setRole(Employee.Role.Admin);
            pizzaria.getEmployees().add(admin);
            
            ClientRequest newRequest = new ClientRequest();
            PizzaTaste newPizza = new PizzaTaste();
            newPizza.setTastes(new String[] {"bazinga", "felipe", "bisnaga"});
            ArrayList<PizzaTaste> newPizzas = new ArrayList<>();
            newPizzas.add(newPizza);
            newRequest.setPizzas(newPizzas);
            newRequest.setStatus(ClientRequest.Status.Requested);
            Queue<ClientRequest> newRequests = pizzaria.getRequests();
            newRequests.add(newRequest);
            pizzaria.setRequests(newRequests);
        }
        pizzasystem.ui.MainFrame.main(null);
    }

    /**
     * @return the pizzaria
     */
    public static Pizzaria getPizzaria() {
        return pizzaria;
    }
    
}
