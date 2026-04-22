import java.awt.*;
import javax.swing.*;

//Clase abstracta que solo puede crear hijos que seran las ventanas separadas
abstract class PanelBase extends JPanel {

    //Variables para la ventana
    private final int ANCHO_PANTALLA = 1920;
    private final int ALTO_PANTALLA = 1080;

    //Variables para abrir y cerrar la barra lateral
    private boolean extendido = false;
    private final int ANCHO_CERRADO = 50;
    private final int ANCHO_ABIERTO = 200;
    //private Timer timerAnimacion; por el momento

    //Nombres de los botones de la barra lateral
    String[] nombreBotones = {"", "Inicio", "Estadisticas", "Recompensas", "Cuenta", "Configuracion", "About Us"};

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
        panelSuperior.setBackground(new Color(230, 230, 230));
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
        panelLateral.setLayout(new GridLayout(10, 1, 0, 5));
        panelLateral.setBackground(new Color(200, 200, 200));
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
                boton.setBackground(new Color(46, 125, 50));
                boton.setForeground(Color.WHITE);

                if(extendido == false){
                    boton.setVisible(false); //Para que no se vea por defecto hasta que se extienda el panel
                }
            }
            //Agrega los botoners al panel lateral
            panelLateral.add(boton);
        }
    }

    //Metodo que cambia entre el panel cerrado y abierto 
    private void alternalPanelLateral(){
        extendido = !extendido;
        if(extendido){
            panelLateral.setPreferredSize(new Dimension(ANCHO_ABIERTO, 0));
        } else {
            panelLateral.setPreferredSize(new Dimension(ANCHO_CERRADO, 0));
        }

        //Bucle para cambiar visible o invisible botones laterales
        Component[] botonComponents = panelLateral.getComponents();
        for(int i = 0; i < nombreBotones.length; i++){
            if(i == 0){
                botonComponents[i].setVisible(true);
            } else {
                botonComponents[i].setVisible(extendido);
            }
        }
        //Recalcula el diseño para repintar los botones
        panelLateral.revalidate();
        panelLateral.repaint();
    }
}