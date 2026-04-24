//Codigo hecho por el God Giovanni Sandoval
import java.awt.*;
import javax.swing.*;

public class About_Us extends JPanel{

    //Atributos para esta pestaña
    JPanel upPanel;
    JPanel imagePanel;
    JPanel namePanel;
    JLabel imageLabel;
    JLabel textLabel;
    JLabel nameLabel;

    //Nombres de los creadores
    static final String[] names = {"Giovanni", "Manuel", "Victor", "Isacc", "Geovani"};

    //Texto sobre nosotros
    static final int MAX_JLABEL_SIZE = 500;
    String muchoTexto = "<html><p style='width:" + MAX_JLABEL_SIZE + "px;'>" +
                    "Somos una union estidiantil de los estados unidos mexicanos (UEEUM) " +
                    "</p></html>";

    public About_Us(){
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(AppColors.COLOR_FONDO_MAIN);

        //Configura el panel superior
        upPanel = new JPanel();
        upPanel.setLayout(new GridLayout(2, 1));
        upPanel.setOpaque(false);
        this.add(upPanel);

        //Configura el panel superior que contendra las imagenes
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 5));
        imagePanel.setOpaque(false);
        upPanel.add(imagePanel);

        //Configura el panel superior de los nombres
        namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 5));
        namePanel.setOpaque(false);
        upPanel.add(namePanel);

        //Configura el panel con los nombres
        for(int i = 0; i < names.length; i++){
            String name = names[i];
            JLabel nameJLabel = new JLabel(name, SwingConstants.CENTER);
            namePanel.add(nameJLabel);
        } 


        //Configuracion del JLabel
        textLabel = new JLabel(muchoTexto);
        textLabel.setBackground(Color.WHITE);
        textLabel.setOpaque(false);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(textLabel);

        //Falta agregar las imagenes de todos y ponerlas en imagePanel

    }

    public static void main(String[] args){
        About_Us window = new About_Us();
        window.setVisible(true);
    }
}

