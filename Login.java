
import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Login extends JFrame {

    // Tus colores personalizados
    private final Color COLOR_FONDO_MAIN = new Color(189, 236, 182);
    private final Color COLOR_IZQUIERDO = new Color(34, 60, 43);
    private final Color COLOR_DERECHO = new Color(43, 153, 99);
    private final Color COLOR_TEXTO = new Color(255, 255, 255);

    // Regex codigo verificador de correo electronico
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // Componentes para el cambio de paneles
    private CardLayout cardLayout = new CardLayout();
    private JPanel contenedorDerecho;

    public Login() {
        setTitle("Sistema de Clasificación ");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // El cuadro central que contiene las dos mitades
        JPanel cuadroLogin = new JPanel(new GridLayout(1, 2));
        cuadroLogin.setPreferredSize(new Dimension(1000, 500));

        // AGREGAR LADO IZQUIERDO
        cuadroLogin.add(new PanelIzquierdo());

        // AGREGAR LADO DERECHO
        contenedorDerecho = new JPanel(cardLayout);

        contenedorDerecho.add(new VistaLogin(), "VISTA_LOGIN");
        contenedorDerecho.add(new VistaRegistro(), "VISTA_REGISTRO");

        cuadroLogin.add(contenedorDerecho);

        add(cuadroLogin, new GridBagConstraints());
        getContentPane().setBackground(COLOR_FONDO_MAIN);

    }

    // PANEL IZQUIERDO
    class PanelIzquierdo extends JPanel {

        public PanelIzquierdo() {

            setBackground(COLOR_IZQUIERDO);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 20, 10, 20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;

            JLabel titulo = new JLabel("Clasifica. Recicla. Transforma", SwingConstants.CENTER);
            titulo.setForeground(COLOR_TEXTO);
            titulo.setFont(new Font("Arial", Font.BOLD, 26));
            gbc.gridy = 0;
            add(titulo, gbc);

            // LOGO
            ImageIcon icono = new ImageIcon(new ImageIcon("Imagenes/LOGO/LogoChillon.png").getImage()
                    .getScaledInstance(250, 250, Image.SCALE_SMOOTH));
            JLabel etiquetaLogo = new JLabel(icono);
            gbc.gridy = 1;
            add(etiquetaLogo, gbc);

            JTextArea descripcion = new JTextArea(
                    "Descubre qué tipo de residuo tienes en las manos en cuestión de segundos. Entra ahora y deja que nuestro modelo inteligente haga el trabajo pesado por ti.");

            descripcion.setLineWrap(true);
            descripcion.setWrapStyleWord(true);
            descripcion.setEditable(false);
            descripcion.setOpaque(false);
            descripcion.setForeground(COLOR_TEXTO);
            descripcion.setFont(new Font("Arial", Font.PLAIN, 16));

            gbc.gridy = 2;
            add(descripcion, gbc);
        }
    }

    // VISTA DE LOGIN
    class VistaLogin extends JPanel {

        public VistaLogin() {

            setBackground(COLOR_DERECHO);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;

            JLabel labelSesion = new JLabel("Inicia Sesión", SwingConstants.CENTER);
            labelSesion.setForeground(COLOR_TEXTO);
            labelSesion.setFont(new Font("Arial", Font.BOLD, 32));
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 20, 0);
            add(labelSesion, gbc);

            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.gridy = 1;
            add(crearLabel("Correo:"), gbc);
            JTextField txtCorreo = new JTextField(15);
            gbc.gridy = 2;
            add(txtCorreo, gbc);

            gbc.gridy = 3;
            add(crearLabel("Contraseña:"), gbc);
            JPasswordField txtPass = new JPasswordField(15);
            gbc.gridy = 4;
            add(txtPass, gbc);

            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            panelBotones.setOpaque(false);

            JButton btnIniciar = crearBoton("Iniciar Sesión");
            JButton btnCrear = crearBoton("Crear Cuenta");

            panelBotones.add(btnIniciar);
            panelBotones.add(btnCrear);

            gbc.gridy = 5;
            gbc.insets = new Insets(30, 8, 8, 8);
            add(panelBotones, gbc);

            // ACCIÓN: CAMBIAR A REGISTRO
            btnCrear.addActionListener(e -> cardLayout.show(contenedorDerecho, "VISTA_REGISTRO"));

            // ACCIÓN: INICIAR SESIÓN (CONEXIÓN A BASE DE DATOS CORREGIDA)
            btnIniciar.addActionListener(e -> {
                String correo = txtCorreo.getText();
                String pass = new String(txtPass.getPassword());

                // Evitar campos vacíos (Usamos Login.this para evitar errores de contexto)
                if (correo.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "Por favor, llena todos los campos.", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Consultas consultas = new Consultas();
                if (consultas.validateUser(correo, pass)) {
                    JOptionPane.showMessageDialog(Login.this, "¡Bienvenido al sistema!", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                    // 1. Instanciamos y mostramos tu ventana principal
                    MainWindow.main(null);

                    // 2. Cerramos la ventana de Login de forma segura
                    Login.this.dispose();

                } else {
                    JOptionPane.showMessageDialog(Login.this, "Correo o contraseña incorrectos.", "Error de Login",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    // --- VISTA DE REGISTRO ---
    class VistaRegistro extends JPanel {

        public VistaRegistro() {
            setBackground(COLOR_DERECHO);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 8, 5, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;

            JLabel labelReg = new JLabel("Registro", SwingConstants.CENTER);
            labelReg.setForeground(COLOR_TEXTO);
            labelReg.setFont(new Font("Arial", Font.BOLD, 32));
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 15, 0);
            add(labelReg, gbc);

            // ASIGNANDO VARIABLES A LOS CAMPOS DE TEXTO
            gbc.insets = new Insets(5, 8, 5, 8);
            gbc.gridy = 1;
            add(crearLabel("Nombre Completo:"), gbc);
            JTextField txtNombre = new JTextField(15);
            gbc.gridy = 2;
            add(txtNombre, gbc);

            gbc.gridy = 3;
            add(crearLabel("Correo Electrónico:"), gbc);
            JTextField txtCorreo = new JTextField(50);
            gbc.gridy = 4;
            add(txtCorreo, gbc);

            gbc.gridy = 5;
            add(crearLabel("Contraseña:"), gbc);
            JPasswordField txtPass = new JPasswordField(50);
            gbc.gridy = 6;
            add(txtPass, gbc);

            JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            pBotones.setOpaque(false);

            JButton btnRegistrar = crearBoton("Registrarme");
            JButton btnVolver = crearBoton("Volver Iniciar Sesión");

            pBotones.add(btnRegistrar);
            pBotones.add(btnVolver);

            gbc.gridy = 7;
            gbc.insets = new Insets(25, 8, 8, 8);
            add(pBotones, gbc);

            // ACCIÓN: VOLVER AL LOGIN
            btnVolver.addActionListener(e -> cardLayout.show(contenedorDerecho, "VISTA_LOGIN"));

            // ACCIÓN: REGISTRAR USUARIO (CONEXIÓN A BASE DE DATOS CORREGIDA)
            btnRegistrar.addActionListener(e -> {
                String correo = txtCorreo.getText();
                String pass = new String(txtPass.getPassword());
                String userName = txtNombre.getText();

                // Verifica que los campos no esten vacios
                if (correo.isEmpty() || pass.isEmpty() || userName.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "Debes ingresar correo y contraseña y Nombre", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Verifica que los campos sean validos correo
                if (!correo.matches(EMAIL_REGEX)) {
                    JOptionPane.showMessageDialog(Login.this, "Su correo es inválido", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Verifica que el campo sean valido de contaseña
                if (pass.isEmpty()
                        || pass.length() < 8) {
                    JOptionPane.showMessageDialog(Login.this, "Su contraseña es inválida", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Agregamos un valor aleatrio y lo convertimos a String para que sea mas facil
                // su uso en las imagenes
                Random rand = new Random();
                int randomValue = rand.nextInt(5);
                String randomString = String.valueOf(randomValue);

                Consultas consultas = new Consultas();
                consultas.registerUser(correo, pass, userName, randomString);

                JOptionPane.showMessageDialog(Login.this, "Registrado",
                        "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

                // Limpiamos los campos
                txtNombre.setText("");
                txtCorreo.setText("");
                txtPass.setText("");

                // Lo regresamos al login automáticamente
                cardLayout.show(contenedorDerecho, "VISTA_LOGIN");
            });
        }
    }

    // MÉTODOS AUXILIARES PARA NO REPETIR CÓDIGO DE ESTILO
    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(COLOR_TEXTO);
        l.setFont(new Font("Arial", Font.PLAIN, 14));
        return l;
    }

    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(COLOR_IZQUIERDO);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
/*
 * CODIGO ECHO POR:
 * 1% YO alias "CONTERNICO845"
 * 99% IA alias "GEMINI"
 */
