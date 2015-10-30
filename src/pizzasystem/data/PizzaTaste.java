package pizzasystem.data;
import pizzasystem.data.Menu;
import newpackage.businesslogic.Pizzaria;
        
public class PizzaTaste {
    
    public enum Size {
        Family,
        Big,
        Medium,
    }
    
    private Float price;
    private String[] tastes = new String[3];
    private Size size;

    public String[] getTasteName() {
        return tastes;
    }

    public void setTastes(String[] tastes) {
        this.tastes = tastes;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    
    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
    
    public Float calculatePizzaPrice() {
        Float maxPrice = 0f;
        for (String taste : tastes){
            for (PizzaTaste pizza : Pizzaria.getMenu().getPizzas()) {
                if (pizza.getTasteName()[0].equals(taste)){
                    maxPrice = Float.max(maxPrice, pizza.getPrice());
                    break;
                }
            }
        }
        return maxPrice;
    }

}
