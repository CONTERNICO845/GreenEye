import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

/*
Codigo generado POR IA para generar cuentas aleatorias
*/
public class GeneradorDePruebas {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("🦆 --- MÓDULO CREADOR DE BOTS - EQUIPO MCQUACK --- 🦆");
        System.out.println("Ideal para rellenar la base de datos del Clasificador de Basura.");
        System.out.print("¿Cuántas cuentas aleatorias deseas crear?: ");

        int cantidad = sc.nextInt();
        generarCuentas(cantidad);

        sc.close();
    }

    public static void generarCuentas(int cantidad) {
        Random rand = new Random();
        String creationDate = LocalDate.now().toString();
        int cuentasCreadas = 0;

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null) {
                System.out.println("❌ Error: No hay conexión a la base de datos.");
                return;
            }

            System.out.println("⏳ Generando e inyectando datos en la Matrix... espera un momento.");

            for (int i = 0; i < cantidad; i++) {
                // 1. Generamos cadenas aleatorias para el usuario
                int randomId = rand.nextInt(999999); // Aumentado para evitar colisiones
                String userName = "Bot_" + randomId;
                String email = "bot" + randomId + "@mcquack.com";
                String password = "pass" + rand.nextInt(9999);
                String photoId = String.valueOf(rand.nextInt(6)); // Foto del 0 al 5

                // 2. Generamos puntos aleatorios entre 0 y 100 para TODOS los materiales
                int nivel = rand.nextInt(101);     // EL NUEVO NIVEL
                int points = rand.nextInt(101);
                int glass = rand.nextInt(101);
                int plastic = rand.nextInt(101);
                int metal = rand.nextInt(101);
                int hard = rand.nextInt(101);
                int paper = rand.nextInt(101);     // NUEVO
                int organic = rand.nextInt(101);   // NUEVO

                // 3. Hacemos un INSERT personalizado para meter TODOS los datos (incluidos nivel, papel y organico)
                // OJO AQUÍ: Se agregó 'nivel' a las columnas y la variable + nivel + a los VALUES
                String query = "INSERT INTO users (user_Name, email, password, creation_date, nivel, points, glass, plastic, metal, hard_to_recycle, paper, organic, user_photo) "
                        + "VALUES ('" + userName + "', '" + email + "', '" + password + "', '" + creationDate + "', " + nivel + ", " + points + ", " +  glass + ", " + plastic + ", " + metal + ", " + hard + ", " + paper + ", " + organic + ", '" + photoId + "')";

                stmt.executeUpdate(query);
                cuentasCreadas++;
            }

            System.out.println("✅ ¡Éxito! Se crearon " + cuentasCreadas + " cuentas falsas con todos los materiales y niveles aleatorios.");

        } catch (Exception e) {
            System.out.println("❌ Ocurrió un error al generar las cuentas: " + e.getMessage());
        }
    }
}