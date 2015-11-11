package pizzasystem.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabe
 */
public class ClientRequest {

    /**
     *
     */
    public enum Status {

        /**
         *
         */
        Requested,

        /**
         *
         */
        ReadyForDelivery,

        /**
         *
         */
        Delivered
    }

    private int id = -1;
    private List<Pizza> pizzas = new ArrayList<>();
    private List<OtherProduct> others = new ArrayList<>();
    private Status status;
    private Person client;

    /**
     * @return the pizzas
     */
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * @return the products
     */
    public List<OtherProduct> getOthers() {
        return others;
    }
    
    /**
     *
     * @param pizza
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }
    
    /**
     *
     * @param other
     */
    public void addOther(OtherProduct other) {
        others.add(other);
    }

    /**
     *
     * @return Retorna o status do pedido
     */
    public Status getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     *
     * @return Retorna o cliente que pediu este pedido
     */
    public Person getClient() {
        return client;
    }

    /**
     *
     * @param client
     */
    public void setClient(Person client) {
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void setOthers(List<OtherProduct> others) {
        this.others = others;
    }
    
}
