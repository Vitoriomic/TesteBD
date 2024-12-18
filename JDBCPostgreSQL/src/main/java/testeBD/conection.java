package testeBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conection {
	public static Connection getConnection() {
        try {
            String url = "jdbc:postgresql://177.157.75.236:5432/GISA_Database";
            String user = "postgres";
            String password = "Tasm1963";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
