//Codigo hecho por el God Giovanni Sandoval
import javax.swing.*;

class MiPanel extends PanelBase {

    public MiPanel(String name) {
        super(name);

        //Para todas las ventanas, las agrega a un Cardlayout para poderlas intercambiar una a una
        Rewards panelRecompensas = new Rewards();
        panelPrincipal.add(panelRecompensas, "Rewards");

        About_Us panelAbout_Us = new About_Us();
        panelPrincipal.add(panelAbout_Us, "About Us");
    }
}

//Aqui i);nicializa todo
public class MainWindow {

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Main window");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MiPanel contenido = new MiPanel("Mi proyecto");

        ventana.add(contenido);
        ventana.pack(); // Ajusta la ventana al tamaño del panel
        ventana.setLocationRelativeTo(null); // Centra la ventana
        ventana.setVisible(true);
    }
}
