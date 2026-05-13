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
}
