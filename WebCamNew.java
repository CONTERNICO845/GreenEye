
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class WebCamNew extends JPanel {

    private BufferedImage imagenActual;
    private JPanel panelFoto;
    private JLabel lblObjetoDetectado;

    private ControladorBluetooth bluetooth;

    private final int TAMANO_CIRCULO = 500;
    private final int MARGEN_INTERNO = 15;
    private final Color COLOR_VERDE = new Color(34, 139, 34);

    public WebCamNew(ControladorBluetooth bluetooth) {
        this.bluetooth = bluetooth;
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

                //Si ya estaba encendida la reinicia
                if (webcam.isOpen()) {
                    webcam.close();
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

        lblObjetoDetectado.setText("Analizando residuo en servidor local...");

        new Thread(() -> {
            try {
                // 1. Convertir imagen a Base64
                String base64 = convertirImagenABase64(imagenActual);

                // 2. Obtener respuesta del conector
                IA_Conector conector = new IA_Conector();
                String respuesta = conector.clasificarImagen(base64);

                // --- PASO 1: MOSTRAR RESPUESTA INMEDIATAMENTE ---
                SwingUtilities.invokeLater(() -> {
                    lblObjetoDetectado.setText(respuesta.toUpperCase());
                });

                // --- PASO 2: PROCESAR BLUETOOTH (Después de mostrar el texto) ---
                if (this.bluetooth != null) {
                    procesarEnvioBluetooth(respuesta);
                }

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    lblObjetoDetectado.setText("Error de conexión: " + ex.getMessage());
                });
                ex.printStackTrace();
            }
        }).start();
    }

    // Método auxiliar para no saturar el hilo principal
    private void procesarEnvioBluetooth(String respuesta) {
        String resUpper = respuesta.toUpperCase();
        String letraAEnviar = "";

        if (resUpper.contains("PLASTICO")) {
            letraAEnviar = "P";
        } else if (resUpper.contains("PAPEL") || resUpper.contains("CARTON")) {
            letraAEnviar = "C";
        } else if (resUpper.contains("METAL")) {
            letraAEnviar = "M";
        } else if (resUpper.contains("ORGANICO")) {
            letraAEnviar = "O";
        } else if (resUpper.contains("VIDRIO")) {
            letraAEnviar = "V";
        } else if (resUpper.contains("PILAS")) {
            letraAEnviar = "B";
        } else if (resUpper.contains("DIFICIL RECICLAJE")) {
            letraAEnviar = "R";
        }

        if (!letraAEnviar.isEmpty()) {
            bluetooth.enviarDato(letraAEnviar);
            System.out.println(">>> SEÑAL ENVIADA AL ARDUINO: " + letraAEnviar);
        } else {
            System.out.println(">>> OBJETO DETECTADO NO ES BASURA O NO ESTÁ CATEGORIZADO");
        }
    }

    private String convertirImagenABase64(BufferedImage imagen) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagen, "jpg", baos);
        byte[] bytesImagen = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytesImagen);
    }
}
