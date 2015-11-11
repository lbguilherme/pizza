package pizzasystem.data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gabe
 */
public class Pizza {

    private String taste1;
    private String taste2;
    private String taste3;
    private PizzaTaste.Size size;

    /**
     *
     * @return retorna o primeiro sabor da pizza
     */
    public String getTaste1() {
        return taste1;
    }

    /**
     *
     * @param taste1
     */
    public void setTaste1(String taste1) {
        this.taste1 = taste1;
    }

    /**
     *
     * @return retorna o segundo sabor da pizza
     */
    public String getTaste2() {
        return taste2;
    }

    /**
     *
     * @param taste2
     */
    public void setTaste2(String taste2) {
        this.taste2 = taste2;
    }

    /**
     *
     * @return retorna o terceiro sabor da pizza
     */
    public String getTaste3() {
        return taste3;
    }

    /**
     *
     * @param taste3
     */
    public void setTaste3(String taste3) {
        this.taste3 = taste3;
    }

    /**
     *
     * @return retorna o tamanho da pizza
     */
    public PizzaTaste.Size getSize() {
        return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(PizzaTaste.Size size) {
        this.size = size;
    }
    
    /**
     *
     * @param result
     * @throws SQLException
     */
    protected void setFromResultSet(ResultSet result) throws SQLException {
        setTaste1(result.getString("taste1"));
        setTaste2(result.getString("taste2"));
        setTaste3(result.getString("taste3"));
        setSize(PizzaTaste.Size.valueOf(result.getString("size")));
    }
    
}
