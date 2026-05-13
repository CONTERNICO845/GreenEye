// Código hecho por Giovanni
import java.awt.*;
import javax.swing.*;

public class Mapa extends JPanel {

    private ImageIcon backgroundImage;

    public Mapa() {
        // Carga la imagen de fondo desde la carpeta "IMAGENES/MapaCut.png"
        backgroundImage = new ImageIcon(getClass().getResource("/IMAGENES/MAPA/MapaCut.png"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja la imagen de fondo alrededor del JPanel
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        // Crea una ventana para mostrar el JPanel
        JFrame frame = new JFrame("Mapa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Mapa());
        frame.pack();
        frame.setVisible(true);
    }
}
