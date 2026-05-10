import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
    
    public static Connection conectar() {
        // Mantenemos la ruta a tu carpeta BaseDeDatos
        String url = "jdbc:sqlite:BaseDeDatos/clasificador.db";
        
        try {
            // Aseguramos que el driver de SQLite esté disponible
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            
            // Creamos la tabla con todos los campos necesarios (incluyendo 'nivel') y tipos de datos de SQLite
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                       + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                       + "user_name TEXT NOT NULL, "
                       + "email TEXT UNIQUE NOT NULL, "
                       + "password TEXT NOT NULL, "
                       + "creation_date TEXT, "
                       + "points INTEGER DEFAULT 0, "
                       + "nivel INTEGER DEFAULT 0, "
                       + "glass INTEGER DEFAULT 0, "
                       + "plastic INTEGER DEFAULT 0, "
                       + "metal INTEGER DEFAULT 0, "
                       + "hard_to_recycle INTEGER DEFAULT 0, "
                       + "paper INTEGER DEFAULT 0, "
                       + "organic INTEGER DEFAULT 0, "
                       + "user_photo TEXT NOT NULL"
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