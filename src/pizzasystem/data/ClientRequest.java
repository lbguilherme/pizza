package pizzasystem.data;

import java.util.ArrayList;
import java.util.List;
import newpackage.businesslogic.Pizzaria;

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
                switch(pizza.getSize()){
                    case Medium:
                        maxPizzaPrice = Math.max(maxPizzaPrice, (Pizzaria.getMenu().getPizzas().get(pizza.getTasteName()))[0]);
                    case Big:
                        maxPizzaPrice = Math.max(maxPizzaPrice, (Pizzaria.getMenu().getPizzas().get(pizza.getTasteName()))[1]);
                    case Family:
                        maxPizzaPrice = Math.max(maxPizzaPrice, (Pizzaria.getMenu().getPizzas().get(pizza.getTasteName()))[2]);
                }
            }
        return (drink != null ? Pizzaria.getMenu().getDrinks().get(drink.getName()) : 0) + maxPizzaPrice;
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
