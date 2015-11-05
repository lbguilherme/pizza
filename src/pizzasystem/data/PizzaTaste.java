package pizzasystem.data;
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
    

}
