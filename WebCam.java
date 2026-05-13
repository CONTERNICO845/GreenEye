/*import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class WebCam extends JFrame {
    private Process procesoPython;
    private BufferedImage imagenActual;
    private JPanel panelFoto;
    
    private JPanel panelResultados; 
    private JLabel lblObjetoDetectado;
    private JLabel lblBoteRecomendado;

    private final int TAMANO_CIRCULO = 500; 
    private final int MARGEN_INTERNO = 15;
    private final Color COLOR_VERDE = new Color(34, 139, 34); 

    public WebCam() {
        setTitle("Clasificador de Basura con IA");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "python", "servidor_ia.py");
            pb.redirectErrorStream(true); 
            procesoPython = pb.start();

            new Thread(() -> {
                try {
                  BufferedReader reader = new BufferedReader(new InputStreamReader(procesoPython.getInputStream()));
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        System.out.println("[PYTHON]: " + linea);
                    }
                } catch (Exception e) {}
            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(Color.WHITE);

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
        JButton btnScan = new JButton("Escanear");
        JButton btnArchivo = new JButton("Subir Archivo");

        Dimension dimBoton = new Dimension(160, 40);
        btnCamara.setPreferredSize(dimBoton);
        btnScan.setPreferredSize(dimBoton);
        btnArchivo.setPreferredSize(dimBoton);

        panelBotones.add(btnCamara);
        panelBotones.add(btnScan);
        panelBotones.add(btnArchivo);

        panelIzquierdo.add(Box.createVerticalStrut(30)); 
        panelIzquierdo.add(panelFoto);
        panelIzquierdo.add(Box.createVerticalStrut(20)); 
        panelIzquierdo.add(panelBotones);

        panelResultados = new JPanel(new BorderLayout());
        panelResultados.setPreferredSize(new Dimension(450, getHeight()));
        panelResultados.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 2, 0, 0, Color.LIGHT_GRAY), 
                new EmptyBorder(30, 30, 30, 30) 
        ));
        panelResultados.setBackground(new Color(245, 245, 250));

        JPanel panelCentroTexto = new JPanel();
        panelCentroTexto.setLayout(new BoxLayout(panelCentroTexto, BoxLayout.Y_AXIS));
        panelCentroTexto.setOpaque(false);

        JLabel tituloResultados = new JLabel("Clasificacion de Residuos");
        tituloResultados.setFont(new Font("Arial", Font.BOLD, 22));
        tituloResultados.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        lblObjetoDetectado = new JLabel("Esperando imagen...");
        lblObjetoDetectado.setFont(new Font("Arial", Font.ITALIC, 14));
        lblObjetoDetectado.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblObjetoDetectado.setForeground(Color.DARK_GRAY);

        lblBoteRecomendado = new JLabel("<html>--</html>");
        lblBoteRecomendado.setVerticalAlignment(SwingConstants.TOP); // Para que el texto empiece arriba
        
        // --- AQUÍ CREAMOS EL SCROLL Y METEMOS LA ETIQUETA ADENTRO ---
        JScrollPane scrollResultados = new JScrollPane(lblBoteRecomendado);
        scrollResultados.setPreferredSize(new Dimension(400, 500)); 
        scrollResultados.setBorder(null); 
        scrollResultados.setOpaque(false);
        scrollResultados.getViewport().setOpaque(false);
        scrollResultados.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollResultados.getVerticalScrollBar().setUnitIncrement(16); // Scroll suave con la rueda del ratón

        panelCentroTexto.add(tituloResultados);
        panelCentroTexto.add(Box.createVerticalStrut(10));
        panelCentroTexto.add(lblObjetoDetectado);
        panelCentroTexto.add(Box.createVerticalStrut(30));
        
        // EN LUGAR DE AGREGAR LA ETIQUETA DIRECTA, AGREGAMOS EL SCROLL
        panelCentroTexto.add(scrollResultados);
        
        panelResultados.add(panelCentroTexto, BorderLayout.NORTH);
        panelResultados.setVisible(false);
        
        add(panelIzquierdo, BorderLayout.CENTER);
        add(panelResultados, BorderLayout.EAST);
        
        btnCamara.addActionListener(e -> abrirCamara());
        btnScan.addActionListener(e -> escanearImagen());
        btnArchivo.addActionListener(e -> cargarArchivo());
    }

    private void escanearImagen() {
        if (imagenActual == null) {
            JOptionPane.showMessageDialog(this, "Primero captura o sube una imagen para escanear.");
            return;
        }

        if (!panelResultados.isVisible()) {
            panelResultados.setVisible(true);
            setSize(1250, getHeight()); 
            setLocationRelativeTo(null); 
            revalidate(); 
            repaint();
        }

        lblObjetoDetectado.setText("Analizando elementos...");
        lblBoteRecomendado.setText("<html>Estructurando clasificación...</html>");
        
        SwingWorker<String[], Void> worker = new SwingWorker<String[], Void>() {
            @Override
            protected String[] doInBackground() throws Exception {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(imagenActual, "png", baos);
                String imagenBase64 = Base64.getEncoder().encodeToString(baos.toByteArray());

                // CONEXIÓN AL NUEVO PUERTO 5006
                URL url = new URL("http://localhost:5009/clasificar");
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/json");
                conexion.setDoOutput(true);

                String jsonEntrada = "{\"imagen\": \"" + imagenBase64 + "\"}";
                try (OutputStream os = conexion.getOutputStream()) {
                    byte[] input = jsonEntrada.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "utf-8"));
                StringBuilder respuesta = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    respuesta.append(linea.trim());
                }

                String jsonRespuesta = respuesta.toString();
                String objeto = jsonRespuesta.replaceAll(".*\"objeto\"\\s*:\\s*\"([^\"]+)\".*", "$1");
                String bote = jsonRespuesta.replaceAll(".*\"bote\"\\s*:\\s*\"([^\"]+)\".*", "$1");
                
                return new String[]{objeto, bote};
            }

            @Override
            protected void done() {
                try {
                    String[] resultado = get();
                    lblObjetoDetectado.setText("Estado: " + resultado[0]);
                    
                    // Renderizamos el HTML exacto que nos mandó Python
                    lblBoteRecomendado.setText("<html>" + resultado[1] + "</html>");
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    lblObjetoDetectado.setText("Error en la conexion");
                    lblBoteRecomendado.setText("--");
                }
            }
        };
        worker.execute();
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
                    
                    lblObjetoDetectado.setText("Esperando escaneo...");
                    lblBoteRecomendado.setText("<html>--</html>");
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
                lblObjetoDetectado.setText("Esperando escaneo...");
                lblBoteRecomendado.setText("<html>--</html>");
                panelFoto.repaint();
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Error al cargar imagen");
            }
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        
        WebCam ventana = new WebCam();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (ventana.procesoPython != null) {
                ventana.procesoPython.destroy();
                System.out.println("Servidor de IA apagado correctamente.");
            }
        }));

        SwingUtilities.invokeLater(() -> ventana.setVisible(true));
    }
}
*/