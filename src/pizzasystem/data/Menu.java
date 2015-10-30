package pizzasystem.data;

import java.util.ArrayList;

public class Menu {
    
    private ArrayList<PizzaTaste> pizzas = new ArrayList<>();
    private ArrayList<DrinkType> drinks = new ArrayList<>();

    public ArrayList<PizzaTaste> getPizzas() {
        return pizzas;
    }

    public void setPizzas(ArrayList<PizzaTaste> pizzas) {
        this.pizzas = pizzas;
    }

    public ArrayList<DrinkType> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<DrinkType> drinks) {
        this.drinks = drinks;
    }
    

    
}
