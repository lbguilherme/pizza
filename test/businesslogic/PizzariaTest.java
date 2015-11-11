/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.Employee;
import pizzasystem.data.OtherProduct;
import pizzasystem.data.OtherProductType;
import pizzasystem.data.Person;
import pizzasystem.data.Pizza;
import pizzasystem.data.PizzaTaste;
import pizzasystem.utility.PasswordHasher;

/**
 *
 * @author Felipe
 */
public class PizzariaTest {

    static class PizzariaWithTestDb extends Pizzaria {
        @Override
        protected Connection getDb() throws SQLException {
            if (db == null) {
                String url = "jdbc:mysql://45.55.166.39:3306/pizzaTEST";
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

        public void truncateAllTables() throws SQLException {
            Connection db = getDb();
            db.createStatement().execute("DELETE FROM Pizza;");
            db.createStatement().execute("DELETE FROM OtherProduct;");
            db.createStatement().execute("DELETE FROM ClientRequest;");
            db.createStatement().execute("DELETE FROM Employee;");
            db.createStatement().execute("DELETE FROM Person;");
            db.createStatement().execute("DELETE FROM PizzaTaste;");
            db.createStatement().execute("DELETE FROM OtherProductType;");
        }
    }

    public PizzariaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    Pizzaria pizzaria;

    @Before
    public void setUp() {
        pizzaria = new PizzariaWithTestDb();
    }

    @After
    public void tearDown() {
        try {
            ((PizzariaWithTestDb)pizzaria).truncateAllTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of doLogin method, of class Pizzaria.
     */
    @Test
    public void testRegisterEmpAndDoLogin() throws Exception {
        String user = "oidfoighfh";
        String pass = "rtpijhiogp";

        assertEquals(0, pizzaria.doLogin(user, pass));
        assertEquals(null, pizzaria.getCurrentUser());

        Employee emp = new Employee();
        emp.setAddress("aaa");
        emp.setCep("147");
        emp.setCpf("456");
        emp.setHashPass(new PasswordHasher().hash(pass));
        emp.setName("Aaa");
        emp.setPhoneNumber("123");
        emp.setRole(Employee.Role.Attendant);
        emp.setUser(user);
        pizzaria.registerEmployee(emp);

        assertEquals(2, pizzaria.doLogin(user, pass));
        assertEquals("aaa", pizzaria.getCurrentUser().getAddress());
    }

    /**
     * Test of addClient method, of class Pizzaria.
     */
    @Test
    public void testAddAndFindClient() throws Exception {
        Person client = new Person();
        client.setAddress("aaa bbb ccc");
        client.setCep("741");
        client.setName("Aaa Bbb");
        client.setPhoneNumber("123456");

        try {
            pizzaria.findClient("123456");
            fail("Should throw");
        } catch(RuntimeException e) {
            assertEquals("Cliente não encontrado.", e.getMessage());
        }
        
        pizzaria.addClient(client);
        assertEquals("Aaa Bbb", pizzaria.findClient("123456").getName());
    }

    /**
     * Test of addClientRequest method, of class Pizzaria.
     */
    @Test
    public void testAddRequest() throws Exception {
        Person client = new Person();
        client.setAddress("aaa bbb ccc");
        client.setCep("741");
        client.setName("Aaa Bbb");
        client.setPhoneNumber("123456");
        pizzaria.addClient(client);
        
        ClientRequest request = new ClientRequest();
        request.setClient(client);
        request.setStatus(ClientRequest.Status.ReadyForDelivery);
        
        assertEquals(0, pizzaria.getRequests().size());
        pizzaria.addRequest(request);
        
        List<ClientRequest> requests = pizzaria.getRequests();
        assertEquals(1, requests.size());
        assertEquals(ClientRequest.Status.ReadyForDelivery, requests.get(0).getStatus());
        assertEquals("Aaa Bbb", requests.get(0).getClient().getName());
    }

    /**
     * Test of registerPizza method, of class Pizzaria.
     */
    @Test
    public void testRegisterAndGetPizza() throws Exception {
        PizzaTaste pizza = new PizzaTaste();
        pizza.setName("Broculis");
        pizza.setPriceMedium(1f);
        pizza.setPriceBig(2f);
        pizza.setPriceFamily(3f);
        
        assertEquals(0, pizzaria.getPizzaTastes().size());
        pizzaria.registerPizza(pizza);
        
        List<PizzaTaste> pizzas = pizzaria.getPizzaTastes();
        assertEquals(1, pizzas.size());
        assertEquals("Broculis", pizzas.get(0).getName());
        assertEquals(2f, pizzas.get(0).getPrice(PizzaTaste.Size.Big), 0.0001f);
    }

    /**
     * Test of registerOutro method, of class Pizzaria.
     */
    @Test
    public void testRegisterAndGetOutro() throws Exception {
        OtherProductType product = new OtherProductType();
        product.setName("Agua");
        product.setPrice(5f);
        
        assertEquals(0, pizzaria.getOtherProductTypes().size());
        pizzaria.registerOutro(product);
        
        List<OtherProductType> products = pizzaria.getOtherProductTypes();
        assertEquals(1, products.size());
        assertEquals("Agua", products.get(0).getName());
        assertEquals(5f, products.get(0).getPrice(), 0.0001f);
    }

    /**
     * Test of findOtherProduct method, of class Pizzaria.
     */
    @Test
    public void testFindOtherProduct() throws Exception {
        {
            OtherProductType product = new OtherProductType();
            product.setName("Agua");
            product.setPrice(5f);
            pizzaria.registerOutro(product);
        }
        {
            OtherProductType product = new OtherProductType();
            product.setName("Copo");
            product.setPrice(8f);
            pizzaria.registerOutro(product);
        }
        
        assertEquals(2, pizzaria.getOtherProductTypes().size());
        assertEquals(8f, pizzaria.findOtherProduct("Copo").getPrice(), 0.001f);
        assertEquals(5f, pizzaria.findOtherProduct("Agua").getPrice(), 0.001f);
    }

    /**
     * Test of calculateRequestPrice method, of class Pizzaria.
     */
    @Test
    public void testCalculatePrice() throws Exception {
        {
            PizzaTaste pizza = new PizzaTaste();
            pizza.setName("Broculis");
            pizza.setPriceMedium(1f);
            pizza.setPriceBig(2f);
            pizza.setPriceFamily(3f);
            pizzaria.registerPizza(pizza);
        }
        {
            PizzaTaste pizza = new PizzaTaste();
            pizza.setName("Queijo");
            pizza.setPriceMedium(10f);
            pizza.setPriceBig(20f);
            pizza.setPriceFamily(30f);
            pizzaria.registerPizza(pizza);
        }
        {
            OtherProductType product = new OtherProductType();
            product.setName("Agua");
            product.setPrice(5f);
            pizzaria.registerOutro(product);
        }
        {
            OtherProductType product = new OtherProductType();
            product.setName("Copo");
            product.setPrice(8f);
            pizzaria.registerOutro(product);
        }
        
        Person client = new Person();
        client.setAddress("aaa bbb ccc");
        client.setCep("741");
        client.setName("Aaa Bbb");
        client.setPhoneNumber("123456");
        pizzaria.addClient(client);
        
        Pizza pizza1 = new Pizza();
        pizza1.setTaste1("Broculis");
        pizza1.setTaste2("Queijo");
        pizza1.setTaste3("Queijo");
        pizza1.setSize(PizzaTaste.Size.Big);
        
        Pizza pizza2 = new Pizza();
        pizza2.setTaste1("Broculis");
        pizza2.setTaste2("Broculis");
        pizza2.setTaste3("Broculis");
        pizza2.setSize(PizzaTaste.Size.Medium);
        
        OtherProduct product1 = new OtherProduct();
        product1.setProduct("Agua");
        
        OtherProduct product2 = new OtherProduct();
        product2.setProduct("Copo");
        
        OtherProduct product3 = new OtherProduct();
        product3.setProduct("Agua");
        
        ClientRequest request = new ClientRequest();
        request.setClient(client);
        request.setStatus(ClientRequest.Status.ReadyForDelivery);
        request.addPizza(pizza1);
        request.addPizza(pizza2);
        request.addOther(product1);
        request.addOther(product2);
        request.addOther(product3);
        pizzaria.addRequest(request);
        
        assertEquals(20f, pizzaria.calculatePizzaPrice(pizza1), 0.001f);
        assertEquals(1f, pizzaria.calculatePizzaPrice(pizza2), 0.001f);
        assertEquals(39f, pizzaria.calculateRequestPrice(request), 0.001f);
    }

    /**
     * Test of finishOrder method, of class Pizzaria.
     */
    //@Test
    public void testFinishOrder() throws Exception {
        System.out.println("finishOrder");
        Pizzaria instance = new Pizzaria();
        instance.finishOrder();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finishPizza method, of class Pizzaria.
     */
    //@Test
    public void testFinishPizza() throws Exception {
        System.out.println("finishPizza");
        Pizzaria instance = new Pizzaria();
        instance.finishPizza();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeDelivered method, of class Pizzaria.
     */
    //@Test
    public void testRemoveDelivered() throws Exception {
        System.out.println("removeDelivered");
        Pizzaria instance = new Pizzaria();
        instance.removeDelivered();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
