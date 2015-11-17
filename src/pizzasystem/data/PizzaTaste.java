package pizzasystem.data;

/**
 * Um sabor de pizza que esteja no menu.
 */
public class PizzaTaste {

    /**
     * Enumeração dos possíveis tamanhos de pizza.
     */
    public enum Size {

        /**
         * Tamanho Família.
         */
        Family,

        /**
         * Tamanho Grande.
         */
        Big,

        /**
         * Tamanho Médio.
         */
        Medium,
    }

    private String name;
    private Float priceMedium;
    private Float priceBig;
    private Float priceFamily;

    /**
     * Obtém o nome da pizza
     * @return o nome do sabor de pizza
     */
    public String getName() {
        return name;
    }

    /**
     * Atribui o nome da pizza
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtem o preço para o tamanho médio
     * @return retorna o preço desta pizza de tamanho médio
     */
    public Float getPriceMedium() {
        return priceMedium;
    }

    /**
     * Atribui o preço para o tamanho médio
     * @param priceMedium O preço
     */
    public void setPriceMedium(Float priceMedium) {
        this.priceMedium = priceMedium;
    }

    /**
     * Obtem o preço para o tamanho grande
     * @return retorna o preço desta pizza de tamanho grande
     */
    public Float getPriceBig() {
        return priceBig;
    }

    /**
     * Atribui o preço para o tamanho grande
     * @param priceBig O preço
     */
    public void setPriceBig(Float priceBig) {
        this.priceBig = priceBig;
    }

    /**
     * Obtem o preço para o tamanho familia
     * @return retorna o preço desta pizza de tamanho familia
     */
    public Float getPriceFamily() {
        return priceFamily;
    }

    /**
     * Atribui o preço para o tamanho familia
     * @param priceFamily O preço
     */
    public void setPriceFamily(Float priceFamily) {
        this.priceFamily = priceFamily;
    }

    /**
     * Obtém o preço da pizza para um tamanho específico.
     * @param size O tamanho da pizza
     * @return O preço da pizza
     */
    public Float getPrice(Size size) {
        switch (size) {
            case Medium: return priceMedium;
            case Big: return priceBig;
            case Family: return priceFamily;
        }
        throw new RuntimeException("Unknown PizzaTaste.Size");
    }
}
