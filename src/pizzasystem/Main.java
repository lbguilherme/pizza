package pizzasystem;

import businesslogic.Pizzaria;
import pizzasystem.transfer.Database;

public class Main {

    private static final Database db = new Database("45.55.166.39", "3306", "pizza", "root", "pizza");
    private static final Pizzaria pizzaria = new Pizzaria(db);

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
