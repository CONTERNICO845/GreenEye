import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class WebCam extends JFrame {
    private BufferedImage imagenActual;
    private JPanel panelFoto;
    
    // Configuración de diseño
    private final int TAMANO_CIRCULO = 500; 
    private final int MARGEN_INTERNO = 15; 
    private final Color COLOR_VERDE = new Color(34, 139, 34); 

    public WebCam() {
        setTitle("Clasificador de Basura");
        setSize(1920, 1080); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Layout principal vertical
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(Color.WHITE);

        // --- 1. PANEL DEL CÍRCULO ---
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
        panelFoto.setPreferredSize(new Dimension(600, 550));
        panelFoto.setMaximumSize(new Dimension(700, 600));
        panelFoto.setOpaque(false);

        // --- 2. PANEL DE BOTONES (Horizontal) ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);

        JButton btnCamara = new JButton("Usar Cámara");
        JButton btnScan = new JButton("Escanear"); // Botón nuevo
        JButton btnArchivo = new JButton("Subir Archivo");

        // Tamaño uniforme para los tres botones
        Dimension dimBoton = new Dimension(160, 40);
        btnCamara.setPreferredSize(dimBoton);
        btnScan.setPreferredSize(dimBoton);
        btnArchivo.setPreferredSize(dimBoton);

        // Agregamos en el orden solicitado: Cámara - Escanear - Archivo
        panelBotones.add(btnCamara);
        panelBotones.add(btnScan);
        panelBotones.add(btnArchivo);

        // --- 3. EVENTOS ---
        btnCamara.addActionListener(e -> abrirCamara());
        btnScan.addActionListener(e -> escanearImagen()); // Acción para escanear
        btnArchivo.addActionListener(e -> cargarArchivo());

        // --- 4. ENSAMBLAJE ---
        add(Box.createVerticalStrut(30)); 
        add(panelFoto);
        add(Box.createVerticalStrut(20)); 
        add(panelBotones); 
    }

    private void escanearImagen() {
        if (imagenActual == null) {
            JOptionPane.showMessageDialog(this, "Primero captura o sube una imagen para escanear.");
            return;
        }
        // Aquí puedes agregar la lógica de clasificación
        System.out.println("Escaneando imagen...");
        JOptionPane.showMessageDialog(this, "Iniciando proceso de escaneo...");
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

                JDialog ventanaCam = new JDialog(this, "Capturar", true);
                JButton btnCaptura = new JButton("TOMAR FOTO");
                btnCaptura.addActionListener(e -> {
                    imagenActual = webcam.getImage();
                    webcam.close();
                    ventanaCam.dispose();
                    panelFoto.repaint();
                });

                ventanaCam.add(p, BorderLayout.CENTER);
                ventanaCam.add(btnCaptura, BorderLayout.SOUTH);
                ventanaCam.pack();
                ventanaCam.setLocationRelativeTo(this);
                ventanaCam.setVisible(true);
            } catch (Exception ex) { ex.printStackTrace(); }
        }).start();
    }

    private void cargarArchivo() {
        JFileChooser selector = new JFileChooser();
        if (selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                imagenActual = ImageIO.read(selector.getSelectedFile());
                panelFoto.repaint();
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Error al cargar imagen");
            }
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new WebCam().setVisible(true));
    }
}