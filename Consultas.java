import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate; 

public class Consultas {

    // Método para el Login
    public boolean validarUsuario(String correo, String password) {
        try (Connection conn = DatabaseConnection.conectar();
             Statement stmt = conn.createStatement()) {
            
            if (conn == null) return false;

            String query = "SELECT * FROM users WHERE email = '" + correo + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);

            return rs.next(); // Si encuentra el usuario, devuelve true
            
        } catch (Exception e) {
            System.out.println("Error en consulta: " + e.getMessage());
            return false;
        }
    }

    // Método para el Registro actualizado con fecha
    public void registrarUsuario(String correo, String password) {
        try (Connection conn = DatabaseConnection.conectar();
             Statement stmt = conn.createStatement()) {
            
            if (conn == null) return;

            // 1. Obtenemos la fecha de hoy automáticamente
            String fechaCreacion = LocalDate.now().toString(); 

            // 2. Inyectamos correo, password y la fecha en la tabla
            String query = "INSERT INTO users (email, password, creation_date) VALUES ('" 
                           + correo + "', '" + password + "', '" + fechaCreacion + "')";
                           
            stmt.executeUpdate(query);
            System.out.println("✅ Usuario registrado con éxito el día: " + fechaCreacion);
            
        } catch (Exception e) {
            System.out.println("❌ Error al registrar: " + e.getMessage());
        }
    }
}