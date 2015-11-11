/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.Employee;
import pizzasystem.data.OtherProductType;
import pizzasystem.data.Person;
import pizzasystem.data.Pizza;
import pizzasystem.data.PizzaTaste;

/**
 *
 * @author Felipe
 */
public class PizzariaTest {
    
    public PizzariaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of doLogin method, of class Pizzaria.
     */
    @Test
    public void testDoLogin() throws Exception {
        System.out.println("doLogin");
        String user = "";
        String pass = "";
        Pizzaria instance = new Pizzaria();
        int expResult = 0;
        int result = instance.doLogin(user, pass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClient method, of class Pizzaria.
     */
    @Test
    public void testAddClient() throws Exception {
        System.out.println("addClient");
        Person client = null;
        Pizzaria instance = new Pizzaria();
        instance.addClient(client);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findClient method, of class Pizzaria.
     */
    @Test
    public void testFindClient() throws Exception {
        System.out.println("findClient");
        String phoneNumber = "";
        Pizzaria instance = new Pizzaria();
        Person expResult = null;
        Person result = instance.findClient(phoneNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClientRequest method, of class Pizzaria.
     */
    @Test
    public void testAddClientRequest() throws Exception {
        System.out.println("addClientRequest");
        ClientRequest request = null;
        Pizzaria instance = new Pizzaria();
        instance.addClientRequest(request);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerPizza method, of class Pizzaria.
     */
    @Test
    public void testRegisterPizza() throws Exception {
        System.out.println("registerPizza");
        PizzaTaste pizza = null;
        Pizzaria instance = new Pizzaria();
        instance.registerPizza(pizza);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerOutro method, of class Pizzaria.
     */
    @Test
    public void testRegisterOutro() throws Exception {
        System.out.println("registerOutro");
        OtherProductType outro = null;
        Pizzaria instance = new Pizzaria();
        instance.registerOutro(outro);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerEmployee method, of class Pizzaria.
     */
    @Test
    public void testRegisterEmployee() throws Exception {
        System.out.println("registerEmployee");
        Employee employee = null;
        Pizzaria instance = new Pizzaria();
        instance.registerEmployee(employee);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPizzaTastes method, of class Pizzaria.
     */
    @Test
    public void testGetPizzaTastes() throws Exception {
        System.out.println("getPizzaTastes");
        Pizzaria instance = new Pizzaria();
        List<PizzaTaste> expResult = null;
        List<PizzaTaste> result = instance.getPizzaTastes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOtherProductTypes method, of class Pizzaria.
     */
    @Test
    public void testGetOtherProductTypes() throws Exception {
        System.out.println("getOtherProductTypes");
        Pizzaria instance = new Pizzaria();
        List<OtherProductType> expResult = null;
        List<OtherProductType> result = instance.getOtherProductTypes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentUser method, of class Pizzaria.
     */
    @Test
    public void testGetCurrentUser() {
        System.out.println("getCurrentUser");
        Pizzaria instance = new Pizzaria();
        Employee expResult = null;
        Employee result = instance.getCurrentUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentUser method, of class Pizzaria.
     */
    @Test
    public void testSetCurrentUser() {
        System.out.println("setCurrentUser");
        Employee aCurrentUser = null;
        Pizzaria instance = new Pizzaria();
        instance.setCurrentUser(aCurrentUser);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findOtherProduct method, of class Pizzaria.
     */
    @Test
    public void testFindOtherProduct() throws Exception {
        System.out.println("findOtherProduct");
        String otherName = "";
        Pizzaria instance = new Pizzaria();
        OtherProductType expResult = null;
        OtherProductType result = instance.findOtherProduct(otherName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRequests method, of class Pizzaria.
     */
    @Test
    public void testGetRequests() throws Exception {
        System.out.println("getRequests");
        Pizzaria instance = new Pizzaria();
        List<ClientRequest> expResult = null;
        List<ClientRequest> result = instance.getRequests();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addRequest method, of class Pizzaria.
     */
    @Test
    public void testAddRequest() throws Exception {
        System.out.println("addRequest");
        ClientRequest request = null;
        Pizzaria instance = new Pizzaria();
        instance.addRequest(request);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateRequestPrice method, of class Pizzaria.
     */
    @Test
    public void testCalculateRequestPrice() throws Exception {
        System.out.println("calculateRequestPrice");
        ClientRequest request = null;
        Pizzaria instance = new Pizzaria();
        Float expResult = null;
        Float result = instance.calculateRequestPrice(request);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculatePizzaPrice method, of class Pizzaria.
     */
    @Test
    public void testCalculatePizzaPrice() throws Exception {
        System.out.println("calculatePizzaPrice");
        Pizza pizza = null;
        Pizzaria instance = new Pizzaria();
        Float expResult = null;
        Float result = instance.calculatePizzaPrice(pizza);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPizzaPrice method, of class Pizzaria.
     */
    @Test
    public void testGetPizzaPrice() throws Exception {
        System.out.println("getPizzaPrice");
        List<PizzaTaste> tastes = null;
        String pizzaName = "";
        PizzaTaste.Size size = null;
        Pizzaria instance = new Pizzaria();
        Float expResult = null;
        Float result = instance.getPizzaPrice(tastes, pizzaName, size);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOtherPrice method, of class Pizzaria.
     */
    @Test
    public void testGetOtherPrice() throws Exception {
        System.out.println("getOtherPrice");
        List<OtherProductType> others = null;
        String product = "";
        Pizzaria instance = new Pizzaria();
        Float expResult = null;
        Float result = instance.getOtherPrice(others, product);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finishOrder method, of class Pizzaria.
     */
    @Test
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
    @Test
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
    @Test
    public void testRemoveDelivered() throws Exception {
        System.out.println("removeDelivered");
        Pizzaria instance = new Pizzaria();
        instance.removeDelivered();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
