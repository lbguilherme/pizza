package pizzasystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import newpackage.businesslogic.Pizzaria;
import pizzasystem.data.Menu;
import java.lang.NullPointerException;
import pizzasystem.data.Employee;
import pizzasystem.utility.PasswordHasher;


public class Main {
    
    public static void saveField(String fieldName, Object fieldValue) throws IOException {
      FileOutputStream fos = new FileOutputStream(new File("MyClass-" + fieldName + ".dat"));
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(fieldValue);
      oos.close();
    }


    public static Object readField(String fieldName) throws IOException, ClassNotFoundException {
      FileInputStream fis = new FileInputStream(new File("MyClass-" + fieldName + ".dat"));
      ObjectInputStream ois = new ObjectInputStream(fis);
      Object value = ois.readObject();
      ois.close();

      return value;
    }
    
    public static void main(String[] args) {
        if (Pizzaria.getEmployees().isEmpty()) {
            Employee admin = new Employee();
            admin.setName("admin");
            admin.setUser("admin");
            admin.setHashPass(new PasswordHasher().hash("admin"));
            admin.setAddress("");
            admin.setCep("");
            admin.setPhoneNumber("");
            admin.setCpf("");
            admin.setRole(Employee.Role.Admin);
            Pizzaria.getEmployees().add(admin);
        }
        pizzasystem.ui.MainFrame.main(null);
    }
    
}
