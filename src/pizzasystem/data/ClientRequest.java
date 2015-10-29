package pizzasystem.data;

import java.util.ArrayList;
import java.util.List;

public class ClientRequest {
    
    public enum Status {
        Requested,
        Cooking,
        ReadyForDelivery,
        OnTheWay,
        Delivered
    }
    
    private List<PizzaTaste> pizzas;
    private DrinkType drink;
    private Status status;
    private Client client;

    public Float getTotalPrice() {
        float maxPizzaPrice = 0f;
        if (pizzas != null)
            for (PizzaTaste pizza : pizzas) {
                maxPizzaPrice = Math.max(maxPizzaPrice, (pizza.getPrice()));
            }
        return (drink != null ? drink.getPrice() : 0) + maxPizzaPrice;
    }
    
    public String[] getPizzaTastes() {
        if (pizzas == null) return new String[0];
        
        String[] tastes = new String[pizzas.size()];
        for (int i = 0; i < pizzas.size(); ++i) {
            tastes[i] = pizzas.get(i).getTasteName();
        }
        return tastes;
    }
    
    public PizzaTaste.Size getPizzaSize() {
        if (pizzas == null) return null;
        return pizzas.get(0).getSize();
    }
    
    public void addPizza(PizzaTaste pizza) {
        if (pizzas == null) {
            pizzas = new ArrayList<>();
        } else {
            if (!pizza.getSize().equals(pizzas.get(0).getSize()))
                throw new RuntimeException();
        }
        pizzas.add(pizza);
    }
    
    public boolean hasPizza() {
        return pizzas != null;
    }

    public DrinkType getDrink() {
        return drink;
    }

    public void setDrink(DrinkType drink) {
        this.drink = drink;
    }
    
    public boolean hasDrink() {
        return drink != null;
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
