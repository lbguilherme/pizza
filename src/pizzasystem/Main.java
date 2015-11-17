package pizzasystem;

import businesslogic.Pizzaria;
import pizzasystem.transfer.Database;

/**
 * Classe inicial da aplicação. Cria a conexão com o banco de dados, a pizzaria
 * e inicializa a interface de usuário.
 */
public class Main {

    private static final Database db = new Database("45.55.166.39", "3306", "pizza", "root", "pizza");
    private static final Pizzaria pizzaria = new Pizzaria(db);

    /**
     * Método inicial. Inicializa a interface de usuário.
     * @param args 
     */
    public static void main(String[] args) {
        pizzasystem.ui.MainFrame.main(null);
    }

    /**
     * Obtém a instancia da pizzaria para ser usada na interface. É um singleton.
     * @return A única instancia da pizzaria
     */
    public static Pizzaria getPizzaria() {
        return pizzaria;
    }

}
