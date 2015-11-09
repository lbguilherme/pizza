package pizzasystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import businesslogic.Pizzaria;
import pizzasystem.data.Menu;
import java.lang.NullPointerException;
import pizzasystem.data.Employee;
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
