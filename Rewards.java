import javax.swing.*;
import java.awt.event.*;

public class Rewards extends JFrame implements ActionListener {

    // Constantes para los valores de puntos
    private static final int PUNTOS_AGREGAR = 50;
    private static final int PUNTOS_CANJEAR = 30;

    private int puntos;
    private int nivel;

    private JLabel labelEstado;
    private JButton btnAgregar;
    private JButton btnCanjear;

    public Rewards() {
        puntos = 0;
        nivel = 1;

        setLayout(null);

        labelEstado = new JLabel("Puntos: " + puntos + " | Nivel: " + nivel);
        labelEstado.setBounds(50, 30, 250, 30);
        add(labelEstado);

        btnAgregar = new JButton("Agregar " + PUNTOS_AGREGAR + " puntos");
        btnAgregar.setBounds(50, 80, 200, 30);
        btnAgregar.addActionListener(this);
        add(btnAgregar);

        btnCanjear = new JButton("Canjear " + PUNTOS_CANJEAR + " puntos");
        btnCanjear.setBounds(50, 130, 200, 30);
        btnCanjear.addActionListener(this);
        add(btnCanjear);

        setTitle("Rewards");
        setBounds(0, 0, 350, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void verificarNivel() {
        if (puntos >= nivel * 100) {
            nivel++;
            JOptionPane.showMessageDialog(this,
                "¡Felicidades! Has alcanzado el nivel " + nivel);
        }
    }

    private void actualizarEstado() {
        labelEstado.setText("Puntos: " + puntos + " | Nivel: " + nivel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            puntos += PUNTOS_AGREGAR;
            verificarNivel();
            actualizarEstado();
        } else if (e.getSource() == btnCanjear) {
            if (puntos >= PUNTOS_CANJEAR) {
                puntos -= PUNTOS_CANJEAR;
                JOptionPane.showMessageDialog(this, "Canje exitoso de " + PUNTOS_CANJEAR + " puntos");
            } else {
                JOptionPane.showMessageDialog(this, "No tienes suficientes puntos");
            }
            actualizarEstado();
        }
    }

    public static void main(String[] args) {
        Rewards ventana = new Rewards();
        ventana.setVisible(true);
    }
}