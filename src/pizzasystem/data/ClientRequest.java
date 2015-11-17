package pizzasystem.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um pedido feito pelo cliente.
 */
public class ClientRequest {
    /**
     * O estado atual do pedido.
     */
    public enum Status {

        /**
         * Pedido foi feito e está na fila para a cozinha.
         */
        Requested,

        /**
         * Pedido está pronto e está na fila para ser enviado.
         */
        ReadyForDelivery,

        /**
         * Pedido foi entregue ao cliente e pode ser excluído.
         */
        Delivered
    }

    private int id = -1;
    private List<Pizza> pizzas = new ArrayList<>();
    private List<OtherProduct> others = new ArrayList<>();
    private Status status;
    private Person client;

    /**
     * Obtém a lista de pizzas no pedido.
     * @return A lista de pizzas.
     */
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Obtém a lista de produtos no pedido.
     * @return A lista de produtos.
     */
    public List<OtherProduct> getOthers() {
        return others;
    }
    
    /**
     * Adiciona uma nova pizza no pedido.
     * @param pizza A pizza a ser adicionada.
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }
    
    /**
     * Adiciona um novo produto no pedido
     * @param other O produto a ser adicinado.
     */
    public void addOther(OtherProduct other) {
        others.add(other);
    }

    /**
     * Obtém o estado do pedido.
     * @return Retorna o status do pedido.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Atribui um estado ao pedido
     * @param status Estado a ser atribuído
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Obtém o cliente que fez o pedido.
     * @return Retorna o cliente que pediu este pedido
     */
    public Person getClient() {
        return client;
    }

    /**
     * Atribui o cliente deste pedido
     * @param client Cliente para ser atribuido
     */
    public void setClient(Person client) {
        this.client = client;
    }

    /**
     * Obtém o identificador único deste pedido.
     * @return O identificador do pedido.
     */
    public int getId() {
        return id;
    }

    /**
     * Atribui o identificador único deste pedido.
     * @param id O identificador para ser atribuido.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui a lista de pizzas ao pedido, descartando a anterior.
     * @param pizzas Lista a ser atribuída
     */
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    /**
     * Atribui a lista de produtos ao pedido, descartando a anterior.
     * @param others Lista a ser atribuída
     */
    public void setOthers(List<OtherProduct> others) {
        this.others = others;
    }
}
