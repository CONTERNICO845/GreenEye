//Codigo hecho por el God Giovanni Sandoval
import java.awt.*;
import javax.swing.*;

//Clase abstracta que solo puede crear hijos que seran las ventanas separadas
abstract class PanelBase extends JPanel {

    //Constantes para la ventana
    public final int SCREEN_WIDTH = 1920;
    public final int SCREEN_HEIGHT = 1080;

    //Constantes para abrir y cerrar la barra lateral
    private boolean isExpanded = false;
    private final int CLOSED_WIDTH = 50;
    private final int OPEN_WIDTH = 200;
    //private Timer timerAnimacion; por el momento 
    
    //Variables para los botones
    Dimension BUTTON_HOME_SIZE = new Dimension(50, 50);
    Dimension NORMAL_BUTTON_SIZE = new Dimension(230, 50);
    Dimension RIGIDAREA_SIZE = new Dimension(0, 15);

    //Nombres de los botones de la barra lateral
    static final String[] buttonNames = {"", "Inicio", "Estadisticas", "Rewards", "Mapa", "Cuenta", "Configuracion", "About Us"};

    //Atributos que solo los hijos pueden usar
    protected JLabel title; //titulo de la pantalla
    protected JPanel sidePanel; //Panel lateral de opciones
    protected JPanel mainPanel; //resto de la pantalla, aqui se trabaja el interior de las ventanas
    protected JPanel topPanel; //Panel donde ira el titulo y perfil
    protected JButton perfilButton; //Boton superior derecha del perfil

    public PanelBase(String titleText) {
        this.setLayout(new BorderLayout());
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        //Configura el panel principal donde se trabajra cada ventana
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new CardLayout());
        this.add(mainPanel, BorderLayout.CENTER);

        //Configura el panel superior del titulo y perfil
        topPanel = new JPanel();
        topPanel.setBackground(AppColors.COLOR_FONDO_01);
        topPanel.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);

        //Configura un titulo para todas las ventanas
        title = new JLabel(titleText, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(255, 255, 255)); 
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        topPanel.add(title, BorderLayout.CENTER);

        //Configura el boton a la derecha del perfil
        perfilButton = new JButton();
        perfilButton.setOpaque(false);
        perfilButton.setContentAreaFilled(false);
        //botonPerfil.setBorderPainted(false);     hasta que tenga una imagen sera false
        perfilButton.setPreferredSize(new Dimension(60, 50));
        //Aqui debe de ir la ruta de la imagen
        topPanel.add(perfilButton, BorderLayout.EAST);

        //Configura le panel lateral con las opciones
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(AppColors.COLOR_FONDO_01);
        sidePanel.setPreferredSize(new Dimension(CLOSED_WIDTH, 0));
        this.add(sidePanel, BorderLayout.WEST);

        //Configura los botones del panel lateral
        for (int i = 0; i < buttonNames.length; i++) {
            String nombre = buttonNames[i];
            JButton boton = new JButton(nombre);
            boton.setAlignmentX(Component.LEFT_ALIGNMENT);

            //El boton que despliega el panel  ☰
            if (i == 0) {
                //Hace transparente al boton que extiende el panel
                boton.setOpaque(false);
                boton.setContentAreaFilled(false);
                boton.setBorderPainted(false);
                boton.setMinimumSize(BUTTON_HOME_SIZE);
                boton.setMaximumSize(BUTTON_HOME_SIZE);

                //Le agrega la imagen ☰ al boton
                ImageIcon icon = new ImageIcon(getClass().getResource("Imagenes/BOTONES/Boton_Home.png")); //Falta que primero lo busque y casi de que no eista
                boton.setIcon(new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                boton.setText(buttonNames[i]);

                //Agrega evento para abrir o cerrar el panel
                boton.addActionListener(e -> {
                    toggleSidebar();
                });
            } else {
                //Configura el resto de botones laterales
                boton.setBackground(AppColors.COLOR_BOTONES);
                boton.setForeground(Color.WHITE);

                //Tamaño de los botones
                boton.setMinimumSize(NORMAL_BUTTON_SIZE);
                boton.setMaximumSize(NORMAL_BUTTON_SIZE);

                //Añade eventos al presioar los botones
                boton.addActionListener(e -> {
                System.out.println("¡Botón presionado: " + nombre + "!");
                    choosePanel(nombre);
                });

                if (isExpanded == false) {
                    boton.setVisible(false); //Para que no se vea por defecto hasta que se extienda el panel
                }
            }
            //Agrega los botones al panel
            sidePanel.add(boton);

            //Agrega espacios entre los botones
            if (i < buttonNames.length - 1) {
            Component space = Box.createRigidArea(RIGIDAREA_SIZE);
            space.setVisible(isExpanded); // Se oculta o muestra según el estado inicial
            sidePanel.add(space);
        }
        }
    }

    //Metodo que cambia entre el panel cerrado y abierto 
    //Esta bien padre apoco no
    private void toggleSidebar() {
        isExpanded = !isExpanded;
        if (isExpanded) {
            sidePanel.setPreferredSize(new Dimension(OPEN_WIDTH, 0));
        } else {
            sidePanel.setPreferredSize(new Dimension(CLOSED_WIDTH, 0));
        }

        //Bucle para cambiar visible o invisible botones laterales y los espacios en medio de cada uno
        Component[] sidebarComponents = sidePanel.getComponents(); //Cuenta todos los componentes del panel 
        for (int i = 0; i < sidebarComponents.length; i++) {
            if (i == 0) {
                sidebarComponents[i].setVisible(true);
            } else {
                sidebarComponents[i].setVisible(isExpanded);
            }
        }
        //Recalcula el diseño para repintar los botones
        sidePanel.revalidate();
        sidePanel.repaint();
    }

    //Metodo que cambia entre ventanas en el panel principal
    private void choosePanel(String screenName) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        
        cl.show(mainPanel, screenName);

        String panelBaseName = screenName;
        title.setText(panelBaseName);
    }
}