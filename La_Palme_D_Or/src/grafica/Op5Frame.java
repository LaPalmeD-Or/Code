package grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import connessione.Connection;

public class Op5Frame extends JFrame{
	public Op5Frame(Connection conn, JTextArea a) {
		c = conn;
		area = a;
		setSize(300,120);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Stelle Michelin");
		add(mainPanel());
	}
	
	private JPanel mainPanel() {
		JPanel panel = new JPanel();
		JLabel lb = new JLabel("Codice fiscale dello chef: ");
		cfField = new JTextField(20);
		panel.add(lb);
		panel.add(cfField);
		panel.add(startButton());
		
		return panel;
	}
	
	
	
	private JButton startButton() {
		button = new JButton("Invio");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
				String s = "";
				String query = "SELECT Name, Surname, MichelinStar FROM Chef WHERE MichelinStar != 0 AND CF_chef='" + cfField.getText() + "'";
				rs = c.excecuteResultQuery(query);
				area.append("[OPERAZIONE 5: Stampare il numero di Stelle Michelin di uno Chef]\n");
				rs.next();
				s+="Nome: " + rs.getString("Name") + ", ";
				s+="Cognome: " + rs.getString("Surname") + ", ";
				s+="Stelle Possedute: " + rs.getString("MichelinStar") + "\n";
				area.append("  " + s + "\n\n");
				dispose();
				} catch(SQLException a) {
					a.printStackTrace();
				}
				
			}
			
		}
		
		button.addActionListener(new ButtonListener());
		return button;
	}
	
	private JTextField cfField;
	private JButton button;
	private ResultSet rs;
	private Connection c;
	private JTextArea area;
}
