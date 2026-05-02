//Codigo hecho por el God Giovanni Sandoval

import java.awt.*;
import javax.swing.*;

class MyPay extends PayChartPanel{

    double[] values;
    public static final Color[] colors = {Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED, Color.CYAN, Color.ORANGE};
    
    public MyPay(double[] values){
        super(values, colors);
        this.setPreferredSize(new Dimension(500, 500));
    }
}

class MyPayInfo extends PayChartInfo{

    public MyPayInfo(double[] values){
        super(values, MyPay.colors);
    }
}

class MyPodium extends Podium{

    public MyPodium(){

    }
}

public class Statistics extends JPanel {

    JPanel rightPanel;
    JPanel leftPanel;
    JButton share;
    JPanel buttonPanel;

    public Statistics(){
        this.setLayout(new GridLayout(1, 2));

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); 

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        //Panel que contendra el boton de share
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        //Boton para compartir
        share = new JButton("Compartir");
        share.setFocusPainted(false);
        share.addActionListener(e -> {
            //Falta ponerle accion
        });

        //Por el momento no los pide de la base de datos
        //Pide los valores a la base de datos
        double[] values = {40, 30, 20, 15, 10, 14};

        MyPay myGraphic = new MyPay(values);
        MyPayInfo myInfo = new MyPayInfo(values);
        MyPodium myPodium = new MyPodium();
        
        leftPanel.add(myGraphic);
        leftPanel.add(myInfo);

        //Agrega la parte de la derecha
        rightPanel.add(myPodium, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(share);

        this.add(leftPanel);
        this.add(rightPanel);

        DarkMode();
    }

    //Modo oscuro
    public void DarkMode(){
        if (Configuracion.esModoObscuro == false){
            this.setBackground(AppColors.COLOR_MAIN_BACKGROUND);
            leftPanel.setBackground(AppColors.COLOR_MAIN_BACKGROUND);
            rightPanel.setBackground(AppColors.COLOR_MAIN_BACKGROUND);
            share.setBackground(AppColors.COLOR_MAIN_BUTTONS);
            share.setForeground(AppColors.COLOR_WHITE);
        } else if (Configuracion.esModoObscuro == true){
            this.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
            leftPanel.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
            rightPanel.setBackground(AppColors.COLOR_DARK_BACKGROUND_2);
            share.setBackground(AppColors.COLOR_DARK_BUTTONS);
            share.setForeground(AppColors.COLOR_DARK_BLUE);
        }

        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        Statistics window = new Statistics();
        window.setVisible(true);
    }
}
