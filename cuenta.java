import java.awt.*;
import javax.swing.*;

public class cuenta extends JPanel {

    // --- COLORES IMPORTADOS DE TU LOGIN ---
    private final Color COLOR_FONDO_MAIN = new Color(189, 236, 182);
    private final Color COLOR_IZQUIERDO = new Color(34, 60, 43);
    private final Color COLOR_DERECHO = new Color(43, 153, 99);
    private final Color COLOR_TEXTO = new Color(255, 255, 255);

    private Consultas consultas;
    protected JPanel topPanel;    
    protected JPanel centerPanel; 

    // Etiquetas para mostrar la información
    private JLabel lblVidrio, lblPlastico, lblMetal, lblPapel, lblOrganico, lblDificil;
    private JButton perfilButton;

    public cuenta() {
        consultas = new Consultas();

        // Aplicamos el color de fondo principal al panel general
        setBackground(COLOR_FONDO_MAIN);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ==========================================
        // 1. PANEL SUPERIOR (Foto, Puntos y Nivel)
        // ==========================================
        topPanel = new JPanel(new BorderLayout(15, 0)); 
        topPanel.setBackground(COLOR_DERECHO); // Fondo verde medio
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_IZQUIERDO, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Configuración de la foto de perfil
        String idImage = Consultas.getPhoto();
        if (idImage == null || idImage.isEmpty()) {
            idImage = "default"; 
        }
        
        String imageRoute = "Imagenes/FOTOS_DE_PERFIL/" + idImage + ".png";
        perfilButton = new JButton();
        perfilButton.setOpaque(false);
        perfilButton.setContentAreaFilled(false);
        perfilButton.setBorderPainted(false);
        perfilButton.setFocusPainted(false);
        perfilButton.setPreferredSize(new Dimension(80, 80));

        try {
            ImageIcon userIcon = new ImageIcon(imageRoute);
            Image img = userIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            perfilButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la foto de perfil: " + e.getMessage());
        }

        // Título y estadísticas principales
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false); // Transparente para que se vea el verde de atrás

        // Obtenemos el nombre del usuario desde la base de datos
        String nombreUsuario = consultas.getUserName();
        
        // Modificamos el JLabel para que muestre el nombre
        JLabel lblTitulo = new JLabel(nombreUsuario, SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_TEXTO); // Texto blanco

        String infoTexto = "Nivel: " + consultas.getNivel() + "  |  Puntos Totales: " + consultas.getPoints();
        JLabel lblSubtitulo = new JLabel(infoTexto, SwingConstants.LEFT);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(COLOR_TEXTO); // Texto blanco

        infoPanel.add(lblTitulo);
        infoPanel.add(lblSubtitulo);

        topPanel.add(perfilButton, BorderLayout.WEST);
        topPanel.add(infoPanel, BorderLayout.CENTER);

        // ==========================================
        // 2. PANEL CENTRAL (Estadísticas por Material)
        // ==========================================
        centerPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        centerPanel.setBackground(COLOR_DERECHO); // Fondo verde medio
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(COLOR_IZQUIERDO, 2), 
                    " Mis Estadísticas de Reciclaje ", 
                    0, 0, 
                    new Font("Arial", Font.BOLD, 14),
                    COLOR_TEXTO), // Título en blanco
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Inicializamos las etiquetas usando un método auxiliar para mantener el diseño limpio
        lblVidrio = crearLabelEstadistica("Vidrio: " + consultas.getGlassPoints() + " pts");
        lblPlastico = crearLabelEstadistica("Plástico: " + consultas.getPlasticPoints() + " pts");
        lblMetal = crearLabelEstadistica("Metal: " + consultas.getMetalPoints() + " pts");
        lblPapel = crearLabelEstadistica("Papel: " + consultas.getPaper() + " pts");
        lblOrganico = crearLabelEstadistica("Orgánico: " + consultas.getOrganic() + " pts");
        lblDificil = crearLabelEstadistica("Difícil Reciclaje: " + consultas.getHardToRecyclePoints() + " pts");

        centerPanel.add(lblVidrio);
        centerPanel.add(lblPlastico);
        centerPanel.add(lblMetal);
        centerPanel.add(lblPapel);
        centerPanel.add(lblOrganico);
        centerPanel.add(lblDificil);

        // ==========================================
        // 3. ENSAMBLAJE FINAL
        // ==========================================
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    // --- MÉTODO AUXILIAR PARA DISEÑO DE ETIQUETAS ---
    private JLabel crearLabelEstadistica(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setForeground(COLOR_TEXTO); // Letra blanca
        lbl.setOpaque(true);
        lbl.setBackground(COLOR_IZQUIERDO); // Fondo verde oscuro
        lbl.setBorder(BorderFactory.createLineBorder(COLOR_FONDO_MAIN, 2)); // Borde verde clarito
        return lbl;
    }
}