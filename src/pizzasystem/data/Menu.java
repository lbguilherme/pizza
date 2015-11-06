package pizzasystem.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu{
    
    private transient ArrayList<PizzaTaste> pizzas = new ArrayList<>();
    private transient ArrayList<OtherProduct> outros = new ArrayList<>();

    public ArrayList<PizzaTaste> getPizzas() {
        return pizzas;
    }

    public void setPizzas(ArrayList<PizzaTaste> pizzas) {
        this.pizzas = pizzas;
    }

    public ArrayList<OtherProduct> getOutros() {
        return outros;
    }

    public void setOutros(ArrayList<OtherProduct> outros) {
        this.outros = outros;
    }
    

    
}
