//Codigo hecho por el God Giovanni Sandoval

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

abstract class PayChartPanel extends JPanel {

    //Constantes
    public static final String[] names = {"Organica", "Plastico", "Papel", "Metal", "Vidrio","Dificil reciclaje"};

    //Variable para los valores de cada parte
    public double[] values;
    public Color[] colors;

    //Variables para la animacion
    private double progress = 0.0;
    private Timer timer;

    public PayChartPanel(double[] values, Color[] colors) {
        this.values = values;
        this.colors = colors;

        this.setPreferredSize(new Dimension(550, 500));

        //ActionPerformed para la animacion 
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 0.02; // Es lo que aumenta cada tick que pasa
                
                if (progress >= 1.0) {
                    progress = 1.0; 
                    timer.stop();   
                }
                
                repaint(); 
            }
        });
        timer.setInitialDelay(100);
        
        // Arrancamos la animación
        timer.start();
        darkMode();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Despues las obtendra de la base de datos
        Rectangle2D area = new Rectangle2D.Double(100, 30, 400, 400);

        double startAngle;
        double arcAngle;

        double total = 0;
        double currentValue = 0;

        for (int i = 0; i < names.length; i++) {
            total += values[i];
        }

        //Calcula cuanto se podra dibujar en cada frame
        double limit = 360.0 * progress;

        for (int i = 0; i < names.length; i++) {
            startAngle = (currentValue * 360 / total);
            arcAngle = (values[i] * 360 / total);

            if (startAngle >= limit) {
                break; 
            }

            if (startAngle + arcAngle > limit) {
                arcAngle = limit - startAngle;
            }

            g2d.setColor(colors[i]);
            g2d.fillArc((int) area.getX(), (int) area.getY(), (int) area.getWidth(), (int) area.getHeight(), (int) startAngle, (int) arcAngle);
            currentValue += values[i];
        }
    }

    public void darkMode(){
        if(Configuracion.esModoObscuro == false){
            this.setBackground(AppColors.COLOR_MAIN_BACKGROUND);
        } else if(Configuracion.esModoObscuro == true){
            this.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
        }
    }
}

abstract class PayChartInfo extends JPanel {

    int squareSize = 30;
    JLabel[] info;
    JPanel[] square;

    public PayChartInfo(double[] values, Color[] colors) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        info = new JLabel[PayChartPanel.names.length];
        square = new JPanel[PayChartPanel.names.length];

        for (int i = 0; i < PayChartPanel.names.length; i++) {

            //Fila donde estara todo
            JPanel line = new JPanel();
            line.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
            line.setOpaque(false);

            //Para el cuadrado de color
            JPanel sq = new JPanel();
            sq.setPreferredSize(new Dimension(squareSize, squareSize));
            sq.setBackground(MyPay.colors[i]);

            this.square[i] = sq;

            //Para el resto de la label
            JLabel inf = new JLabel(MyPay.names[i] + "    "+ values[i]);
            inf.setFont(new Font("SansSerif", Font.BOLD, 26));

            this.info[i] = inf;

            line.add(sq);
            line.add(inf);

            this.add(line);
            line.setVisible(true);

        }
        darkMode();
    }

    public void darkMode(){
        if(Configuracion.esModoObscuro == false){
            this.setBackground(AppColors.COLOR_MAIN_BACKGROUND);
            for(int i = 0; i < info.length; i++){
                square[i].setBorder(BorderFactory.createLineBorder(AppColors.COLOR_BLACK, 2));
            }
        } else if(Configuracion.esModoObscuro == true){
            this.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
            for(int i = 0; i < info.length; i++){
                square[i].setBorder(BorderFactory.createLineBorder(AppColors.COLOR_WHITE, 2));
                info[i].setForeground(AppColors.COLOR_DARK_BLUE);
            }
        }
    }
}

abstract class Podium extends JPanel{

    public static final int peopleAmount = 10;
    JPanel podium;

    public Podium() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(100));

        podium = new JPanel();
        podium.setLayout(new BoxLayout(podium, BoxLayout.Y_AXIS));
        podium.setMaximumSize(new Dimension(400, 515));

        //Llamamos al método que ahora nos devuelve los datos organizados
        String[][] topUsuarios = Consultas.getTopTenUsers();

        for (int i = 0; i < peopleAmount; i++) {
            
            // ponemos guiones para que la interfaz no se vea vacía.
            String UsersName = (topUsuarios[i][0] != null) ? topUsuarios[i][0] : "---";
            String usersPoints = (topUsuarios[i][1] != null) ? topUsuarios[i][1] : "0";

            JLabel place = new JLabel("#" + (i + 1));
            place.setFont(new Font("SansSerif", Font.BOLD, 26));
            place.setForeground(AppColors.COLOR_WHITE);

            //Usamos las variables
            JLabel name = new JLabel("  " + UsersName);
            name.setFont(new Font("SansSerif", Font.BOLD, 20));
            name.setForeground(AppColors.COLOR_WHITE);

            JLabel points = new JLabel("  " + usersPoints);
            points.setFont(new Font("SansSerif", Font.BOLD, 20));
            points.setForeground(AppColors.COLOR_WHITE);

            JPanel users = new JPanel();
            users.setLayout(new BoxLayout(users, BoxLayout.X_AXIS));
            users.setOpaque(false);
            
            users.add(place);
            users.add(name);
            users.add(points);

            podium.add(Box.createVerticalStrut(15));
            podium.add(users);
        }

        this.add(podium);

        darkMode();
    }

    public void darkMode(){
        if(Configuracion.esModoObscuro == false){
            this.setBackground(AppColors.COLOR_MAIN_BACKGROUND);
            podium.setBackground(AppColors.COLOR_MAIN_BUTTONS);
            podium.setBorder(BorderFactory.createLineBorder(AppColors.COLOR_WHITE, 5));
        } else if(Configuracion.esModoObscuro == true){
            this.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
            podium.setBackground(AppColors.COLOR_DARK_PANEL);
            podium.setBorder(BorderFactory.createLineBorder(AppColors.COLOR_DARK_BLUE, 5));
        }
    }
}