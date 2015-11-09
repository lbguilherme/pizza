package pizzasystem.data;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientRequest {

    /**
     * @return the pizzas
     */
    public ArrayList<PizzaTaste> getPizzas() {
        return pizzas;
    }

    /**
     * @param pizzas the pizzas to set
     */
    public void setPizzas(ArrayList<PizzaTaste> pizzas) {
        this.pizzas = pizzas;
    }
    
    public enum Status {
        Requested,
        ReadyForDelivery,
        Delivered
    }
    
    private ArrayList<PizzaTaste> pizzas = new ArrayList<>();
    private ArrayList<OtherProduct> outros = new ArrayList<>();
    private Status status;
    private Client client;

    public Float getTotalPrice() {
        Float pizzaPrice = 0f;
        Float outroPrice = 0f;
        if (getPizzas() != null){
            for (PizzaTaste pizza : getPizzas()){
                pizzaPrice += pizza.getPrice();
            }
        }
        if (outros != null){
            for (OtherProduct outro :outros){
                outroPrice += outro.getPrice();
            }
        }
        return pizzaPrice + outroPrice;
    }
    
    
    public PizzaTaste.Size getPizzaSize() {
        if (getPizzas() == null) return null;
        return getPizzas().get(0).getSize();
    }
    
    public void addPizza(PizzaTaste pizza) {
        if (getPizzas() == null) {
            setPizzas(new ArrayList<>());
        } else {
            if (!pizza.getSize().equals(pizzas.get(0).getSize()))
                throw new RuntimeException();
        }
        getPizzas().add(pizza);
    }
    
    public boolean hasPizza() {
        return getPizzas() != null;
    }

    public ArrayList<OtherProduct> getOutros() {
        return outros;
    }

    public void setOutros(ArrayList<OtherProduct> outros) {
        this.outros = outros;
    }
    
    public boolean hasDrinks() {
        return outros != null;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
}
