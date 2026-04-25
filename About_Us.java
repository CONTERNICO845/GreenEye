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
    static final String[] names = {"Giovanni", "Manuel", "Geovani", "Isacc", "Victor"};

    //Texto sobre nosotros
    static final int MAX_JLABEL_SIZE = 500;
    String muchoTexto = "<html><p style='width:" + MAX_JLABEL_SIZE + "px;'>" +
                    " Somos un grupo de cinco desarrolladores e innovadores unidos por la convicción de que la tecnología avanzada, cuando se aplica con inteligencia y propósito, puede cambiar hábitos de consumo a nivel masivo. No solo estamos creando una aplicación; estamos diseñando una herramienta que empodera a cada usuario para tomar decisiones responsables de manera intuitiva y precisa.\n" + //
                    "\n" + 
                    "Creemos en un mañana donde el reciclaje ya no sea una duda, sino un hábito sencillo facilitado por la inteligencia artificial. Nuestro compromiso es transformar la complejidad de la clasificación de residuos en una experiencia simple, rápida y efectiva para todos. Bienvenidos a la próxima generación de la sostenibilidad." +
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

