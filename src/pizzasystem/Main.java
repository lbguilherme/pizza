package pizzasystem;

import businesslogic.Pizzaria;

public class Main {
    
    private static final Pizzaria pizzaria = new Pizzaria();
    
    public static void main(String[] args) {
        pizzasystem.ui.MainFrame.main(null);
    }

    /**
     * @return the pizzaria
     */
    public static Pizzaria getPizzaria() {
        return pizzaria;
    }
    
}
