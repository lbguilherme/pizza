package pizzasystem.data;

import java.util.HashMap;
import java.util.Map;

public class Menu {
    
    private Map<String, Float[]> pizzas = new HashMap<String, Float[]>();
    private Map<String, Float> drinks = new HashMap<>();

    public Map<String, Float[]> getPizzas() {
        return pizzas;
    }

    public void setPizzas(Map<String, Float[]> pizzas) {
        this.pizzas = pizzas;
    }

    public Map<String, Float> getDrinks() {
        return drinks;
    }

    public void setDrinks(Map<String, Float> drinks) {
        this.drinks = drinks;
    }

    
}
