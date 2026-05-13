//Codigo hecho por el God Giovanni Sandoval
import javax.swing.*;

class MyPanel extends PanelBase {

    public MyPanel(String name) { 
        super(name);
    }
}

//Aqui inicializa todo el programa
//Falta inicializar el server de python (Creo)
public class MainWindow {

    // Método para iniciar el puente de Python de forma invisible
    public static void StartPythonServer() {
    new Thread(() -> {
        try {
            // Asegúrate de usar la ruta correcta a tu archivo .py
            ProcessBuilder pb = new ProcessBuilder("python", "servidor_ia.py");
            pb.inheritIO(); // Esto hace que los "prints" de Python salgan en la consola de Java
            Process proceso = pb.start();
            
            // Esto asegura que si cierras Java, el proceso de Python también se detenga
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                proceso.destroy();
            }));
            
            System.out.println("--- Servidor Python iniciado desde Java ---");
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor Python: " + e.getMessage());
        }
    }).start();
}

    // Main method that starts the application
    public static void main(String[] args) {
        StartPythonServer();

        System.out.println("iniciando app"); // Prints a message indicating the start of the app

        // Creates and configures the main window
        JFrame window = new JFrame("Main window");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configures the close operation to exit on closing the window

        // Adds the content panel to the window
        MyPanel content = new MyPanel("Inicio"); // Creates a new instance of MyPanel with the name "Inicio"
        window.add(content);

        // Packs and configures the window's layout and size
        window.pack(); // Adjusts the window's size to fit its contents
        window.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizes the window
        window.setLocationRelativeTo(null); // Centers the window on the screen

        // Makes the window visible
        window.setVisible(true);

        // Initializes and connects the Bluetooth controller
        ControladorBluetooth bluetooth = new ControladorBluetooth(); // Creates a new instance of ControladorBluetooth
       bluetooth.conectar("COM8"); // Selecciona el puerto que necesite (a mi no me funciona el 8 pero a Manuel si)
    }
}
