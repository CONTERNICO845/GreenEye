import java.awt.*;
import javax.swing.*;

public class cuenta extends JPanel  {

   JButton perfilButton = new JButton();
    protected JPanel topPanel; //Panel donde ira el titulo y perfil

    public cuenta() {

      String idImage = Consultas.getPhoto();
        String imageRoute = "Imagenes/FOTOS_DE_PERFIL/" + idImage + ".png";
        perfilButton = new JButton("");
        perfilButton.setOpaque(false);
        perfilButton.setContentAreaFilled(false);
        perfilButton.setBorderPainted(false);
        perfilButton.setFocusPainted(false);
        perfilButton.setPreferredSize(new Dimension(60, 50));
        ImageIcon userIcon = new ImageIcon(imageRoute);
        Image img = userIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        perfilButton.setIcon(new ImageIcon(img));
        topPanel.add(perfilButton, BorderLayout.EAST);
        

    }

    public static void main(String[] args) {
        cuenta cuenta2 = new cuenta();
        cuenta2.setVisible(true);
    }


}
