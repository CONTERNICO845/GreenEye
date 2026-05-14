import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;
import java.security.cert.TrustAnchor;
import java.util.Base64;

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
        if(Configuracion.esModoObscuro == true );{
        this.setBackground(AppColors.COLOR_DARK_BACKGROUND);    
        }
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(Color.WHITE);
        if (Configuracion.esModoObscuro) {
            panelIzquierdo.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
        }


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
            if (Configuracion.esModoObscuro) {
                g2.setColor(AppColors.COLOR_DARK_BLUE);
            }
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

        //CONFIGURACIÓN BOTÓN CAMARA
        btnCamara.setBackground(AppColors.COLOR_MAIN_BUTTONS);
        btnCamara.setForeground(Color.WHITE);           
        btnCamara.setOpaque(true);                      
        btnCamara.setBorderPainted(false);
        btnCamara.setFocusPainted(false);
        if(Configuracion.esModoObscuro == true){
            btnCamara.setBackground(AppColors.COLOR_DARK_BACKGROUND);
            btnCamara.setForeground(AppColors.COLOR_DARK_BLUE);
        }             

        //CONFIGURACIÓN BOTÓN ARCHIVO
        btnArchivo.setBackground(AppColors.COLOR_MAIN_BUTTONS);
        btnArchivo.setForeground(Color.WHITE);
        btnArchivo.setOpaque(true);
        btnArchivo.setBorderPainted(false);
        btnArchivo.setFocusPainted(false);
        if(Configuracion.esModoObscuro == true){
            btnArchivo.setBackground(AppColors.COLOR_DARK_BACKGROUND);
            btnArchivo.setForeground(AppColors.COLOR_DARK_BLUE);
        }     

        //CONFIGURACIÓN BOTÓN ESCANEAR
        btnEscanear.setBackground(AppColors.COLOR_MAIN_BUTTONS);
        btnEscanear.setForeground(Color.WHITE);
        btnEscanear.setOpaque(true);
        btnEscanear.setBorderPainted(false);
        btnEscanear.setFocusPainted(false);
        if(Configuracion.esModoObscuro == true){
            btnEscanear.setBackground(AppColors.COLOR_DARK_BACKGROUND);
            btnEscanear.setForeground(AppColors.COLOR_DARK_BLUE);
        }     

        panelBotones.add(btnCamara);
        panelBotones.add(btnArchivo);
        panelBotones.add(btnEscanear);

        lblObjetoDetectado = new JLabel("Esperando imagen...", SwingConstants.CENTER);
        lblObjetoDetectado.setFont(new Font("Arial", Font.BOLD, 18));
        lblObjetoDetectado.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblObjetoDetectado.setForeground(Color.white);
        if(Configuracion.esModoObscuro == true){
            lblObjetoDetectado.setForeground(AppColors.COLOR_DARK_BLUE);
        }


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
                
                // 2. Instanciar tu nueva clase conectora a Ollama
                IA_Conector conector = new IA_Conector();
                
                // 3. Enviar y recibir respuesta
                String respuesta = conector.clasificarImagen(base64);

                SwingUtilities.invokeLater(() -> {
                    lblObjetoDetectado.setText(respuesta.toUpperCase());
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    lblObjetoDetectado.setText("Error de conexión: " + ex.getMessage());
                });
                ex.printStackTrace();
            }
        }).start();
        this.revalidate();
        this.repaint(); 
    }

    private String convertirImagenABase64(BufferedImage imagen) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagen, "jpg", baos);
        byte[] bytesImagen = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytesImagen);
    }
}