import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

	public Login() {

		setTitle("Login");
		setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());

		JPanel cuadroLogin = new JPanel(new GridLayout(1, 2));
		cuadroLogin.setPreferredSize(new Dimension(1000, 450));

		cuadroLogin.add(new PanelIzquierdo());
		cuadroLogin.add(new PanelDerecho());

		add(cuadroLogin, new GridBagConstraints());
		getContentPane().setBackground(new Color(255, 255, 255));
	}

	// IZQUIERDA
	class PanelIzquierdo extends JPanel {
		public PanelIzquierdo() {
			setBackground(new Color(255, 0, 0));
			setLayout(new GridBagLayout());

			JLabel label = new JLabel("IZQUIERDA");
			label.setForeground(Color.WHITE);
			JButton btn = new JButton("Botón Izquierdo");
		}
	}

	// DERECHA
	class PanelDerecho extends JPanel {
		public PanelDerecho() {
			setBackground(new Color(0, 0, 0));
			setLayout(new GridBagLayout());

			JLabel label = new JLabel("DERECHA");
			label.setForeground(Color.WHITE);
			
			// Botón
			JButton btn = new JButton("Botón Derecho");

		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Login().setVisible(true);
		});
	}
}
