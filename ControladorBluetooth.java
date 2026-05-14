
import com.fazecast.jSerialComm.SerialPort;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;
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
        puerto.setBaudRate(9600);

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

                System.out.println("Esperando confirmación selectiva del Arduino...");

                while (puerto.isOpen()) {
                    int bytesLeidos = entrada.read(buffer);

                    if (bytesLeidos > 0) {
                        String mensaje = new String(buffer, 0, bytesLeidos).trim();
                        System.out.println("RECIBIDO DESDE ARDUINO: [" + mensaje + "]");

                        // Evaluamos qué se entregó con éxito para afectar la BD correctamente
                        if (mensaje.contains("PLASTICO")) {
                            SwingUtilities.invokeLater(() -> {
                                Consultas.updatePlasticPoints(3); 
                                JOptionPane.showMessageDialog(null, "¡+3  puntos en reciclaje de Plástico!");
                            });
                        } 
                        else if (mensaje.contains("CARTON")) {
                            SwingUtilities.invokeLater(() -> {
                                Consultas.updatePaperPoints(3); 
                                JOptionPane.showMessageDialog(null, "¡+3 puntos en Papel y Cartón!");
                            });
                        } 
                        else if (mensaje.contains("METAL")) {
                            SwingUtilities.invokeLater(() -> {
                                Consultas.updateMetalPoints(3);
                                JOptionPane.showMessageDialog(null, "¡+3 puntos en Metales!");
                            });
                        }
                        else if (mensaje.contains("ORGANICO")) {
                            SwingUtilities.invokeLater(() -> {
                                Consultas.updateOrganicPoints(3); 
                                JOptionPane.showMessageDialog(null, "¡+3 puntos en Orgánicos!");
                            });
                        }
                        else if (mensaje.contains("VIDRIO")) {
                            SwingUtilities.invokeLater(() -> {
                                //Consultas.updatePilasPoints(3); 
                                JOptionPane.showMessageDialog(null, "¡+3 puntos en Orgánicos!");
                            });
                        }
                        else if (mensaje.contains("DIFICIL_RECICLAJE")) {
                            SwingUtilities.invokeLater(() -> {
                                Consultas.updateHardToRecyclePoints(3); 
                                JOptionPane.showMessageDialog(null, "¡+3 puntos en Orgánicos!");
                            });
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error en la escucha selectiva: " + e.getMessage());
            }
        });

        hilo.setDaemon(true);
        hilo.start();
    }

    public void enviarDato(String dato) {
        if (salida != null && puerto != null && puerto.isOpen()) {
            salida.print(dato);
            salida.flush(); // Fuerza el envío inmediato por el puerto serial
            System.out.println(">>> SEÑAL ENVIADA AL ARDUINO: " + dato);
        } else {
            System.err.println("Error: No se pudo enviar el dato. El puerto no está abierto.");
        }
    }
}
