import java.util.Scanner;
1
public class ExampleHowToUseConsulta {
/*
Codigo generado POR IA para consultas 
*/
    public static void main(String[] args) {
        Consultas consultas = new Consultas();
        Scanner sc = new Scanner(System.in);

        System.out.println("--- 🚀 MÓDULO DE PRUEBAS - EQUIPO MCQUACK ---");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Registrar Usuario");
        System.out.println("3. Modo Debug (ID 1)");
        System.out.print("Selecciona: ");
        
        int modo = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        if (modo == 3) {
            Consultas.loggedUserId = 1; 
            System.out.println("⚠️ Modo Debug activado. Usuario ID: 1");
            abrirMenuPrincipal(consultas, sc);

        } else if (modo == 2) {
            System.out.print("Nuevo Correo: ");
            String correo = sc.nextLine();
            System.out.print("Nueva Contraseña: ");
            String pass = sc.nextLine();
            System.out.print("Nombre de Usuario: ");
            String nombre = sc.nextLine();
            System.out.print("ID de Foto (ej. 1, 2, 3): ");
            String foto = sc.nextLine();
            
            consultas.registerUser(correo, pass, nombre, foto);
            System.out.println("✅ Ahora intenta iniciar sesión con tu nueva cuenta.");

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
            System.out.println("6. Agregar Puntos (¡Probar Updates!)");
            System.out.println("7. Salir");
            System.out.print("Opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- 👤 MIS ESTADÍSTICAS ---");
                    System.out.println("💰 Total Puntos Generales: " + consultas.getPoints());
                    System.out.println("  ↳ 🧊 Vidrio: " + consultas.getGlassPoints());
                    System.out.println("  ↳ 🥤 Plástico: " + consultas.getPlasticPoints());
                    System.out.println("  ↳ 🏗️ Metal: " + consultas.getMetalPoints());
                    System.out.println("  ↳ ⚠️ Difícil: " + consultas.getHardToRecyclePoints());
                    System.out.println("  ↳ 📄 Papel: " + consultas.getPaper());
                    System.out.println("  ↳ 🍎 Orgánico: " + consultas.getOrganic());
                    break;
                case 2:
                    System.out.println("\n--- 🏆 TOP 10 USUARIOS ---");
                    String[][] top10 = Consultas.getTopTenUsers(); // Retorna la matriz
                    for (int i = 0; i < top10.length; i++) {
                        if (top10[i][0] != null) { // Solo imprime si hay datos
                            System.out.println((i + 1) + ". " + top10[i][0] + " - " + top10[i][1] + " pts");
                        }
                    }
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
                    menuAgregarPuntos(consultas, sc);
                    break;
                case 7:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void mostrarTotalesGlobales(Consultas consultas) {
        System.out.println("\n--- 🌍 IMPACTO GLOBAL DE LA COMUNIDAD ---");
        System.out.println("✨ Puntos totales generados: " + consultas.getTotalPoints());
        System.out.println("🧊 Vidrio reciclado: " + consultas.getTotalGlass());
        System.out.println("🥤 Plástico reciclado: " + consultas.getTotalPlastic());
        System.out.println("🏗️ Metal reciclado: " + consultas.getTotalMetal());
        System.out.println("⚠️ Material difícil: " + consultas.getTotalHardToRecycle());
        System.out.println("📄 Papel reciclado: " + consultas.getTotalPaper());
        System.out.println("🍎 Material orgánico: " + consultas.getTotalOrganic());
        System.out.println("------------------------------------------");
    }

    public static void menuAgregarPuntos(Consultas consultas, Scanner sc) {
        System.out.println("\n--- ➕ SUMAR PUNTOS ---");
        System.out.println("1. Puntos Generales");
        System.out.println("2. Vidrio");
        System.out.println("3. Plástico");
        System.out.println("4. Metal");
        System.out.println("5. Reciclaje Difícil");
        System.out.println("6. Papel");
        System.out.println("7. Orgánico");
        System.out.print("Elige material a sumar: ");
        int mat = sc.nextInt();
        System.out.print("¿Cuántos puntos deseas agregar?: ");
        int pts = sc.nextInt();
        sc.nextLine();

        switch (mat) {
            case 1 -> consultas.updatePoints(pts);
            case 2 -> consultas.updateGlassPoints(pts);
            case 3 -> consultas.updatePlasticPoints(pts);
            case 4 -> consultas.updateMetalPoints(pts);
            case 5 -> consultas.updateHardToRecyclePoints(pts);
            case 6 -> consultas.updatePaperPoints(pts);
            case 7 -> consultas.updateOrganicPoints(pts);
            default -> System.out.println("Material no válido.");
        }
    }
}