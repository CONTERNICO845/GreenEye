//Codigo hecho por el God Giovanni Sandoval

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

//Clase para crear la grafica circular
class BarChartPanel extends JPanel {

    static final int trsahTypes = 4;

    private List<Bar> bars = new ArrayList<>();

    //Clase para cada rebanada
    public static class Bar {

        double value;
        Color color;
        String name;

        public Bar(double value, Color color, String name) {
            this.value = value;
            this.color = color;
            this.name = name;
        }
    }
}

//Clase principal de estadisticas
public class Statistics extends JPanel {

    //Atributos de esta ventana
    JPanel rightPanel;

    public Statistics() {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(AppColors.COLOR_FONDO_MAIN);
    }

    public static void main(String[] args) {
        Statistics window = new Statistics();
        window.setVisible(true);
    }
}
