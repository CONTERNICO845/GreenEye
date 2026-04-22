
import java.awt.*;
import javax.swing.*;

//Clase abstracta que solo puede crear hijos que seran las ventanas separadas
abstract class PanelBase extends JPanel {

    //Constantes para la ventana
    private final int ANCHO_PANTALLA = 1920;
    private final int ALTO_PANTALLA = 1080;

    //Constantes para abrir y cerrar la barra lateral
    private boolean extendido = false;
    private final int ANCHO_CERRADO = 50;
    private final int ANCHO_ABIERTO = 200;
    //private Timer timerAnimacion; por el momento

    //Constantes para los colores a usar
    private static final Color COLOR_VERDE_PRIMARIO = new Color(76, 175, 80);
    private static final Color COLOR_GRIS_PRIMARIO = new Color(230, 230, 230);

    //Variables para los botones
    Dimension BUTTON_HOME_SIZE = new Dimension(50, 50);
    Dimension NORMAL_BUTTON_SIZE = new Dimension(230, 50);
    Dimension RIGIDAREA_SIZE = new Dimension(0, 15);

    //Nombres de los botones de la barra lateral
    String[] nombreBotones = {"", "Inicio", "Estadisticas", "Recompensas", "Mapa", "Cuenta", "Configuracion", "About Us"};

    //Atributos que solo los hijos pueden usar
    protected JLabel titulo; //titulo de la pantalla
    protected JPanel panelLateral; //Panel lateral de opciones
    protected JPanel panelPrincipal; //resto de la pantalla, aqui se trabaja el interior de las ventanas
    protected JPanel panelSuperior; //Panel donde ira el titulo y perfil
    protected JButton botonPerfil; //Boton superior derecha del perfil

    public PanelBase(String textoTitulo) {
        this.setLayout(new BorderLayout());
        this.setSize(ANCHO_PANTALLA, ALTO_PANTALLA);

        //Configura el panel principal donde se trabajra cada ventana
        panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.WHITE);
        this.add(panelPrincipal, BorderLayout.CENTER);

        //Configura el panel superior del titulo y perfil
        panelSuperior = new JPanel();
        panelSuperior.setBackground(COLOR_GRIS_PRIMARIO);
        panelSuperior.setLayout(new BorderLayout());
        this.add(panelSuperior, BorderLayout.NORTH);

        //Configura un titulo para todas las ventanas
        titulo = new JLabel(textoTitulo, SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(new Color(45, 45, 45)); // Gris oscuro
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panelSuperior.add(titulo, BorderLayout.CENTER);

        //Configura el boton a la derecha del perfil
        botonPerfil = new JButton();
        botonPerfil.setOpaque(false);
        botonPerfil.setContentAreaFilled(false);
        //botonPerfil.setBorderPainted(false);     hasta que tenga una imagen sera false
        botonPerfil.setPreferredSize(new Dimension(60, 50));
        //Aqui debe de ir la ruta de la imagen
        panelSuperior.add(botonPerfil, BorderLayout.EAST);

        //Configura le panel lateral con las opciones
        panelLateral = new JPanel();
        //panelLateral.setLayout(new GridLayout(10, 1, 0, 5));
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setBackground(COLOR_GRIS_PRIMARIO);
        panelLateral.setPreferredSize(new Dimension(ANCHO_CERRADO, 0));
        this.add(panelLateral, BorderLayout.WEST);

        //Configura los botones del panel lateral
        for (int i = 0; i < nombreBotones.length; i++) {
            String nombre = nombreBotones[i];
            JButton boton = new JButton(nombre);
            boton.setAlignmentX(Component.LEFT_ALIGNMENT);

            //El boton que despliega el panel  ☰
            if (i == 0) {
                //Hace transparente al boton que extiende el panel
                boton.setOpaque(false);
                boton.setContentAreaFilled(false);
                boton.setBorderPainted(true);
                boton.setMinimumSize(BUTTON_HOME_SIZE);
                boton.setMaximumSize(BUTTON_HOME_SIZE);

                //Le agrega la imagen ☰ al boton
                ImageIcon icon = new ImageIcon(getClass().getResource("Imagenes/BOTONES/Boton_Home.png")); //Falta que primero lo busque y casi de que no eista
                boton.setIcon(new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                boton.setText(nombreBotones[i]);

                //Agrega evento para abrir o cerrar el panel
                boton.addActionListener(e -> {
                    alternalPanelLateral();
                });
            } else {
                //Configura el resto de botones laterales
                boton.setBackground(COLOR_VERDE_PRIMARIO);
                boton.setForeground(Color.WHITE);

                //Tamaño de los botones
                boton.setMinimumSize(NORMAL_BUTTON_SIZE);
                boton.setMaximumSize(NORMAL_BUTTON_SIZE);


                if (extendido == false) {
                    boton.setVisible(false); //Para que no se vea por defecto hasta que se extienda el panel
                }
            }
            //Agrega los botones al panel
            panelLateral.add(boton);

            //Agrega espacios entre los botones
            if (i < nombreBotones.length - 1) {
            Component space = Box.createRigidArea(RIGIDAREA_SIZE);
            space.setVisible(extendido); // Se oculta o muestra según el estado inicial
            panelLateral.add(space);
        }
        }
    }

    //Metodo que cambia entre el panel cerrado y abierto 
    //Esta bien padre apoco no
    private void alternalPanelLateral() {
        extendido = !extendido;
        if (extendido) {
            panelLateral.setPreferredSize(new Dimension(ANCHO_ABIERTO, 0));
        } else {
            panelLateral.setPreferredSize(new Dimension(ANCHO_CERRADO, 0));
        }

        //Bucle para cambiar visible o invisible botones laterales y los espacios en medio de cada uno
        Component[] panelLateralComponents = panelLateral.getComponents(); //Cuenta todos los componentes del panel 
        for (int i = 0; i < panelLateralComponents.length; i++) {
            if (i == 0) {
                panelLateralComponents[i].setVisible(true);
            } else {
                panelLateralComponents[i].setVisible(extendido);
            }
        }
        //Recalcula el diseño para repintar los botones
        panelLateral.revalidate();
        panelLateral.repaint();
    }
}