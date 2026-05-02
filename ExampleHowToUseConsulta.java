import java.util.Scanner;

public class ExampleHowToUseConsulta {

    public static void main(String[] args) {
        Consultas consultas = new Consultas();
        Scanner sc = new Scanner(System.in);

        System.out.println("--- 🚀 MÓDULO DE PRUEBAS - EQUIPO MCQUACK ---");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Modo Debug (ID 1)");
        System.out.print("Selecciona: ");
        
        int modo = sc.nextInt();
        sc.nextLine(); 

        if (modo == 2) {
            Consultas.loggedUserId = 1; // Ajustado al nombre real en tu clase Consultas
            System.out.println("⚠️ Modo Debug activado. Usuario ID: 1");
            abrirMenuPrincipal(consultas, sc);
        } else {
            System.out.print("Correo: ");
            String correo = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();

            if (consultas.validateUser(correo, pass)) {
                System.out.println("✅ Bienvenido, " + correo);
                abrirMenuPrincipal(consultas, sc);
            } else {
                System.out.println("❌ Error: Usuario o contraseña no válidos.");
            }
        }
        sc.close();
    }

    public static void abrirMenuPrincipal(Consultas consultas, Scanner sc) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- 📊 PANEL DE CONTROL ---");
            System.out.println("1. Mis Puntos y Materiales");
            System.out.println("2. Ver TOP 10 Usuarios");
            System.out.println("3. Estadísticas Globales (Toda la App)");
            System.out.println("4. Cambiar mi Contraseña");
            System.out.println("5. Ver ID de Foto");
            System.out.println("6. Salir");
            System.out.print("Opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- 👤 MIS ESTADÍSTICAS ---");
                    System.out.println("💰 Total Puntos: " + consultas.getPoints());
                    System.out.println("  ↳ 🧊 Vidrio: " + consultas.getGlassPoints());
                    System.out.println("  ↳ 🥤 Plástico: " + consultas.getPlasticPoints());
                    System.out.println("  ↳ 🏗️ Metal: " + consultas.getMetalPoints());
                    System.out.println("  ↳ ⚠️ Difícil: " + consultas.getHardToRecyclePoints());
                    break;
                case 2:
                    consultas.getTopTenUsers();
                    break;
                case 3:
                    mostrarTotalesGlobales(consultas);
                    break;
                case 4:
                    System.out.print("Nueva contraseña: ");
                    consultas.updatePassword(sc.nextLine());
                    break;
                case 5:
                    System.out.println("🖼️ Tu ID de foto es: " + consultas.getPhoto());
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Nueva función para comprobar el impacto total de la app
    public static void mostrarTotalesGlobales(Consultas consultas) {
        System.out.println("\n--- 🌍 IMPACTO GLOBAL DE LA COMUNIDAD ---");
        System.out.println("✨ Puntos totales generados: " + consultas.getTotalPoints());
        System.out.println("🧊 Vidrio reciclado: " + consultas.getTotalGlass());
        System.out.println("🥤 Plástico reciclado: " + consultas.getTotalPlastic());
        System.out.println("🏗️ Metal reciclado: " + consultas.getTotalMetal());
        System.out.println("⚠️ Material difícil: " + consultas.getTotalHardToRecycle());
        System.out.println("------------------------------------------");
    }
}