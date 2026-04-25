//Codigo hecho por el God Giovanni Sandoval
import javax.swing.*;

class MyPanel extends PanelBase {

    public MyPanel(String name) { 
        super(name);

        //Para todas las ventanas, las agrega a un Cardlayout para poderlas intercambiar una a una
        Rewards rewardsPanel = new Rewards();
        mainPanel.add(rewardsPanel, "Rewards");

        About_Us aboutUsPanel = new About_Us();
        mainPanel.add(aboutUsPanel, "About Us");
    }
}

//Aqui inicializa todo
public class MainWindow {

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Main window");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyPanel contenido = new MyPanel("Inicio");
        ventana.add(contenido);

        ventana.pack();
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
