import java.awt.*;
import javax.swing.*;

public class Login extends JFrame {


    private final Color COLOR_FONDO_MAIN = new Color(189, 236, 182); // Verde menta muy clarito
    private final Color COLOR_IZQUIERDO = new Color(34, 60, 43);    // Verde ecológico vivo 43, 153, 99
    private final Color COLOR_DERECHO = new Color(43, 153, 99);       // Verde bosque oscuro 34, 60, 43
    private final Color COLOR_TEXTO = new Color(255, 255, 255);      // Blanco puro para textos
   

    public Login() {
        setTitle("Login");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JPanel cuadroLogin = new JPanel(new GridLayout(1, 2));
        cuadroLogin.setPreferredSize(new Dimension(1000, 450));

        cuadroLogin.add(new PanelIzquierdo());
        cuadroLogin.add(new PanelDerecho());

        add(cuadroLogin, new GridBagConstraints());
        
        getContentPane().setBackground(COLOR_FONDO_MAIN);
    }
    
// --- PANEL IZQUIERDO 
    class PanelIzquierdo extends JPanel {
        public PanelIzquierdo() {
            setBackground(COLOR_IZQUIERDO);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER; // Centra todo horizontalmente
            gbc.gridx = 0;

            // 1. TÍTULO (Fila 0)
            JLabel titulo = new JLabel("Clasifica. Recicla. Transforma", SwingConstants.CENTER);
            titulo.setForeground(COLOR_TEXTO);
            titulo.setFont(new Font("Arial", Font.BOLD, 26));
            gbc.gridy = 0;
            add(titulo, gbc);

            // 2. LOGO (Fila 1 - Justo debajo del título)
            ImageIcon icono = new ImageIcon(new ImageIcon("Imagenes/LogoChillon.png").getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)); // Un poco más grande ya que va solo
            
            JLabel etiquetaLogo = new JLabel(icono);
            etiquetaLogo.setHorizontalAlignment(SwingConstants.CENTER);
            gbc.gridy = 1;
            gbc.insets = new Insets(15, 10, 15, 10); // Espacio extra arriba y abajo del logo
            add(etiquetaLogo, gbc);

            // 3. DESCRIPCIÓN (Fila 2 - Debajo del logo)
            JTextArea descripcion = new JTextArea("Descubre qué tipo de residuo tienes en las manos en cuestión de segundos. Entra ahora y deja que nuestro modelo inteligente haga el trabajo pesado por ti.");
            descripcion.setLineWrap(true);
            descripcion.setWrapStyleWord(true);
            descripcion.setEditable(false);
            descripcion.setOpaque(false);
            descripcion.setForeground(COLOR_TEXTO);
            descripcion.setFont(new Font("Arial", Font.PLAIN, 18));
            descripcion.setAlignmentX(CENTER_ALIGNMENT); // Intenta centrar el texto

            gbc.gridy = 2;
            gbc.insets = new Insets(5, 20, 10, 20); // Margen lateral para que el texto no toque los bordes
            add(descripcion, gbc);
        }
    }

    // PANEL DERECHO 
    class PanelDerecho extends JPanel {
        public PanelDerecho() {
            // Aplicamos el color global derecho
            setBackground(COLOR_DERECHO);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL; 
            gbc.gridx = 0;

            // Título
            JLabel labelSesion = new JLabel("Inicia Sesión", SwingConstants.CENTER);
            labelSesion.setForeground(COLOR_TEXTO); 
            labelSesion.setFont(new Font("Arial", Font.BOLD, 32));
            gbc.gridy = 0;
            add(labelSesion, gbc);

            // Correo
            JLabel labelcorreo = new JLabel("Correo:");
            labelcorreo.setForeground(COLOR_TEXTO); 
            gbc.gridy = 1;
            add(labelcorreo, gbc);

            JTextField txtCorreo = new JTextField(15);
            gbc.gridy = 2; 
            add(txtCorreo, gbc);

            // Contraseña
            JLabel labelPass = new JLabel("Contraseña:");
            labelPass.setForeground(COLOR_TEXTO);
            gbc.gridy = 3;
            add(labelPass, gbc);

            JPasswordField txtPass = new JPasswordField(50);
            gbc.gridy = 4;
            add(txtPass, gbc);


            // Botones
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            panelBotones.setOpaque(false);
            

            JButton btnIniciar = new JButton("Iniciar Sesión");
            btnIniciar.setBackground(COLOR_IZQUIERDO); 
            btnIniciar.setForeground(Color.WHITE);     
            btnIniciar.setFont(new Font("Arial", Font.BOLD, 14));
            btnIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR)); 

            
            JButton btnCrear = new JButton("Crear Cuenta");
            btnCrear.setBackground(COLOR_IZQUIERDO);
            btnCrear.setForeground(Color.WHITE);
            btnCrear.setFont(new Font("Arial", Font.BOLD, 14));
            btnCrear.setCursor(new Cursor(Cursor.HAND_CURSOR)); 

            panelBotones.add(btnIniciar);
            panelBotones.add(btnCrear);

            gbc.gridy = 5; 
            gbc.insets = new Insets(20, 8, 8, 8); 
            gbc.fill = GridBagConstraints.NONE; 
            
            add(panelBotones, gbc);

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}