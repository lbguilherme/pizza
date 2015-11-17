package pizzasystem.data;

/**
 * Representa um produto no menu, com nome e preço.
 */
public class OtherProductType{
    private String name;
    private Float price;

    /**
     * Obtém o nome do produto.
     * @return retorna o nome do produto.
     */
    public String getName() {
        return name;
    }

    /**
     * Atribui o nome do produto.
     * @param name Nome do produto.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém o preço do produto.
     * @return O valor do produto.
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Atribui o preço do produto.
     * @param price Preço do produto.
     */
    public void setPrice(Float price) {
        this.price = price;
    }
}
