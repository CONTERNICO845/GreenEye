
import javax.swing.*;

class MiPanel extends PanelBase {

    public MiPanel(String name) {
        super(name);
    }
}

public class MainWindow {

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Prueba02");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MiPanel contenido = new MiPanel("Mi proyecto");

        ventana.add(contenido);
        ventana.pack(); // Ajusta la ventana al tamaño del panel
        ventana.setLocationRelativeTo(null); // Centra la ventana
        ventana.setVisible(true);
    }
}