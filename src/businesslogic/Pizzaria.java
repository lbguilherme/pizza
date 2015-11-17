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

/**
 * Classe responsável por representar uma instancia do sistema da pizzaria. Ela
 * contém informações de estado atual como o usuário logado e métodos para login
 * e logout. Também contém todos os métodos que representam as regras de negócio,
 * aplicando as alterações no banco de dados providenciado pelo construtor.
 */
public class Pizzaria{
    private final IDatabase db;
    private Employee currentUser;

    /**
     * Cria uma nova instancia do sistema de pizzaria, integrando com o banco
     * de dados providênciado.
     * @param db Veja a documentação de IDatabase para detalhes dos requsitos
     */
    public Pizzaria(IDatabase db) {
        this.db = db;
    }

    /**
     * Faz o login com o usuário e senha e retorna o Employee.Role desse usuário.
     * Em caso de falha, lança uma RuntimeException. Esse login é persistido até
     * que logOut seja chamado.
     * @param user O nome de usuário para login
     * @param pass A senha em plaintext
     * @return O Employee.Role do usuário, se login foi feito com sucesso
     * @throws SQLException
     * @throws RuntimeException
     */
    public Employee.Role doLogin(String user, String pass) throws SQLException {
        if (getCurrentUser() != null) {
            throw new RuntimeException("Já logado");
        }
        PasswordHasher hasher = new PasswordHasher();
        String passHash = hasher.hash(pass);

        Employee emp = EmployeeDAO.fetch(db.getConnection(), user);
        if (emp != null && emp.getHashPass().equals(passHash)) {
            currentUser = emp;
            return getCurrentUser().getRole();
        } else {
            throw new RuntimeException("Usuário/Senha inválidos");
        }
    }

    /**
     * Faz logout do usuário atual.
     * @see doLogin
     */
    public void logOut() {
        currentUser = null;
    }

    /**
     * Adiciona um novo cliente na tabela de clientes. Se já existir um com o mesmo
     * número de telefone, ele será atualizado.
     * @param client O cliente para adicionar/atualizar
     * @throws SQLException
     */
    public void addClient(Person client) throws SQLException {
        if (client.getAddress().equals("") ||
                client.getCep().equals("") ||
                client.getPhoneNumber().equals("") ||
                client.getName().equals("")) {
            throw new RuntimeException("Não foi possivel registrar ou atualizar cliente, informações incompletas!");
        } else {
            new PersonDAO(client).save(db.getConnection());
        }
    }

    /**
     * Encontra um cliente pelo número de telefone. Se não existir, lança
     * RuntimeException.
     * @param phoneNumber
     * @return Retorna o cliente que tem o numero de telefone passado como argumento
     * @throws SQLException
     * @throws RuntimeException
     */
    public Person findClient(String phoneNumber) throws SQLException {
        Person client = PersonDAO.fetch(db.getConnection(), phoneNumber);
        if (client != null) {
            return client;
        } else {
            throw new RuntimeException("Cliente não encontrado.");
        }
    }

    /**
     * Registra um novo sabor de pizza no menu, junto com os preços para cada
     * tamanho. Se ja existir um com o mesmo nome, será atualizado.
     * @param pizza PizzaTaste a ser adicionado/atualizado.
     * @throws SQLException
     */
    public void registerPizza(PizzaTaste pizza) throws SQLException {
        new PizzaTasteDAO(pizza).save(db.getConnection());
    }

    /**
     * Registra um novo tipo de produto no menu, junto com seu preço.
     * Se ja existir um com o mesmo nome, será atualizado.
     * @param outro OtherProductType a ser adicionado/atualizado.
     */
    public void registerOutro(OtherProductType outro) throws SQLException {
        new OtherProductTypeDAO(outro).save(db.getConnection());
    }

    /**
     * Registra um novo empregado que terá permissão de fazer login.
     * Empregados também são pessoas e tem telefone, logo serã também
     * adicionados na lista de clientes e podem ser encontrados pelo
     * número de telefone. Caso já exista, será atualizado.
     * @param employee A ser adicionado/atualizado.
     * @throws SQLException
     */
    public void registerEmployee(Employee employee) throws SQLException {
        if (employee.getAddress() == null ||
                employee.getCep().equals("") ||
                employee.getCpf().equals("") ||
                employee.getPhoneNumber().equals("") ||
                employee.getName().equals("") ||
                employee.getHashPass().equals("") ||
                employee.getRole() == null ||
                employee.getUser().equals("")) {
            throw new RuntimeException("Não foi possivel cadastrar funcionario, há informações faltando");
        }
        else{
            new EmployeeDAO(employee).save(db.getConnection());
        }
    }

    /**
     * Obtém a lista de sabores de pízza disponíveis no banco de dados.
     * @return Retorna a lista de sabores
     * @throws SQLException
     */
    public List<PizzaTaste> getPizzaTastes() throws SQLException {
        return PizzaTasteDAO.fetchAll(db.getConnection());
    }

    /**
     * Obtém a lista de produtos disponíveis no banco de dados
     * @return Retorna a lista de produtos
     * @throws SQLException
     */
    public List<OtherProductType> getOtherProductTypes() throws SQLException {
        return OtherProductTypeDAO.fetchAll(db.getConnection());
    }

    /**
     * Retorna o usuário que fez login, ou `null` se não houver.
     * @return O usuário atual
     */
    public Employee getCurrentUser() {
        return currentUser;
    }

    /**
     * Encontra um produto no menu pelo nome. Útil para consultar preço.
     * @param otherName O nome do produto a ser encontrado
     * @return Retorna o produto ou `null` se não existir
     * @throws SQLException
     */
    public OtherProductType findOtherProduct(String otherName) throws SQLException {
        for (OtherProductType otherInMenu : this.getOtherProductTypes()) {
            if (otherInMenu.getName().equals(otherName)) {
                return otherInMenu;
            }
        }
        return null;
    }

    /**
     * Retorna a lista de todos os pedidos efetuados pelos clientes. Ela estará
     * ordenada por data do pedido. O mais antigo primeiro.
     * @return A lista de pedidos
     * @throws java.sql.SQLException
     */
    public List<ClientRequest> getRequests() throws SQLException {
        return ClientRequestDAO.fetchAll(db.getConnection());
    }

    /**
     * Adiciona um novo pedido na lista de pedidos. Se já existir um pedido com
     * o mesmo id, será atualizado.
     * @param request O pedido a ser adicionado/atualizado.
     * @throws SQLException
     */
    public void addRequest(ClientRequest request) throws SQLException {
        if (request.getClient() != null && request.getStatus() != null) {
            new ClientRequestDAO(request).save(db.getConnection());
        } else {
            throw new RuntimeException("Não foi possivel realizar o pedido.");
        }
    }

    /**
     * Calcula e retorna o preço total de um pedido, somando as pizzas e produtos.
     * @param request O pedido para o qual o preço será calculado
     * @return Retorna o valor total do pedido
     * @throws SQLException
     */
    public Float calculateRequestPrice(ClientRequest request) throws SQLException {
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
     * Calcula o preço total de uma única pizza, com base nos sabores e no tamanhp.
     * @param pizza Pizza para calcular valor.
     * @return Retorna o valor da pizza
     * @throws SQLException
     */
    public Float calculatePizzaPrice(Pizza pizza) throws SQLException {
        List<PizzaTaste> tastes = getPizzaTastes();
        Float maxPrice = 0f;
        maxPrice = Float.max(maxPrice, getPizzaPrice(tastes, pizza.getTaste1(), pizza.getSize()));
        maxPrice = Float.max(maxPrice, getPizzaPrice(tastes, pizza.getTaste2(), pizza.getSize()));
        maxPrice = Float.max(maxPrice, getPizzaPrice(tastes, pizza.getTaste3(), pizza.getSize()));
        return maxPrice;
    }
    
    private Float getPizzaPrice(List<PizzaTaste> tastes, String pizzaName, PizzaTaste.Size size) throws SQLException {
        for (PizzaTaste pizzaInMenu : tastes) {
            if ((pizzaInMenu.getName().equals(pizzaName))) {
                return pizzaInMenu.getPrice(size);
            }
        }
        return 0f;
    }

    private Float getOtherPrice(List<OtherProductType> others, String product) throws SQLException {
        for (OtherProductType otherInMenu : others) {
            if ((otherInMenu.getName().equals(product))) {
                return otherInMenu.getPrice();
            }
        }
        return 0f;
    }

    /**
     * Fecha o pedido mais antigo, marcando-o como entregue.
     * @throws SQLException
     */
    public void finishOrder() throws SQLException {
        for (ClientRequest request : getRequests()) {
            if (request.getStatus() == ClientRequest.Status.ReadyForDelivery) {
                request.setStatus(ClientRequest.Status.Delivered);
                new ClientRequestDAO(request).save(db.getConnection());
                return;
            }
        }
        throw new RuntimeException("Não foi possivel finalizar o pedido, não há pedidos para finalizar.");
    }

    /**
     * Declara que o pedido mais antigo está pronto para ser enviado.
     * @throws SQLException
     */
    public void finishPizza() throws SQLException {
        for (ClientRequest request : getRequests()) {
            if (request.getStatus() == ClientRequest.Status.Requested) {
                request.setStatus(ClientRequest.Status.ReadyForDelivery);
                new ClientRequestDAO(request).save(db.getConnection());
                return;
            }
        }
        throw new RuntimeException("Não foi possivel finalizar pizza, não há pizzas para finalizar.");
    }

    /**
     * Remove todos os pedidos concluídos e já entregues da lista
     * @throws SQLException 
     */
    public void removeDelivered() throws SQLException {
        for (ClientRequest request : getRequests()) {
            if (request.getStatus() == ClientRequest.Status.Delivered) {
                new ClientRequestDAO(request).deleteRequests(db.getConnection());
            }
        }
    }
}
