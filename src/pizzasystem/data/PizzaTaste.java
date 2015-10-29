package pizzasystem.data;

public class PizzaTaste {

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    
    public enum Size {
        Family,
        Big,
        Medium,
    }
    
    private Float price;
    private String taste;
    private Size size;

    public String getTasteName() {
        return taste;
    }

    public void setTasteName(String tasteName) {
        this.taste = tasteName;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

}
