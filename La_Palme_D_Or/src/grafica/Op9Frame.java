package grafica;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import connessione.Connection;

public class Op9Frame extends JFrame{
	public Op9Frame(Connection conn, JTextArea a) {
		c = conn;
		area = a;
		setSize(400, 100);
		setTitle("Piatti con Budget");
		setResizable(false);
		setLocationRelativeTo(null);
		add(mainPanel());
	}
	
	private JPanel mainPanel() {
		JPanel panel = new JPanel();
		JLabel lb = new JLabel("Prezzo massimo: ");
		costoField = new JTextField(10);
		panel.add(lb);
		panel.add(costoField);
		panel.add(startButton());
		return panel;
	}
	
	private JButton startButton() {
		JButton button = new JButton("Invio");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					String s = "";
					String query = "SELECT Name, Price, Description, Allergens FROM Dish WHERE Price <= '" + costoField.getText() + "'";
					rs = c.excecuteResultQuery(query);
					area.append("[OPERAZIONE 9: Stampare le informazioni dei piatti con un prezzo inferiore ad un importo]\n");
					while(rs.next()) {
						s+="  Nome: " + rs.getString("Name") + ", ";
						s+="Costo: " + rs.getString("Price") + "\n";
						s+="    Descrizione: " + rs.getString("Description") + "\n ";
						s+="    Allergeni: " + rs.getString("Allergens") + "\n\n";
						dispose();
					}
					area.append("  " + s + "\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
		}
		
		button.addActionListener(new ButtonListener());
		return button;

	}
	
	private ResultSet rs;
	private JTextField costoField;
	private Connection c;
	private JTextArea area;
}
