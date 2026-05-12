
import com.fazecast.jSerialComm.SerialPort;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class ControladorBluetooth {

    private SerialPort puerto;
    private PrintWriter salida;

    public void conectar(String nombrePuerto) {
        // 1. Limpieza de seguridad: si el puerto estaba abierto, lo cerramos
        if (puerto != null && puerto.isOpen()) {
            puerto.closePort();
        }

        puerto = SerialPort.getCommPort(nombrePuerto);

        // 2. Configuración de parámetros para el JDY-31
        puerto.setBaudRate(38400);

        // 3. CAMBIO CRÍTICO: Timeout semi-bloqueante
        // Esto evita que Java se desconecte si el sensor no manda nada en 2 segundos
        puerto.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 2000, 0);

        if (puerto.openPort()) {
            System.out.println("--- Conectado con éxito a " + nombrePuerto + " ---");

            // 4. Pequeña pausa para estabilizar la línea serial
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            salida = new PrintWriter(puerto.getOutputStream(), true);
            iniciarEscucha();
        } else {
            System.err.println("Error: No se pudo abrir el puerto " + nombrePuerto);
            System.err.println("Asegúrate de que no esté abierto el Monitor Serial de Arduino.");
        }
    }

    private void iniciarEscucha() {
        Thread hilo = new Thread(() -> {
            try {
                java.io.InputStream entrada = puerto.getInputStream();
                byte[] buffer = new byte[1024];

                System.out.println("Esperando bytes del Arduino...");

                while (puerto.isOpen()) {

                    int bytesLeidos = entrada.read(buffer); // 🔥 lectura directa (clave)

                    if (bytesLeidos > 0) {
                        String mensaje = new String(buffer, 0, bytesLeidos).trim();

                        System.out.println("RECIBIDO RAW: [" + mensaje + "]");

                        if (mensaje.contains("DETECTADO")) {
                            System.out.println("¡CRÍTICO: Sensor activado!");
                        }
                    }
                }

            } catch (Exception e) {
                System.err.println("Error en lectura: " + e.getMessage());
            }
        });

        hilo.setDaemon(true);
        hilo.start();
    }
}
