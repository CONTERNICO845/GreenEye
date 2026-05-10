import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DebugMaestro {
    public static void main(String[] args) {
        // Alargamos un poco la línea para que quepa la nueva columna
        System.out.println("=====================================================================================================================================");
        System.out.println("🚀 SISTEMA DE DEPURACIÓN TOTAL - EQUIPO MCQUACK");
        System.out.println("=====================================================================================================================================");

        // 1. Verificación de la conexión física
        System.out.print("1. Buscando base de datos en Arch Linux... ");
        try (Connection conn = DatabaseConnection.conectar()) {
            
            if (conn == null) {
                System.out.println("❌ ERROR: No se pudo conectar.");
                return;
            }
            System.out.println("✅ ¡CONECTADO!");

            Statement stmt = conn.createStatement();
            
            // 2. Reporte con columnas ajustadas para ver TODOS los campos
            System.out.println("\n2. REPORTE COMPLETO DE USUARIOS (VERIFICACIÓN DE CAMBIOS):");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
            // Agregamos NIV (Nivel) al formato de impresión antes de PTS (Puntos)
            System.out.printf("%-3s | %-12s | %-20s | %-12s | %-3s | %-4s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s\n", 
                              "ID", "USUARIO", "EMAIL", "PASSWORD", "NIV", "PTS", "VID", "PLA", "MET", "DIF", "PAP", "ORG", "FOT");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            int contador = 0;
            
            while (rs.next()) {
                // Extraemos cada valor de la fila actual, incluyendo el nuevo campo "nivel"
                System.out.printf("%-3d | %-12s | %-20s | %-12s | %-3d | %-4d | %-3d | %-3d | %-3d | %-3d | %-3d | %-3d | %-3s\n",
                                  rs.getInt("id"),
                                  rs.getString("user_name"), // Ajustado a user_name en minúscula
                                  rs.getString("email"),
                                  rs.getString("password"),
                                  rs.getInt("nivel"),        // NUEVO: Nivel agregado aquí
                                  rs.getInt("points"),
                                  rs.getInt("glass"),
                                  rs.getInt("plastic"),
                                  rs.getInt("metal"),
                                  rs.getInt("hard_to_recycle"),
                                  rs.getInt("paper"),        
                                  rs.getInt("organic"),      
                                  rs.getString("user_photo")); 
                contador++;
            }
            
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("📊 Total de registros encontrados: " + contador);
            System.out.println("\n✅ Depuración finalizada con éxito.");
            System.out.println("👤 Desarrollador: Geovani Gael Carmona Barbosa");

        } catch (Exception e) {
            System.err.println("\n❌ FALLO CRÍTICO EN EL DEBUG:");
            System.err.println("Mensaje: " + e.getMessage());
            // e.printStackTrace(); 
        }
    }
}