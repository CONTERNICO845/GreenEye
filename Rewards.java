import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Rewards extends JPanel implements ActionListener {

    // Constantes para los valores de puntos y créditos
    private static final int PUNTOS_AGREGAR = 50;
    private static final int PUNTOS_CANJEAR = 30;

    private static final int CREDITOS_VERDE = 10;
    private static final int CREDITOS_AMARILLO = 5;
    private static final int CREDITOS_ROJO = 2;

    private int puntos;
    private int nivel;
    private int creditosSiiau;

    private JLabel labelEstado;
    private JButton btnAgregar;
    private JButton btnRojo, btnAmarillo, btnVerde;

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
        JPanel panelCuadros = new JPanel(new GridLayout(1, 3, 20, 0));
        panelCuadros.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        btnRojo = crearBoton("Canjear", Color.RED);
        btnAmarillo = crearBoton("Canjear", Color.YELLOW);
        btnVerde = crearBoton("Canjear", Color.GREEN);

        panelCuadros.add(btnRojo);
        panelCuadros.add(btnAmarillo);
        panelCuadros.add(btnVerde);

        add(panelCuadros, BorderLayout.CENTER);

        setSize(500, 300);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
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
            actualizarEstado();
        } else if (e.getSource() == btnRojo) {
            canjear(CREDITOS_ROJO, "Rojo");
        } else if (e.getSource() == btnAmarillo) {
            canjear(CREDITOS_AMARILLO, "Amarillo");
        } else if (e.getSource() == btnVerde) {
            canjear(CREDITOS_VERDE, "Verde");
        }
    }

    private void canjear(int creditos, String color) {
        if (puntos >= PUNTOS_CANJEAR) {
            puntos -= PUNTOS_CANJEAR;
            creditosSiiau += creditos;
            JOptionPane.showMessageDialog(this,
                "Canje exitoso en cuadro " + color + ": recibiste " + creditos + " créditos en SIIAU");
        } else {
            JOptionPane.showMessageDialog(this, "No tienes suficientes puntos");
        }
        actualizarEstado();
    }

    public static void main(String[] args) {
        Rewards ventana = new Rewards();
        ventana.setVisible(true);
    }
}
