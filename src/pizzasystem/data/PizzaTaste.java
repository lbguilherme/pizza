package pizzasystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabe
 */
public class PizzaTaste {

    /**
     *
     */
    public enum Size {

        /**
         *
         */
        Family,

        /**
         *
         */
        Big,

        /**
         *
         */
        Medium,
    }

    private String name;
    private Float priceMedium;
    private Float priceBig;
    private Float priceFamily;

    /**
     *
     * @return o nome do sabor de pizza
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return retorna o preço desta pizza de tamanho medio
     */
    public Float getPriceMedium() {
        return priceMedium;
    }

    /**
     *
     * @param priceMedium
     */
    public void setPriceMedium(Float priceMedium) {
        this.priceMedium = priceMedium;
    }

    /**
     *
     * @return retorna o preço desta pizza de tamanho grande
     */
    public Float getPriceBig() {
        return priceBig;
    }

    /**
     *
     * @param priceBig
     */
    public void setPriceBig(Float priceBig) {
        this.priceBig = priceBig;
    }

    /**
     *
     * @return retorna o preço desta pizza de tamanho familia
     */
    public Float getPriceFamily() {
        return priceFamily;
    }

    /**
     *
     * @param priceFamily
     */
    public void setPriceFamily(Float priceFamily) {
        this.priceFamily = priceFamily;
    }

    /**
     *
     * @param size
     * @return retorna o preço da pizza de tamanho passado pelo argumento
     */
    public Float getPrice(Size size) {
        switch (size) {
            case Medium: return priceMedium;
            case Big: return priceBig;
            case Family: return priceFamily;
        }
        throw new RuntimeException("Unknown PizzaTaste.Size");
    }
    
    /**
     *
     * @param result
     * @throws SQLException
     */
    protected void setFromResultSet(ResultSet result) throws SQLException {
        setName(result.getString("name"));
        setPriceMedium(result.getFloat("priceMedium"));
        setPriceBig(result.getFloat("priceBig"));
        setPriceFamily(result.getFloat("priceFamily"));
    }

    /**
     *
     * @param db
     * @return retorna a lista de todas as pizzas que estão no database
     * @throws SQLException
     */
    public static List<PizzaTaste> fetchAll(Connection db) throws SQLException {
        String query = "select * from PizzaTaste;";
        PreparedStatement stmt = db.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        List<PizzaTaste> list = new ArrayList<>();
        while (result.next()) {
            PizzaTaste pizzaTaste = new PizzaTaste();
            pizzaTaste.setFromResultSet(result);
            list.add(pizzaTaste);
        }

        return list;
    }

    /**
     *
     * @param db
     * @throws SQLException
     */
    public void save(Connection db) throws SQLException {
        String query = "INSERT INTO PizzaTaste VALUES(?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE priceMedium=VALUES(priceMedium), " +
            "priceBig=VALUES(priceBig), priceFamily=VALUES(priceFamily);";
        PreparedStatement stmt = db.prepareStatement(query);
        stmt.setString(1, getName());
        stmt.setFloat(2, getPriceMedium());
        stmt.setFloat(3, getPriceBig());
        stmt.setFloat(4, getPriceFamily());
        stmt.executeUpdate();
    }
}
