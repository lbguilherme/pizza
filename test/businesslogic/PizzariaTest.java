package businesslogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.Employee;
import pizzasystem.data.OtherProduct;
import pizzasystem.data.OtherProductType;
import pizzasystem.data.Person;
import pizzasystem.data.Pizza;
import pizzasystem.data.PizzaTaste;
import pizzasystem.transfer.Database;
import pizzasystem.utility.PasswordHasher;

public class PizzariaTest {

    private Pizzaria pizzaria;

    @Before
    public void setUp() {
        Database db = new Database("45.55.166.39", "3306", "pizzaTEST", "root", "pizza");
        pizzaria = new Pizzaria(db);
        try {
            Connection conn = db.getConnection();
            conn.createStatement().execute("DELETE FROM Pizza;");
            conn.createStatement().execute("DELETE FROM OtherProduct;");
            conn.createStatement().execute("DELETE FROM ClientRequest;");
            conn.createStatement().execute("DELETE FROM Employee;");
            conn.createStatement().execute("DELETE FROM Person;");
            conn.createStatement().execute("DELETE FROM PizzaTaste;");
            conn.createStatement().execute("DELETE FROM OtherProductType;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of doLogin method, of class Pizzaria.
     * @throws java.lang.Exception
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
     * @throws java.lang.Exception
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
     * @throws java.lang.Exception
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
     * @throws java.lang.Exception
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
     * @throws java.lang.Exception
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
     * @throws java.lang.Exception
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
     * @throws java.lang.Exception
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

        assertEquals(39f, pizzaria.calculateRequestPrice(pizzaria.getRequests().get(0)), 0.001f);
    }

    /**
     * Test of finishOrder method, of class Pizzaria.
     * @throws java.lang.Exception
     */
    @Test
    public void testFinishing() throws Exception {
        Person client = new Person();
        client.setAddress("aaa bbb ccc");
        client.setCep("741");
        client.setName("Aaa Bbb");
        client.setPhoneNumber("123456");
        pizzaria.addClient(client);

        ClientRequest request1 = new ClientRequest();
        request1.setClient(client);
        request1.setStatus(ClientRequest.Status.Requested);
        pizzaria.addRequest(request1);

        ClientRequest request2 = new ClientRequest();
        request2.setClient(client);
        request2.setStatus(ClientRequest.Status.Requested);
        pizzaria.addRequest(request2);

        ClientRequest request3 = new ClientRequest();
        request3.setClient(client);
        request3.setStatus(ClientRequest.Status.Requested);
        pizzaria.addRequest(request3);

        {
            List<ClientRequest> requests = pizzaria.getRequests();
            assertEquals(3, requests.size());
            assertEquals(request1.getId(), requests.get(0).getId());
            assertEquals(request2.getId(), requests.get(1).getId());
            assertEquals(request3.getId(), requests.get(2).getId());
            assertEquals(ClientRequest.Status.Requested, requests.get(0).getStatus());
            assertEquals(ClientRequest.Status.Requested, requests.get(1).getStatus());
            assertEquals(ClientRequest.Status.Requested, requests.get(2).getStatus());
        }

        pizzaria.finishPizza();

        {
            List<ClientRequest> requests = pizzaria.getRequests();
            assertEquals(3, requests.size());
            assertEquals(request1.getId(), requests.get(0).getId());
            assertEquals(request2.getId(), requests.get(1).getId());
            assertEquals(request3.getId(), requests.get(2).getId());
            assertEquals(ClientRequest.Status.ReadyForDelivery, requests.get(0).getStatus());
            assertEquals(ClientRequest.Status.Requested, requests.get(1).getStatus());
            assertEquals(ClientRequest.Status.Requested, requests.get(2).getStatus());
        }

        pizzaria.finishPizza();

        {
            List<ClientRequest> requests = pizzaria.getRequests();
            assertEquals(3, requests.size());
            assertEquals(request1.getId(), requests.get(0).getId());
            assertEquals(request2.getId(), requests.get(1).getId());
            assertEquals(request3.getId(), requests.get(2).getId());
            assertEquals(ClientRequest.Status.ReadyForDelivery, requests.get(0).getStatus());
            assertEquals(ClientRequest.Status.ReadyForDelivery, requests.get(1).getStatus());
            assertEquals(ClientRequest.Status.Requested, requests.get(2).getStatus());
        }

        pizzaria.finishOrder();

        {
            List<ClientRequest> requests = pizzaria.getRequests();
            assertEquals(3, requests.size());
            assertEquals(request1.getId(), requests.get(0).getId());
            assertEquals(request2.getId(), requests.get(1).getId());
            assertEquals(request3.getId(), requests.get(2).getId());
            assertEquals(ClientRequest.Status.Delivered, requests.get(0).getStatus());
            assertEquals(ClientRequest.Status.ReadyForDelivery, requests.get(1).getStatus());
            assertEquals(ClientRequest.Status.Requested, requests.get(2).getStatus());
        }

        pizzaria.finishOrder();

        {
            List<ClientRequest> requests = pizzaria.getRequests();
            assertEquals(3, requests.size());
            assertEquals(request1.getId(), requests.get(0).getId());
            assertEquals(request2.getId(), requests.get(1).getId());
            assertEquals(request3.getId(), requests.get(2).getId());
            assertEquals(ClientRequest.Status.Delivered, requests.get(0).getStatus());
            assertEquals(ClientRequest.Status.Delivered, requests.get(1).getStatus());
            assertEquals(ClientRequest.Status.Requested, requests.get(2).getStatus());
        }

        pizzaria.removeDelivered();

        {
            List<ClientRequest> requests = pizzaria.getRequests();
            assertEquals(1, requests.size());
            assertEquals(request3.getId(), requests.get(0).getId());
            assertEquals(ClientRequest.Status.Requested, requests.get(0).getStatus());
        }
    }
}
