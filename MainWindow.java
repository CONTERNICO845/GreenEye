//Codigo hecho por el God Giovanni Sandoval
import javax.swing.*;

class MyPanel extends PanelBase {

    public MyPanel(String name) { 
        super(name);
    }
}

//Aqui inicializa todo
public class MainWindow {

    public static void main(String[] args) {
        JFrame window = new JFrame("Main window");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
        
        MyPanel content = new MyPanel("Inicio");
        window.add(content);

        window.pack();
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
