package pizzasystem.data;

public class PizzaTaste {
    
    public enum Size {
        Family,
        Big,
        Medium,
    }
    
    public enum Taste {
        Calabresa,
        QuatroQueijos,
        Frango         
    }


    private Taste tasteName;
    private Size size;
    private Integer price;

    public Taste getTasteName() {
        return tasteName;
    }

    public void setTasteName(Taste tasteName) {
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
