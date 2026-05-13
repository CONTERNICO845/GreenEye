//Codigo hecho por el God Giovanni Sandoval
import java.io.IOException;
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
        try {
            // "python" es el comando, "servidor_ia.py" es tu archivo
            ProcessBuilder pb = new ProcessBuilder("python", "servidor_ia.py");
            
            // Esto evita que se abra una ventana negra molesta
            pb.redirectErrorStream(true); 
            pb.start(); 
            
            System.out.println("Servidor Python iniciado en segundo plano...");
        } catch (IOException e) {
            System.out.println("Error al iniciar Python: " + e.getMessage());
        }
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
