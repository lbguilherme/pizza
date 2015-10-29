package pizzasystem.data;

public class PizzaTaste {
    
    public enum Size {
        Family,
        Big,
        Medium,
    }
    
    public enum Taste {
        Calabresa(22.50f, 25.50f, 29.50f),
        QuatroQueijos(23.40f, 26.50f, 30.00f),
        Frango(27.90f, 30.00f, 32.50f);         
        
        private final float priceMedium;
        private final float priceBig;
        private final float priceFamily;
        Taste(float priceMedium, float priceBig, float priceFamily){
            this.priceMedium = priceMedium;
            this.priceBig = priceBig;
            this.priceFamily = priceFamily;
        }
        
    }


    private Taste taste;
    private Size size;

    public Taste getTasteName() {
        return taste;
    }

    public void setTasteName(Taste tasteName) {
        this.taste = tasteName;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public float getPrice() {
        float price;
        switch (this.size){
            case Medium:
                price = this.taste.priceMedium;
            case Big:
                price = this.taste.priceBig;
            case Family:
                price = this.taste.priceFamily;
            default:
                price = 0.0f;
        }
        return price;
    }
}
