package pizzasystem.data;

/**
 * Representa uma pizza que esteja em um pedido.
 */
public class Pizza {
    private String taste1;
    private String taste2;
    private String taste3;
    private PizzaTaste.Size size;

    /**
     * Obtém o primeiro sabor da pizza.
     * @return retorna o primeiro sabor da pizza
     */
    public String getTaste1() {
        return taste1;
    }

    /**
     * Atribui o primeiro sabor da pizza
     * @param taste1 O sabor
     */
    public void setTaste1(String taste1) {
        this.taste1 = taste1;
    }

    /**
     * Obtém o segundo sabor da pizza.
     * @return retorna o segundo sabor da pizza
     */
    public String getTaste2() {
        return taste2;
    }

    /**
     * Atribui o segundo sabor da pizza
     * @param taste2 O sabor
     */
    public void setTaste2(String taste2) {
        this.taste2 = taste2;
    }

    /**
     * Obtém o terceiro sabor da pizza.
     * @return retorna o terceiro sabor da pizza
     */
    public String getTaste3() {
        return taste3;
    }

    /**
     * Atribui o terceiro sabor da pizza
     * @param taste3 O sabor
     */
    public void setTaste3(String taste3) {
        this.taste3 = taste3;
    }

    /**
     * Obtém o tamanho da pizza.
     * @return retorna o tamanho da pizza.
     */
    public PizzaTaste.Size getSize() {
        return size;
    }

    /**
     * Atribui o tamanho da pizza.
     * @param size
     */
    public void setSize(PizzaTaste.Size size) {
        this.size = size;
    }
}
