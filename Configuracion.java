import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Configuracion extends JPanel implements ActionListener {

    // Definimos los botones como variables de clase
    private JButton btnOpcion1, btnOpcion2, btnOpcion3;
    public static boolean esModoObscuro = false; 

//JAJAJAJAJAJ no me dio risa 
    public Configuracion() {
        // Configuramos el diseño de ESTE panel
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.setBackground(Color.WHITE); 
        configurarPantalla();
    }

    public void configurarPantalla() {
        btnOpcion1 = new JButton("Cambiar Contraseña");
        btnOpcion2 = new JButton("Modo Oscuro");
        btnOpcion3 = new JButton("Eliminar Cuenta");

        // Colores y Estilo
        btnOpcion1.setBackground(new Color(76, 175, 80));
        btnOpcion1.setFocusPainted(false);
        btnOpcion1.setPreferredSize(new Dimension(200, 100));

        btnOpcion2.setBackground(new Color(60, 60, 60));
        btnOpcion2.setFocusPainted(false);
        btnOpcion2.setPreferredSize(new Dimension(200, 100));

        btnOpcion3.setBackground(new Color(211, 47, 47));
        btnOpcion3.setFocusPainted(false);
        btnOpcion3.setPreferredSize(new Dimension(200, 100));

        aplicacarColores();
        // Listeners
        btnOpcion1.addActionListener(this);
        btnOpcion2.addActionListener(this);
        btnOpcion3.addActionListener(this);
        
        // Agregamos los botones directamente al panel (this)
        this.add(btnOpcion1);
        this.add(btnOpcion2);
        this.add(btnOpcion3);
    }
    private void aplicacarColores(){
    if (esModoObscuro){
        this.setBackground(Color.BLACK);

        btnOpcion1.setBackground(AppColors.COLOR_GREEN_CLARO);
        btnOpcion2.setBackground(AppColors.COLOR_WHITE);
        btnOpcion3.setBackground(AppColors.COLOR_ROJO_CLARO); 

        btnOpcion1.setForeground(Color.BLACK);
        btnOpcion2.setForeground(Color.BLACK);
        btnOpcion3.setForeground(Color.BLACK);

    }else{
        this.setBackground(Color.WHITE);

        btnOpcion1.setBackground(new Color(76, 175, 80));
        btnOpcion2.setBackground(new Color(60, 60, 60));
        btnOpcion3.setBackground(new Color(211, 47, 47));
            
        btnOpcion1.setForeground(Color.WHITE);
        btnOpcion2.setForeground(Color.WHITE);
        btnOpcion3.setForeground(Color.WHITE);

    }
    }
//Pegamento que reconoce que se presionarón los botones
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnOpcion1) {
            System.out.println("Cambiando contraseña...");
            
        } else if (e.getSource() == btnOpcion2) {
            System.out.println("Activando Modo Oscuro...");
        esModoObscuro = !esModoObscuro;
        if (esModoObscuro) {
            btnOpcion2.setText("Modo Claro");
        }else{
            btnOpcion2.setText("Modo Obscuro");
        
        }
            aplicacarColores();
            this.revalidate();
            this.repaint();

            Container ancestor = SwingUtilities.getAncestorOfClass(PanelBase.class, this);
            if (ancestor instanceof PanelBase) {
                ((PanelBase) ancestor).darkMode(); 
            }

        
        } else if (e.getSource() == btnOpcion3) {
            System.out.println("Eliminando cuenta... ¡Cuidado!");
            Object [] opciones = {"No, no quiero eliminarlo", "Si, si quiero elimimarlo"};
            int respuesta = JOptionPane.showOptionDialog(this,"Esta apunto de elinar su cuenta. \n¿Esta Seguro?",
            "Ultima Advertencia", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null, opciones, opciones [0]);    
            if (respuesta == 1) { 
                // El índice 1 corresponde a la segunda opción del arreglo: "Sí, Sí quiero eliminarla"
                System.out.println("Eliminación de cuenta exitosa.");
                int resultado = deleteAccount();
                
                if (resultado > 0) {
                JOptionPane.showMessageDialog(this, 
                        "Su cuenta ha sido eliminada con éxito.", 
                        "Cuenta Eliminada", 
                        JOptionPane.INFORMATION_MESSAGE);
                       
                } else {
                    
                    JOptionPane.showMessageDialog(this, 
                            "No se pudo eliminar la cuenta. Intente más tarde.", 
                            "Error en el Servidor", 
                            JOptionPane.ERROR_MESSAGE);
                }        

                } else {
                    System.out.println("Eliminación Cancelada");
                    
                    JOptionPane.showMessageDialog(this, 
                        "Operación Cancelada",
                        "Cancelado", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    public static int deleteAccount(){
       String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null)
                return 0;

            // Se respeta tu instrucción: "loggedUserId" ya es reconocido por el sistema
            pstmt.setInt(1, Consultas.loggedUserId);

            // executeUpdate devuelve el número de filas afectadas
            int filasBorradas = pstmt.executeUpdate();

            if (filasBorradas > 0) {
                System.out.println("Cuenta eliminada exitosamente.");
                // Opcional: Reiniciar el ID de sesión tras borrar la cuenta
                Consultas.loggedUserId = -1;
            }

            return filasBorradas;

        } catch (Exception ex) {
            System.out.println("Error al intentar eliminar la cuenta: " + ex.getMessage());
            return 0;
        }
    }



    public static void main(String[] args){
        Configuracion ventana = new Configuracion();
        ventana.setVisible(true);
    }
}