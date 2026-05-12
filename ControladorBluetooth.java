import com.fazecast.jSerialComm.SerialPort;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class ControladorBluetooth {
    // Estas NO deben llevar la palabra 'static'
    private SerialPort puerto;
    private PrintWriter salida;

    public void conectar(String nombrePuerto) {
        puerto = SerialPort.getCommPort(nombrePuerto);
        puerto.setBaudRate(9600);
        puerto.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        if (puerto.openPort()) {
            System.out.println("--- Conectado con éxito a " + nombrePuerto + " ---");
            salida = new PrintWriter(puerto.getOutputStream(), true);
            iniciarEscucha();
        } else {
            System.err.println("Error: No se pudo abrir el puerto " + nombrePuerto);
        }
    }

    private void iniciarEscucha() {
        Thread hilo = new Thread(() -> {
            try (Scanner s = new Scanner(puerto.getInputStream())) {
                while (s.hasNextLine()) {
                    String linea = s.nextLine().trim();
                    System.out.println("Arduino dice: " + linea);
                    
                    if (linea.equals("DETECTADO")) {
                        SwingUtilities.invokeLater(() -> {
                            System.out.println("¡Señal recibida en la App!");
                        });
                    }
                }
            } catch (Exception e) {
                System.out.println("Conexión finalizada.");
            }
        });
        hilo.start();
    }

    public void enviarDato(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
        }
    }
}