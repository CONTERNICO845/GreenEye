import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

/*
Codigo generado POR IA para generear cuentas aleatorias

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
                int randomId = rand.nextInt(99999); // Para que los correos y nombres no se repitan
                String userName = "Bot_" + randomId;
                String email = "bot" + randomId + "@mcquack.com";
                String password = "pass" + rand.nextInt(9999);
                String photoId = String.valueOf(rand.nextInt(6)); // Foto del 0 al 5

                // 2. Generamos puntos aleatorios entre 0 y 100
                int points = rand.nextInt(101);
                int glass = rand.nextInt(101);
                int plastic = rand.nextInt(101);
                int metal = rand.nextInt(101);
                int hard = rand.nextInt(101);

                // 3. Hacemos un INSERT personalizado para meter TODOS los datos de golpe
                String query = "INSERT INTO users (user_Name, email, password, creation_date, points, glass, plastic, metal, hard_to_recycle, user_photo) "
                        +
                        "VALUES ('" + userName + "', '" + email + "', '" + password + "', '" + creationDate + "', " +
                        points + ", " + glass + ", " + plastic + ", " + metal + ", " + hard + ", '" + photoId + "')";

                stmt.executeUpdate(query);
                cuentasCreadas++;
            }

            System.out.println("✅ ¡Éxito! Se crearon " + cuentasCreadas + " cuentas falsas con puntos aleatorios.");

        } catch (Exception e) {
            System.out.println("❌ Ocurrió un error al generar las cuentas: " + e.getMessage());
        }
    }
}