
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * WebCamNew corregida para funcionar como un componente JPanel. Ahora puede ser
 * insertada en cualquier contenedor o switch de pantallas.
 */
public class WebCamNew extends JPanel {

    private BufferedImage imagenActual;
    private JPanel panelFoto;
    private JLabel lblObjetoDetectado;

    private final int TAMANO_CIRCULO = 500;
    private final int MARGEN_INTERNO = 15;
    private final Color COLOR_VERDE = new Color(34, 139, 34);

    public WebCamNew() {
        // Configuración básica del panel
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(Color.WHITE);

        // Panel personalizado para el dibujo de la foto circular
        panelFoto = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int x = (getWidth() - TAMANO_CIRCULO) / 2;
                int y = (getHeight() - TAMANO_CIRCULO) / 2;

                g2.setColor(COLOR_VERDE);
                g2.fillOval(x, y, TAMANO_CIRCULO, TAMANO_CIRCULO);

                if (imagenActual != null) {
                    int diametroFoto = TAMANO_CIRCULO - (MARGEN_INTERNO * 2);
                    int xFoto = x + MARGEN_INTERNO;
                    int yFoto = y + MARGEN_INTERNO;
                    Shape recorte = new Ellipse2D.Double(xFoto, yFoto, diametroFoto, diametroFoto);
                    g2.setClip(recorte);
                    g2.drawImage(imagenActual, xFoto, yFoto, diametroFoto, diametroFoto, null);
                }
                g2.dispose();
            }
        };
        panelFoto.setPreferredSize(new Dimension(700, 600));
        panelFoto.setOpaque(false);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);

        JButton btnCamara = new JButton("Usar Cámara");
        JButton btnArchivo = new JButton("Subir Archivo");
        JButton btnEscanear = new JButton("Escanear");

        Dimension dimBoton = new Dimension(160, 40);
        btnCamara.setPreferredSize(dimBoton);
        btnArchivo.setPreferredSize(dimBoton);
        btnEscanear.setPreferredSize(dimBoton);

        panelBotones.add(btnCamara);
        panelBotones.add(btnArchivo);
        panelBotones.add(btnEscanear);

        lblObjetoDetectado = new JLabel("Esperando imagen...", SwingConstants.CENTER);
        lblObjetoDetectado.setFont(new Font("Arial", Font.BOLD, 18));
        lblObjetoDetectado.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Construcción de la interfaz
        panelIzquierdo.add(Box.createVerticalStrut(30));
        panelIzquierdo.add(panelFoto);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(lblObjetoDetectado);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(panelBotones);

        add(panelIzquierdo, BorderLayout.CENTER);

        // Eventos
        btnCamara.addActionListener(e -> abrirCamara());
        btnArchivo.addActionListener(e -> cargarArchivo());
        btnEscanear.addActionListener(e -> iniciarClasificacion());
    }

    private void abrirCamara() {
        new Thread(() -> {
            try {
                Webcam webcam = Webcam.getDefault();
                if (webcam == null) {
                    JOptionPane.showMessageDialog(this, "No hay cámara disponible");
                    return;
                }
                webcam.setViewSize(WebcamResolution.VGA.getSize());
                WebcamPanel p = new WebcamPanel(webcam);
                p.setMirrored(true);

                // Se usa SwingUtilities.getWindowAncestor para que el JDialog se centre en la ventana principal
                JDialog ventanaCam = new JDialog(SwingUtilities.getWindowAncestor(this), "Capturar", Dialog.ModalityType.APPLICATION_MODAL);
                JButton btnCaptura = new JButton("TOMAR FOTO");
                btnCaptura.addActionListener(e -> {
                    imagenActual = webcam.getImage();
                    webcam.close();
                    ventanaCam.dispose();

                    lblObjetoDetectado.setText("Foto lista. Presiona Escanear.");
                    panelFoto.repaint();
                });

                ventanaCam.setLayout(new BorderLayout());
                ventanaCam.add(p, BorderLayout.CENTER);
                ventanaCam.add(btnCaptura, BorderLayout.SOUTH);
                ventanaCam.pack();
                ventanaCam.setLocationRelativeTo(this);
                ventanaCam.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void cargarArchivo() {
        JFileChooser selector = new JFileChooser();
        if (selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                imagenActual = ImageIO.read(selector.getSelectedFile());
                lblObjetoDetectado.setText("Imagen lista. Presiona Escanear.");
                panelFoto.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar imagen");
            }
        }
    }

    private void iniciarClasificacion() {
        if (imagenActual == null) {
            JOptionPane.showMessageDialog(this, "Primero toma una foto o sube un archivo.");
            return;
        }

        lblObjetoDetectado.setText("Analizando residuo...");

        new Thread(() -> {
            try {
                String base64 = convertirImagenABase64(imagenActual);
                String respuesta = enviarAlServidorPython(base64);

                SwingUtilities.invokeLater(() -> {
                    lblObjetoDetectado.setText("Resultado: " + respuesta.toUpperCase());
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    lblObjetoDetectado.setText("Error de conexión" + ex.getMessage());
                });
                ex.printStackTrace();
            }
        }).start();
    }

    private String convertirImagenABase64(BufferedImage imagen) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagen, "jpg", baos);
        byte[] bytesImagen = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytesImagen);
    }

    private String enviarAlServidorPython(String base64) throws Exception {
        // 1. Limpieza absoluta de la cadena
        String base64Limpio = base64.replaceAll("\\r|\\n", "");

        // 2. Usar 127.0.0.1 es más confiable que 'localhost' en Windows
        URL url = new URL("http://127.0.0.1:5005/clasificar");
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

        conexion.setRequestMethod("POST");
        conexion.setRequestProperty("Content-Type", "application/json; utf-8"); // Añadimos charset
        conexion.setRequestProperty("Accept", "application/json");
        conexion.setDoOutput(true);
        // Establecer tiempos de espera para que Java no se rinda mientras la GPU procesa
        conexion.setConnectTimeout(5000);
        conexion.setReadTimeout(30000);

        String jsonInput = "{\"image\": \"" + base64Limpio + "\"}";

        try (OutputStream os = conexion.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // 3. Leer respuesta
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            String jsonRespuesta = response.toString();
            // Un split más seguro por si la IA devuelve comillas
            return jsonRespuesta.split("\"resultado\":\\s*\"")[1].split("\"")[0];
        }
    }
}
