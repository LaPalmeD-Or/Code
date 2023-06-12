package grafica;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import connessione.Connection;

public class StampaFrame extends JFrame{

	public StampaFrame(Connection conn, JTextArea a)
	{
		c = conn;
		try {
			c2 = new Connection();
			c2.createConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		area = a;
		setSize(400, 300);
		setResizable(false);
		add(mainPanel());
	}
	
	private JPanel mainPanel()
	{
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(6, 1));
		p.add(stampaClienti());
		p.add(stampaCamerieri());
		p.add(stampaCassieri());
		p.add(stampaChef());
		p.add(stampaPiatti());
		p.add(stampaPrenotazioni());
		return p;
	}
	
	private JPanel stampaClienti()
	{
		JPanel p = new JPanel();
		scli = new JButton("Stampa clienti");
		class Listener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					String s = "";
					String query = "SELECT * FROM Customer";
					rs = c.excecuteResultQuery(query);
					area.append("[OPERAZIONE STAMPA 1: Stampare tutti i clienti e le relative informazioni]\n");
					while(rs.next()) {
						s = "  CF: " + rs.getString("CF_customer") + ", Nome: " + rs.getString("Name") + ", Cognome: " + rs.getString("Surname") + "\n";
						area.append(s);
					}
					area.append("\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
			
		}
		scli.addActionListener(new Listener());
		p.add(scli);
		return p;
	}
	
	private JPanel stampaCassieri()
	{
		JPanel p = new JPanel();
		scass = new JButton("Stampa cassieri");
		class Listener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					String s = "";
					String query = "SELECT * FROM Cashier";
					rs = c.excecuteResultQuery(query);
					area.append("[OPERAZIONE STAMPA 3: Stampare tutti i cassieri e le relative informazioni]\n");
					while(rs.next()) {
						s = "  CF: " + rs.getString("CF_cashier") + ", Nome: " + rs.getString("Name") + ", Cognome: " + rs.getString("Surname") + "\n";
						area.append(s);
					}
					area.append("\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
			
		}
		scass.addActionListener(new Listener());
		p.add(scass);
		return p;
	}
	
	private JPanel stampaChef()
	{
		JPanel p = new JPanel();
		JButton s = new JButton("Stampa chef");
		class Listener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					String s = "";
					String query = "SELECT * FROM Chef";
					rs = c.excecuteResultQuery(query);
					area.append("[OPERAZIONE STAMPA 4: Stampare tutti gli chef e le relative informazioni]\n");
					while(rs.next()) {
						String cf = rs.getString("CF_chef");
						s = "  CF: " + cf + ", Nome: " + rs.getString("Name") + ", Cognome: " + rs.getString("Surname") + "Stelle Michelin: " + rs.getString("MichelinStar") + "\n";
						area.append(s);
						String q = "SELECT * FROM Certification WHERE CF_chef='" + cf + "'";
						ResultSet rs2;
						rs2 = c2.excecuteResultQuery(q);
						while(rs2.next()) {
							area.append("     [Certificati] Nome:" + rs2.getString("Name") + ", Istituzione: " + rs2.getString("Institution") + "\n");
							area.append("                    Descrizione: " + rs2.getString("Description") + "\n");
							area.append("                    Data: " + rs2.getString("Date_of_certification") + "\n");
						}
					}
					area.append("\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
			
		}
		s.addActionListener(new Listener());
		p.add(s);
		return p;
	}
	
	private JPanel stampaCamerieri()
	{
		JPanel p = new JPanel();
		scam = new JButton("Stampa camerieri");
		class Listener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					String s = "";
					String query = "SELECT * FROM Waiter";
					rs = c.excecuteResultQuery(query);
					area.append("[OPERAZIONE STAMPA 2: Stampare tutti i camerieri e le relative informazioni]\n");
					while(rs.next()) {
						s = "  CF: " + rs.getString("CF_Waiter") + ", Nome: " + rs.getString("Name") + ", Cognome: " + rs.getString("Surname") + ", NumeroOrdiniPresi: " + rs.getString("Number_orders") +"\n";
						area.append(s);
					}
					area.append("\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
			
		}
		scam.addActionListener(new Listener());
		p.add(scam);
		return p;
	}
	
	private JPanel stampaPiatti()
	{
		JPanel p = new JPanel();
		spi = new JButton("Stampa piatti");
		class Listener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					String s = "";
					String query = "SELECT * FROM Dish";
					rs = c.excecuteResultQuery(query);
					area.append("[OPERAZIONE STAMPA 4: Stampare tutti i piatti e le relative informazioni]\n");
					while(rs.next()) {
						s = "  Id: " + rs.getString("Id_dish") + ", Nome: " + rs.getString("Name") + ", Prezzo: " + rs.getString("Price") + ", Descrizione: " + rs.getString("Description") + ", Allergeni: " + rs.getString("Allergens") + "\n";
						area.append(s);
					}
					area.append("\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
			
		}
		spi.addActionListener(new Listener());
		p.add(spi);
		return p;
	}
	
	private JPanel stampaPrenotazioni()
	{
		JPanel p = new JPanel();
		spren = new JButton("Stampa prenotazioni");
		class Listener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					String s = "";
					String query = "SELECT * FROM Reservation";
					rs = c.excecuteResultQuery(query);
					area.append("[OPERAZIONE STAMPA 5: Stampare tutte le prenotazioni e le relative informazioni]\n");
					while(rs.next()) {
						s = "  Id: " + rs.getString("Id_reservation") + ", NumeroTavolo: " + rs.getString("NumTable") + ", Data: " + rs.getString("Date") + ", Posti: " + rs.getString("Seated")
							+ ", Coperto: ";
						if(rs.getString("InDoor").equals("1"))
							s+= "si";
						else
							s+= "no";
						s+=", CF cliente: " + rs.getString("CF_customer") + "\n";
						area.append(s);
					}
					area.append("\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
			
		}
		spren.addActionListener(new Listener());
		p.add(spren);
		return p;
	}
	
	private Connection c, c2;
	private ResultSet rs;
	private JButton scli, scass, scam, spi, spren;
	private JTextArea area;
}
