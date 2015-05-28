package pizzasystem.data;

public class PizzaTaste {
    
    public enum Size {
        Family,
        Big,
        Medium,
    }
    
    private String tasteName;
    private Size size;
    private Integer price;

    public String getTasteName() {
        return tasteName;
    }

    public void setTasteName(String tasteName) {
        this.tasteName = tasteName;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
