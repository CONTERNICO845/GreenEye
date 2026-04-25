import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
    
    public static Connection conectar() {
        // Mantenemos la ruta a tu carpeta BaseDeDatos
        String url = "jdbc:sqlite:BaseDeDatos/clasificador.db";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            
            // Creamos la tabla con todos los campos necesarios para el proyecto
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                       + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                       + "email TEXT UNIQUE NOT NULL, "
                       + "password TEXT NOT NULL, "
                       + "creation_date VARCHAR(20), "
                       + "points INT DEFAULT 0, "
                       + "glass INT DEFAULT 0, "
                       + "plastic INT DEFAULT 0, "
                       + "metal INT DEFAULT 0, "
                       + "hard_to_recycle INT DEFAULT 0"
                       + ");";
            
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            
            return conn;
            
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }
}