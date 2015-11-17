package businesslogic;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import pizzasystem.data.Person;
import pizzasystem.data.ClientRequest;
import pizzasystem.data.OtherProductType;
import pizzasystem.data.Employee;
import pizzasystem.data.OtherProduct;
import pizzasystem.data.Pizza;
import pizzasystem.data.PizzaTaste;
import pizzasystem.transfer.ClientRequestDAO;
import pizzasystem.transfer.EmployeeDAO;
import pizzasystem.transfer.IDatabase;
import pizzasystem.transfer.OtherProductTypeDAO;
import pizzasystem.transfer.PersonDAO;
import pizzasystem.transfer.PizzaTasteDAO;
import pizzasystem.utility.PasswordHasher;

public class Pizzaria{
    private IDatabase db;
    private Employee currentUser;

    public Pizzaria(IDatabase db) {
        this.db = db;
    }

    /**
     *
     * @param user
     * @param pass
     * @return Faz login com o usuario e password fornecidos e retorna um inteiro representando que tipo de funcionario fez login
     * @throws SQLException
     */
    public int doLogin(String user, String pass) throws SQLException {
        if (getCurrentUser() != null) {
            JOptionPane.showMessageDialog(null, "Já existe um usuario logado!");
            return 0;
        }
        PasswordHasher hasher = new PasswordHasher();
        String passHash = hasher.hash(pass);

        Employee emp = EmployeeDAO.fetch(db.getConnection(), user);
        if (emp != null && emp.getHashPass().equals(passHash)) {
            currentUser = emp;
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
        return 0;
    }

    public void logOut() {
        currentUser = null;
    }

    /**
     *
     * @param client
     * @throws SQLException
     */
    public void addClient(Person client) throws SQLException {
        if (client == null){
            throw new RuntimeException("Can't insert null Client");
        }else{
            if (client.getAddress().equals("") ||
                    client.getCep().equals("") ||
            client.getPhoneNumber().equals("") ||
                   client.getName().equals("")) {
                throw new RuntimeException("Não foi possivel registrar ou atualizar cliente, informações incompletas!");
            }else{
                new PersonDAO(client).save(db.getConnection());
            }
        }
    }

    /**
     *
     * @param phoneNumber
     * @return Retorna o cliente que tem o numero de telefone passado como argumento
     * @throws SQLException
     */
    public Person findClient(String phoneNumber) throws SQLException {
        Person client = PersonDAO.fetch(db.getConnection(), phoneNumber);
        if(client != null){
             return client;
        }else{
            throw new RuntimeException("Cliente não encontrado.");
        }

    }

    /**
     *
     * @param pizza
     * @throws SQLException
     */
    public void registerPizza(PizzaTaste pizza) throws SQLException {
        // TODO: Checar todos os atributos
        new PizzaTasteDAO(pizza).save(db.getConnection());
    }

    /**
     *
     * @param outro
     * @throws SQLException
     */
    public void registerOutro(OtherProductType outro) throws SQLException {
        // TODO: Checar todos os atributos
        new OtherProductTypeDAO(outro).save(db.getConnection());
    }

    /**
     *
     * @param employee
     * @throws SQLException
     */
    public void registerEmployee(Employee employee) throws SQLException {
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
                throw new RuntimeException("Não foi possivel cadastrar funcionario, há informações faltando");
            }
            else{
                new EmployeeDAO(employee).save(db.getConnection());
            }
        }
    }

    /**
     *
     * @return retorna a lista de pizzas disponiveis que estão no database
     * @throws SQLException
     */
    public List<PizzaTaste> getPizzaTastes() throws SQLException {
        return PizzaTasteDAO.fetchAll(db.getConnection());
    }

    /**
     *
     * @return retorna a lista de outros produtos disponiveis que estão no database
     * @throws SQLException
     */
    public List<OtherProductType> getOtherProductTypes() throws SQLException {
        return OtherProductTypeDAO.fetchAll(db.getConnection());
    }

    /**
     * @return the currentUser
     */
    public Employee getCurrentUser() {
        return currentUser;
    }

    /**
     *
     * @param otherName
     * @return retorna o produto que tem o nome passado como argumento
     * @throws SQLException
     */
    public OtherProductType findOtherProduct(String otherName) throws SQLException{
        for(OtherProductType otherInMenu : this.getOtherProductTypes()){
            if (otherInMenu.getName().equals(otherName)){
                return otherInMenu;
            }
        }
        return null;
    }

    /**
     * @return the requests
     * @throws java.sql.SQLException
     */
    public List<ClientRequest> getRequests() throws SQLException {
        return ClientRequestDAO.fetchAll(db.getConnection());
    }

    /**
     *
     * @param request
     * @throws SQLException
     */
    public void addRequest(ClientRequest request) throws SQLException {
        if (request.getClient() != null && request.getStatus() != null){
            new ClientRequestDAO(request).save(db.getConnection());
        } else {
            throw new RuntimeException("Não foi possivel realizar o pedido.");
        }
    }

    /**
     *
     * @param request
     * @return Retorna o valor total do pedido baseado em todas as pizzas e nos outros produtos
     * @throws SQLException
     */
    public Float calculateRequestPrice(ClientRequest request) throws SQLException{
        List<PizzaTaste> tastes = getPizzaTastes();
        List<OtherProductType> others = getOtherProductTypes();
        Float totalPrice = 0f;

        for (Pizza pizza : request.getPizzas()) {
            Float maxPrice = 0f;
            maxPrice = Float.max(maxPrice, getPizzaPrice(tastes, pizza.getTaste1(), pizza.getSize()));
            maxPrice = Float.max(maxPrice, getPizzaPrice(tastes, pizza.getTaste2(), pizza.getSize()));
            maxPrice = Float.max(maxPrice, getPizzaPrice(tastes, pizza.getTaste3(), pizza.getSize()));
            totalPrice += maxPrice;
        }

        for (OtherProduct other : request.getOthers()) {
            totalPrice += getOtherPrice(others, other.getProduct());
        }

        return totalPrice;
    }

    /**
     *
     * @param pizza
     * @return Retorna o valor da pizza baseado nos 3 sabores pedidos, o valor da pizza será o maior valor entre as pedidas
     * @throws SQLException
     */
    public Float calculatePizzaPrice(Pizza pizza) throws SQLException{
        List<PizzaTaste> tastes = getPizzaTastes();
        Float maxPrice = 0f;
        maxPrice = Float.max(maxPrice, getPizzaPrice(tastes, pizza.getTaste1(), pizza.getSize()));
        maxPrice = Float.max(maxPrice, getPizzaPrice(tastes, pizza.getTaste2(), pizza.getSize()));
        maxPrice = Float.max(maxPrice, getPizzaPrice(tastes, pizza.getTaste3(), pizza.getSize()));
        return maxPrice;
    }

    /**
     *
     * @param tastes
     * @param pizzaName
     * @param size
     * @return Retorna o valor da pizza com o nome e tamanho passados como argumento
     * @throws SQLException
     */
    private Float getPizzaPrice(List<PizzaTaste> tastes, String pizzaName, PizzaTaste.Size size) throws SQLException{
        for (PizzaTaste pizzaInMenu : tastes) {
            if ((pizzaInMenu.getName().equals(pizzaName))){
                return pizzaInMenu.getPrice(size);
            }
        }
        return 0f;
    }

    /**
     *
     * @param others
     * @param product
     * @return Retorna o valor do produto com o nome como argumento
     * @throws SQLException
     */
    private Float getOtherPrice(List<OtherProductType> others, String product) throws SQLException{
        for (OtherProductType otherInMenu : others) {
            if ((otherInMenu.getName().equals(product))){
                return otherInMenu.getPrice();
            }
        }
        return 0f;
    }

    /**
     *
     * @throws SQLException
     */
    public void finishOrder() throws SQLException {
        for(ClientRequest request : getRequests()){
            if (request.getStatus() == ClientRequest.Status.ReadyForDelivery){
                request.setStatus(ClientRequest.Status.Delivered);
                new ClientRequestDAO(request).save(db.getConnection());
                return;
            }
        }
        throw new RuntimeException("Não foi possivel finalizar o pedido, não há pedidos para finalizar.");
    }

    /**
     *
     * @throws SQLException
     */
    public void finishPizza() throws SQLException {
        for(ClientRequest request : getRequests()){
            if (request.getStatus() == ClientRequest.Status.Requested){
                request.setStatus(ClientRequest.Status.ReadyForDelivery);
                new ClientRequestDAO(request).save(db.getConnection());
                return;
            }
        }
        throw new RuntimeException("Não foi possivel finalizar pizza, não há pizzas para finalizar.");
    }

    public void removeDelivered() throws SQLException{
        for (ClientRequest request : getRequests()){
            if (request.getStatus() == ClientRequest.Status.Delivered){
                new ClientRequestDAO(request).deleteRequests(db.getConnection());
            }
        }
    }
}
