package grafica;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import connessione.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Op3Frame extends JFrame{
	public Op3Frame(Connection conn, JTextArea a){
		c = conn;
		area = a;
		setSize(400, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Nuovo Piatto");
		add(mainPanel());
		
	}
	
	private JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		panel.add(nomePanel());
		panel.add(desPanel());
		panel.add(prezzoPanel());
		panel.add(allPanel());
		panel.add(buttonPanel());
		return panel;
	}
	
	private JLabel label(String str) {
		JLabel label = new JLabel(str);
		return label;
	}
	
	private JTextField fieldNome() {
		tnome = new JTextField(10);
		return tnome;
	}
	
	private JPanel nomePanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tNome: "));
		panel.add(fieldNome());
		return panel;
	}
	
	private JTextField fieldDes() {
		tdes = new JTextField(10);
		return tdes;
	}
	
	private JPanel desPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tDescrizione: "));
		panel.add(fieldDes());
		return panel;
	}
	
	private JTextField fieldPrezzo() {
		tprezzo = new JTextField(10);
		return tprezzo;
	}
	
	private JPanel prezzoPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tPrezzo: "));
		panel.add(fieldPrezzo());
		return panel;
	}
	
	private JTextField fieldAll() {
		tall = new JTextField(10);
		return tall;
	}
	
	private JPanel allPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tAllergeni (opzionale): "));
		panel.add(fieldAll());
		return panel;
	}
	
	private JButton button() {
		button = new JButton("Invio");
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				dispose();
				area.append("[OPERAZIONE 3: Aggiungere un piatto]\n");
				String query = "INSERT INTO Dish(Name, Price, Description, Allergens)"
					       + "VALUES('" + tnome.getText() + "', '" + tprezzo.getText()
					       + "', '" + tdes.getText() + "', '" + tall.getText() + "')";
				c.executeNoResultQuery(query);
				area.append("    " + getDish() + "\n\n");
			}	
		}
		
		button.addActionListener(new ButtonListener());
		return button;
	}
	
	private JPanel buttonPanel() {
		JPanel panel = new JPanel();
		panel.add(button());
		return panel;
	}
	
	private String getDish() {
		try {
			String s="";
			String query = "SELECT * FROM Dish WHERE Name='" + tnome.getText() +
							"' AND Description='" + tdes.getText() + "'";
			rs = c.excecuteResultQuery(query);
			rs.next();
			s+="Id_Piatto: " + rs.getString("Id_dish") + ", ";
			s+="Nome: " + rs.getString("Name") + ", ";
			s+="Prezzo: " + rs.getString("Price") + ", ";
			s+="Descrizione: " + rs.getString("Description");
			s+=", Allergeni: " + rs.getString("Allergens");
			return s;
		}catch(SQLException e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
	
	private ResultSet rs;
	private JButton button;
	private JTextField tnome, tdes, tprezzo, tall;
	private Connection c;
	private JTextArea area;
}
