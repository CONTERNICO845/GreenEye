import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.LocalDate;

public class Rewards extends JPanel implements ActionListener {

    private static final int PUNTOS_CANJEAR = 0;
    private static final int PUNTOS_CANJEARAM = 0;
    private static final int PUNTOS_CANJEARVERD = 0;
    private static final int CREDITOS_VERDE = 1;
    private static final int TAMANO_IMAGEN = 250;

    private int puntos;
    private int nivel;
    private int creditosSiiau;

    private JLabel labelEstado;
    private JButton btnRojo, btnAmarillo, btnVerde;

    private JPanel panelCuadros;
    private JPanel panelTicket;   // 👉 Panel con borde para ticket
    private JLabel lblCodigo, lblFecha, lblCaducidad, lblRecompensa;

    public Rewards() {
        puntos = 0;
        nivel = 1;
        creditosSiiau = 0;

        setLayout(new BorderLayout());

        // Estado arriba
        labelEstado = new JLabel("Puntos: " + puntos + " | Nivel: " + nivel + " | Créditos SIIAU: " + creditosSiiau, SwingConstants.CENTER);
        add(labelEstado, BorderLayout.NORTH);

        // Panel central con los tres cuadros
        panelCuadros = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnRojo = crearBoton("Canjear", Color.RED, "imagenes/botorewards/Aguafresca.png"); 
        btnRojo.setPreferredSize(new Dimension(300, 300));

        btnAmarillo = crearBoton("Canjear", Color.YELLOW, "imagenes/botorewards/Hotdog.jpg");
        btnAmarillo.setPreferredSize(new Dimension(300, 300));

        btnVerde = crearBoton("Canjear", Color.GREEN, "imagenes/botorewards/Combo.png");
        btnVerde.setPreferredSize(new Dimension(300, 300));

        panelCuadros.add(btnRojo);
        panelCuadros.add(btnAmarillo);
        panelCuadros.add(btnVerde);

        add(panelCuadros, BorderLayout.CENTER);

        // 👉 Panel Ticket con borde (parte izquierda)
        panelTicket = new JPanel(new GridLayout(4,1));
        panelTicket.setBorder(BorderFactory.createTitledBorder("Ticket de Canje"));
        lblCodigo = new JLabel("Código: ---", SwingConstants.CENTER);
        lblFecha = new JLabel("Fecha: ---", SwingConstants.CENTER);
        lblCaducidad = new JLabel("Caducidad: ---", SwingConstants.CENTER);
        lblRecompensa = new JLabel("Recompensa: ---", SwingConstants.CENTER);

        panelTicket.add(lblCodigo);
        panelTicket.add(lblFecha);
        panelTicket.add(lblCaducidad);
        panelTicket.add(lblRecompensa);

        // Inicialmente oculto
        panelTicket.setVisible(false);

        add(panelTicket, BorderLayout.WEST);

        setSize(700, 400);

        DarkMode();
    }

    private JButton crearBoton(String texto, Color color, String rutaImagen) {
        JButton boton = new JButton(texto);

        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image img = icono.getImage().getScaledInstance(TAMANO_IMAGEN, TAMANO_IMAGEN, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
            boton.setHorizontalTextPosition(SwingConstants.CENTER);
            boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        }

        boton.setBackground(color);
        boton.setOpaque(true);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.addActionListener(this);
        return boton;
    }

    private void verificarNivel() {
        if (puntos >= nivel * 100) {
            nivel++;
            JOptionPane.showMessageDialog(this,
                "¡Felicidades! Has alcanzado el nivel " + nivel);
        }
    }

    private void actualizarEstado() {
        labelEstado.setText("Puntos: " + puntos + " | Nivel: " + nivel + " | Créditos SIIAU: " + creditosSiiau);
    }

    // 👉 Genera ticket con código, fecha, caducidad y recompensa
    private void generarTicket(String recompensa) {
        String codigo = "TK-" + (int)(Math.random() * 900000 + 100000);
        LocalDate hoy = LocalDate.now();
        LocalDate caducidad = hoy.plusDays(7);

        lblCodigo.setText("Código: " + codigo);
        lblFecha.setText("Fecha: " + hoy);
        lblCaducidad.setText("Caducidad: " + caducidad);
        lblRecompensa.setText("Recompensa: " + recompensa);

        panelTicket.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRojo) {
            if (puntos >= PUNTOS_CANJEAR) {
                puntos -= PUNTOS_CANJEAR;
                JOptionPane.showMessageDialog(this, "Canjeaste un agua fresca mediana");
                generarTicket("Agua fresca mediana");
            } else {
                JOptionPane.showMessageDialog(this, "Te faltan " + (PUNTOS_CANJEAR - puntos) + " puntos para canjear.");
            }
        } else if (e.getSource() == btnAmarillo) {
            if (puntos >= PUNTOS_CANJEARAM) {
                puntos -= PUNTOS_CANJEARAM;
                JOptionPane.showMessageDialog(this, "Canjeaste un hot dog mediano");
                generarTicket("Hot dog mediano");
            } else {
                JOptionPane.showMessageDialog(this, "Te faltan " + (PUNTOS_CANJEARAM - puntos) + " puntos para canjear.");
            }
        } else if (e.getSource() == btnVerde) {
            if (puntos >= PUNTOS_CANJEARVERD) {
                puntos -= PUNTOS_CANJEARVERD;
                creditosSiiau += CREDITOS_VERDE;
                JOptionPane.showMessageDialog(this, "Canjeaste un combo big de Agua fresca y Hotdog junto a " + CREDITOS_VERDE + " crédito en siiau.");
                generarTicket("Combo big Agua fresca + Hotdog");
            } else {
                JOptionPane.showMessageDialog(this, "Te faltan " + (PUNTOS_CANJEARVERD - puntos) + " puntos para canjear.");
            }
        }
        verificarNivel();
        actualizarEstado();
    }

    public void DarkMode() {
        if (!Configuracion.esModoObscuro) {
            this.setBackground(AppColors.COLOR_MAIN_BACKGROUND);
            panelCuadros.setBackground(AppColors.COLOR_MAIN_BACKGROUND);
            panelTicket.setBackground(AppColors.COLOR_MAIN_BACKGROUND);

            labelEstado.setForeground(AppColors.COLOR_BLACK);
            lblCodigo.setForeground(AppColors.COLOR_BLACK);
            lblFecha.setForeground(AppColors.COLOR_BLACK);
            lblCaducidad.setForeground(AppColors.COLOR_BLACK);
            lblRecompensa.setForeground(AppColors.COLOR_BLACK);

            ((javax.swing.border.TitledBorder) panelTicket.getBorder()).setTitleColor(AppColors.COLOR_BLACK);

        } else {
            this.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
            panelCuadros.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
            panelTicket.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);

            labelEstado.setForeground(AppColors.COLOR_WHITE);
            lblCodigo.setForeground(AppColors.COLOR_WHITE);
            lblFecha.setForeground(AppColors.COLOR_WHITE);
            lblCaducidad.setForeground(AppColors.COLOR_WHITE);
            lblRecompensa.setForeground(AppColors.COLOR_WHITE);

            ((javax.swing.border.TitledBorder) panelTicket.getBorder()).setTitleColor(AppColors.COLOR_WHITE);
        }
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rewards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Rewards rewardsPanel = new Rewards();
        frame.add(rewardsPanel);
        frame.setVisible(true);
        rewardsPanel.DarkMode();
    }
}