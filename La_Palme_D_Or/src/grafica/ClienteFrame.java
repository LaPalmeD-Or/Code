package grafica;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridLayout;
import connessione.Connection;

public class ClienteFrame extends JFrame{
	public ClienteFrame(Connection conn, JTextArea area)
	{
		a = area;
		c = conn;
		setSize(400, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		add(mainPanel());
		setTitle("Nuovo Utente");
	}
	
	private JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		panel.add(cfPanel());
		panel.add(nomePanel());
		panel.add(cognomePanel());
		panel.add(numeroPanel());
		panel.add(buttonPanel());
		return panel;
	}
	
	private JLabel label(String str) {
		JLabel label = new JLabel(str);
		return label;
	}
	
	private JTextField fieldCF() {
		fcf = new JTextField(17);
		return fcf;
	}
	
	private JPanel cfPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tCF: "));
		panel.add(fieldCF());
		return panel;
	}
	
	private JTextField fieldNome() {
		fnome = new JTextField(10);
		return fnome;
	}
	
	private JPanel nomePanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tNome: "));
		panel.add(fieldNome());
		return panel;
	}
	
	private JTextField fieldCognome() {
		fcognome = new JTextField(13);
		return fcognome;
	}
	
	private JPanel cognomePanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tCognome: "));
		panel.add(fieldCognome());
		return panel;
	}
	
	private JPanel numeroPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tNumeroTelefono: "));
		panel.add(fieldNumero());
		return panel;
	}
	
	private JTextField fieldNumero() {
		fnumero = new JTextField(13);
		return fnumero;
	}
	
	private JButton button() {
		invio = new JButton("Invio");
		
		class InvioButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
					dispose();
					s = getWaiterCF();
					String query = "INSERT INTO Customer(CF_customer, Name, Surname, CF_waiter)"
							+ "VALUES ('" + fcf.getText() + "', '" + fnome.getText() + "', '" + 
							fcognome.getText() + "', '" + s + "')";
					c.executeNoResultQuery(query);
					a.append(getCustomer());
					query = "INSERT INTO PhoneNumber(Number, CF_customer) "+
					         "VALUES (" + fnumero.getText() + ", '" + fcf.getText() + "')";
					c.executeNoResultQuery(query);
					query = "SELECT Number FROM PhoneNumber WHERE CF_customer='" + fcf.getText() + "'";
					rs = c.excecuteResultQuery(query);
					try {
						rs.next();
						a.append("NumeroTelefono: " + rs.getString("Number") + "\n");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			}
		}
		
		invio.addActionListener(new InvioButtonListener());
		return invio;
	}
	
	private JPanel buttonPanel(){
		JPanel panel = new JPanel();
		panel.add(button());
		return panel;
	}
	
	private String getWaiterCF() {
		try {
			String query = "SELECT DISTINCT CF_waiter FROM Waiter";
			rs = c.excecuteResultQuery(query);
			ArrayList<String> s = new ArrayList<String>();
			while(rs.next()) 
				s.add(rs.getString("CF_waiter")); 
			Random r = new Random();
			return s.get(r.nextInt(s.size()-1));
		}catch(SQLException e) {
			System.out.print(e.getMessage());
			return null;
		}	
	}
	
	public String getCustomer() {
		return "\n[OPERAZIONE 1:  Aggiungere un cliente]\n    CF: " + fcf.getText() + ",  Nome: " + fnome.getText() + ",  Cognome: " + fcognome.getText() + ", ";
	}
	
	private JTextArea a;
	private String s="";
	private ResultSet rs;
	private Connection c;
	private JButton invio;
	private JTextField fnome, fcognome, fcf, fnumero;
	
}
