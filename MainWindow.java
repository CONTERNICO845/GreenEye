//Codigo hecho por el God Giovanni Sandoval
import javax.swing.*;

class MyPanel extends PanelBase {

    public MyPanel(String name) { 
        super(name);

        //Para todas las ventanas, las agrega a un Cardlayout para poderlas intercambiar una a una
        Statistics estadisticasPanel = new Statistics();
        mainPanel.add(estadisticasPanel, "Estadisticas");

        Rewards rewardsPanel = new Rewards();
        mainPanel.add(rewardsPanel, "Rewards");

        About_Us aboutUsPanel = new About_Us();
        mainPanel.add(aboutUsPanel, "About Us");
    }
}

//Aqui inicializa todo
public class MainWindow {

    public static void main(String[] args) {
        JFrame window = new JFrame("Main window");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyPanel content = new MyPanel("Inicio");
        window.add(content);

        window.pack();
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
