package pizzasystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pizza {

    private String taste1;
    private String taste2;
    private String taste3;
    private PizzaTaste.Size size;

    public String getTaste1() {
        return taste1;
    }

    public void setTaste1(String taste1) {
        this.taste1 = taste1;
    }

    public String getTaste2() {
        return taste2;
    }

    public void setTaste2(String taste2) {
        this.taste2 = taste2;
    }

    public String getTaste3() {
        return taste3;
    }

    public void setTaste3(String taste3) {
        this.taste3 = taste3;
    }

    public PizzaTaste.Size getSize() {
        return size;
    }

    public void setSize(PizzaTaste.Size size) {
        this.size = size;
    }
    
    protected void setFromResultSet(ResultSet result) throws SQLException {
        setTaste1(result.getString("taste1"));
        setTaste2(result.getString("taste1"));
        setTaste3(result.getString("taste1"));
        setSize(PizzaTaste.Size.valueOf(result.getString("size")));
    }
    
}
