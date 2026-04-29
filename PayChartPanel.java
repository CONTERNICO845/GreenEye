//Codigo hecho por el God Giovanni Sandoval

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

abstract class PayChartPanel extends JPanel {

    //Constantes
    public static final String[] names = {"Organica", "Inorganica", "Papel", "Dificil reciclaje"};

    //Variable para los valores de cada parte
    public double[] values;
    public Color[] colors;

    public PayChartPanel(double[] values, Color[] colors) {
        this.values = values;
        this.colors = colors;

        this.setPreferredSize(new Dimension(550, 500));
        this.setBackground(AppColors.COLOR_FONDO_MAIN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        double startAngle;
        double arcAngle;

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Rectangle2D area = new Rectangle2D.Double(100, 30, 400, 400);

        double total = 0;
        double currentValue = 0;

        for (int i = 0; i < names.length; i++) {
            total += values[i];
        }

        for (int i = 0; i < names.length; i++) {
            startAngle = (currentValue * 360 / total);
            arcAngle = (values[i] * 360 / total);

            g2d.setColor(colors[i]);
            g2d.fillArc((int) area.getX(), (int) area.getY(), (int) area.getWidth(), (int) area.getHeight(), (int) startAngle, (int) arcAngle);
            currentValue += values[i];
        }
    }
}

abstract class PayChartInfo extends JPanel {

    int squareSize = 30;

    public PayChartInfo(double[] values, Color[] colors) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(AppColors.COLOR_FONDO_MAIN);

        for (int i = 0; i < PayChartPanel.names.length; i++) {

            //Fila donde estara todo
            JPanel line = new JPanel();
            line.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
            line.setOpaque(false);

            //Para el cuadrado de color
            JPanel square = new JPanel();
            square.setPreferredSize(new Dimension(squareSize, squareSize));
            square.setBackground(MyPay.colors[i]);

            //Para el resto de la label
            JLabel info = new JLabel(MyPay.names[i] + "    "+ values[i]);
            info.setFont(new Font("SansSerif", Font.BOLD, 26));

            line.add(square);
            line.add(info);

            this.add(line);
            line.setVisible(true);
        }
    }
}

abstract class Podium extends JPanel{

    public static final int peopleAmount = 10;

    public Podium(){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(AppColors.COLOR_FONDO_MAIN);

        this.add(Box.createVerticalStrut(100));

        JPanel podium = new JPanel();
        podium.setLayout(new BoxLayout(podium, BoxLayout.Y_AXIS));
        podium.setMaximumSize(new Dimension(300, 515));
        podium.setBackground(AppColors.COLOR_BOTONES);
        podium.setBorder(BorderFactory.createLineBorder(AppColors.COLOR_WHITE, 5));

        for(int i = 1; i <=peopleAmount; i++){

            JLabel place = new JLabel("#" + i);
            place.setFont(new Font("SansSerif", Font.BOLD, 26));
            place.setForeground(AppColors.COLOR_WHITE);
            place.setOpaque(false);

            //Label para el nombre de usuario, falta por la base de datos

            //Puntos igual faltan por la base de datos

            //Crea el panel de cada usuario
            JPanel users = new JPanel();
            users.setLayout(new BoxLayout(users, BoxLayout.X_AXIS));
            users.add(place);
            users.setOpaque(false);

            //Deja un espacio
            podium.add(Box.createVerticalStrut(15));

            podium.add(users);
            users.setVisible(true);
        }

        this.add(podium);
    }
}