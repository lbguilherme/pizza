package pizzasystem.data;

import java.util.ArrayList;

public class ClientRequest {
    
    public enum Status {
        Requested,
        Cooking,
        ReadyForDelivery,
        OnTheWay,
        Delivered
    }
    
    private ArrayList<PizzaTaste> pizzas = new ArrayList<>();
    private ArrayList<DrinkType> drinks = new ArrayList<>();
    private Status status;
    private Client client;

    public Float getTotalPrice() {
        Float pizzaPrice = 0f;
        Float drinkPrice = 0f;
        if (pizzas != null){
            for (PizzaTaste pizza : pizzas){
                pizzaPrice += pizza.getPrice();
            }
        }
        if (drinks != null){
            for (DrinkType drink :drinks){
                drinkPrice += drink.getPrice();
            }
        }
        return pizzaPrice + drinkPrice;
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

    public ArrayList<DrinkType> getDrink() {
        return drinks;
    }

    public void setDrink(ArrayList<DrinkType> drinks) {
        this.drinks = drinks;
    }
    
    public boolean hasDrink() {
        return drinks != null;
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
