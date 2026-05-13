// Código hecho por Giovanni
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Mapa extends JPanel {

    private ImageIcon backgroundImage;
    private ArrayList<Pin> listaPines; // Guardará la información de proporciones

    // Clase interna para almacenar la información de cada botón
    class Pin {
        int id;
        double proporcionX; // Porcentaje del ancho
        double proporcionY; // Porcentaje del alto
        JButton boton;

        public Pin(int id, double proporcionX, double proporcionY, JButton boton) {
            this.id = id;
            this.proporcionX = proporcionX;
            this.proporcionY = proporcionY;
            this.boton = boton;
        }
    }

    public Mapa() {
        // Carga la imagen de fondo
        backgroundImage = new ImageIcon(getClass().getResource("/Imagenes/MAPA/MapaCutMapeado.png"));
        setLayout(null);
        listaPines = new ArrayList<>();

        // LISTENER PARA REDIMENSIONAR (Detecta la barra lateral de PanelBase)
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                actualizarPosiciones();
            }
        });

        // ==========================================
        // TUS PINES MAPEADOS EXACTOS
        // ==========================================
        agregarPin(1, 0.6161971830985915 , 0.8048268625393494);
        agregarPin(2, 0.6390845070422535 , 0.8405036726128017);
        agregarPin(3, 0.6836854460093896 , 0.8153200419727177);
        agregarPin(4, 0.5223004694835681 , 0.8688352570828961);
        agregarPin(5, 0.6273474178403756 , 0.6169989506820567);
        agregarPin(6, 0.6807511737089202 , 0.6159496327387198);
        agregarPin(7, 0.7012910798122066 , 0.5802728226652676);
        agregarPin(8, 0.8356807511737089 , 0.5550891920251836);
        agregarPin(9, 0.6825117370892019 , 0.5141657922350472);
        agregarPin(10, 0.7482394366197183 , 0.40818467995802726);
        agregarPin(11, 0.8280516431924883 , 0.4228751311647429);
        agregarPin(12, 0.8257042253521126 , 0.37670514165792235);
        agregarPin(13, 0.715962441314554 , 0.34522560335781743);
        agregarPin(14, 0.6490610328638498 , 0.35047219307450156);
        agregarPin(15, 0.6408450704225352 , 0.32633788037775446);
        agregarPin(16, 0.6519953051643192 , 0.2906610703043022);
        agregarPin(17, 0.6572769953051644 , 0.2497376705141658);
        agregarPin(18, 0.704225352112676 , 0.26862539349422876);
        agregarPin(19, 0.7130281690140845 , 0.3473242392444911);
        agregarPin(20, 0.6009389671361502 , 0.05036726128016789);
        agregarPin(21, 0.5604460093896714 , 0.28226652675760755);
        agregarPin(22, 0.5610328638497653 , 0.3336831059811123);
        agregarPin(23, 0.5692488262910798 , 0.3945435466946485);
        agregarPin(24, 0.5856807511737089 , 0.521511017838405);
        agregarPin(25, 0.5856807511737089 , 0.6484784889821616);
        agregarPin(26, 0.5440140845070423 , 0.6442812172088143);
        agregarPin(27, 0.49295774647887325 , 0.6379853095487933);
        agregarPin(28, 0.5 , 0.5823714585519413);
        agregarPin(29, 0.5322769953051644 , 0.5089192025183631);
        agregarPin(30, 0.5545774647887324 , 0.4459601259181532);
        agregarPin(31, 0.5005868544600939 , 0.38929695697796435);
        agregarPin(32, 0.4653755868544601 , 0.41028331584470096);
        agregarPin(33, 0.5082159624413145 , 0.1825813221406086);
        agregarPin(34, 0.4242957746478873 , 0.20776495278069254);
        agregarPin(35, 0.3374413145539906 , 0.4910807974816369);
        agregarPin(36, 0.3609154929577465 , 0.6159496327387198);
        agregarPin(37, 0.31396713615023475 , 0.6747114375655824);
        agregarPin(38, 0.29107981220657275 , 0.44491080797481636);
        agregarPin(39, 0.2564553990610329 , 0.3252885624344176);
        agregarPin(40, 0.2535211267605634 , 0.4627492130115425);
        agregarPin(41, 0.23591549295774647 , 0.385099685204617);
        agregarPin(42, 0.18955399061032863 , 0.4249737670514166);
        agregarPin(43, 0.2112676056338028 , 0.5078698845750262);
        agregarPin(44, 0.20892018779342722 , 0.6747114375655824);
        agregarPin(45, 0.16490610328638497 , 0.4197271773347324);
        agregarPin(46, 0.13791079812206572 , 0.2759706190975866);
    }

    // Método que crea el botón invisible y su evento
    private void agregarPin(int numeroBoton, double proporcionX, double proporcionY) {
        JButton boton = new JButton();
        
        // Hacemos el botón completamente invisible para que se vea el pin de la imagen
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false); // Ponlo en 'true' temporalmente si quieres ver la hitbox de los botones
        
        // Evento de confirmación visual
        boton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Has presionado el pin número: " + numeroBoton, 
                "Información del Mapa", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        add(boton);
        // Guardamos el botón en la lista para recalcularlo cuando se mueva la ventana
        listaPines.add(new Pin(numeroBoton, proporcionX, proporcionY, boton));
        
        if(getWidth() > 0) {
            actualizarPosiciones();
        }
    }

    // Método que recalcula los píxeles cuando abres/cierras la barra lateral
    private void actualizarPosiciones() {
        int anchoActual = getWidth();
        int altoActual = getHeight();
        
        int anchoBoton = 30; // Tamaño del área clicable
        int altoBoton = 30;

        for (Pin p : listaPines) {
            int xPixel = (int) (anchoActual * p.proporcionX) - (anchoBoton / 2);
            int yPixel = (int) (altoActual * p.proporcionY) - (altoBoton / 2);
            
            p.boton.setBounds(xPixel, yPixel, anchoBoton, altoBoton);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Dibuja el fondo adaptándose al tamaño del JPanel
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}