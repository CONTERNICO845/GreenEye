import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Rewards extends JPanel implements ActionListener {

    private static final int PUNTOS_AGREGAR = 50;
    private static final int PUNTOS_CANJEAR = 30;
    private static final int PUNTOS_CANJEARAM = 60;
    private static final int PUNTOS_CANJEARVERD = 90;
    private static final int CREDITOS_VERDE = 10;
    private static final int TAMANO_IMAGEN = 250;

    private int puntos;
    private int nivel;
    private int creditosSiiau;

    private JLabel labelEstado;
    private JButton btnAgregar;
    private JButton btnRojo, btnAmarillo, btnVerde;

    // 👉 Guardamos referencia al panel central
    private JPanel panelCuadros;

    public Rewards() {
        puntos = 0;
        nivel = 1;
        creditosSiiau = 0;

        setLayout(new BorderLayout());

        // Estado arriba
        labelEstado = new JLabel("Puntos: " + puntos + " | Nivel: " + nivel + " | Créditos SIIAU: " + creditosSiiau, SwingConstants.CENTER);
        add(labelEstado, BorderLayout.NORTH);

        // Botón para agregar puntos
        btnAgregar = new JButton("Agregar " + PUNTOS_AGREGAR + " puntos");
        btnAgregar.addActionListener(this);
        add(btnAgregar, BorderLayout.SOUTH);

        // Panel central con los tres cuadros
        panelCuadros = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnRojo = crearBoton("Canjear", Color.RED, "imagenes/botorewards/fiesta-salchichas_2.jpg"); 
        btnRojo.setPreferredSize(new Dimension(300, 300));

        btnAmarillo = crearBoton("Canjear", Color.YELLOW, "imagenes/botorewards/Sukumbia.jpg");
        btnAmarillo.setPreferredSize(new Dimension(300, 300));

        btnVerde = crearBoton("Canjear", Color.GREEN, "imagenes/botorewards/Dormir.jpg");
        btnVerde.setPreferredSize(new Dimension(300, 300));

        panelCuadros.add(btnRojo);
        panelCuadros.add(btnAmarillo);
        panelCuadros.add(btnVerde);

        add(panelCuadros, BorderLayout.CENTER);

        setSize(500, 300);

        // Aplica colores iniciales
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            puntos += PUNTOS_AGREGAR;
            verificarNivel();
        } else if (e.getSource() == btnRojo) {
            if (puntos >= PUNTOS_CANJEAR) {
                puntos -= PUNTOS_CANJEAR;
                JOptionPane.showMessageDialog(this, "Hot dog con salchicha doble (♥ω♥*) ");
            } else {
                JOptionPane.showMessageDialog(this, "Te faltan " + (PUNTOS_CANJEAR - puntos) + " puntos para canjear.");
            }
        } else if (e.getSource() == btnAmarillo) {
            if (puntos >= PUNTOS_CANJEARAM) {
                puntos -= PUNTOS_CANJEARAM;
                JOptionPane.showMessageDialog(this, "Te ganaste dos hamburguesas");
            } else {
                JOptionPane.showMessageDialog(this, "Te faltan " + (PUNTOS_CANJEARAM - puntos) + " puntos para canjear.");
            }
        } else if (e.getSource() == btnVerde) {
            if (puntos >= PUNTOS_CANJEARVERD) {
                puntos -= PUNTOS_CANJEARVERD;
                creditosSiiau += CREDITOS_VERDE;
                JOptionPane.showMessageDialog(this, "Canjeaste en el botón verde. Recibiste " + CREDITOS_VERDE + " créditos.");
            } else {
                JOptionPane.showMessageDialog(this, "Te faltan " + (PUNTOS_CANJEARVERD - puntos) + " puntos para canjear.");
            }
        }
        actualizarEstado();
    }

    // 👉 Método DarkMode que cambia el área central
    public void DarkMode() {
    if (!Configuracion.esModoObscuro) {
        this.setBackground(AppColors.COLOR_MAIN_BACKGROUND);
        panelCuadros.setBackground(AppColors.COLOR_MAIN_BACKGROUND);

        // Texto en negro para modo claro
        labelEstado.setForeground(AppColors.COLOR_BLACK);
    } else {
        this.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
        panelCuadros.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);

        // Texto en blanco para modo oscuro
        labelEstado.setForeground(AppColors.COLOR_WHITE);
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

        // Aplica modo según Configuracion
        rewardsPanel.DarkMode();
    }
}