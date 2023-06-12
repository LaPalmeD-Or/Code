package grafica;
import java.awt.*;
import javax.swing.*;
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
import java.util.Random;

import connessione.Connection;

public class Op2Frame extends JFrame{
	public Op2Frame(Connection conn, JTextArea a) {
		c = conn;
		area = a;
		setSize(400, 230);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Nuova Prenotazione");
		add(mainPanel());
	}
	
	private JPanel mainPanel() {
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(6,1));
		panel.add(cfPanel());
		panel.add(dataPanel());
		panel.add(oraPanel());
		panel.add(numPanel());
		panel.add(copertoPanel());
		panel.add(buttonPanel());
		return panel;
	}
	
	private JLabel label(String str) {
		JLabel label = new JLabel(str);
		return label;
	}
	
	private JTextField fieldCF() {
		tcf  = new JTextField(17);
		return tcf;
	}
	
	private JPanel cfPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tCF: "));
		panel.add(fieldCF());
		return panel;
	}
	
	private JTextField fieldData() {
		tdata  = new JTextField(10);
		return tdata;
	}
	
	private JPanel dataPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tData yyyy-mm-dd: "));
		panel.add(fieldData());
		return panel;
	}
	
	private JPanel oraPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tOra hh:mm: "));
		panel.add(fieldOra());
		return panel;
	}
	
	private JTextField fieldOra() {
		tora  = new JTextField(10);
		return tora;
	}
	
	private JTextField fieldNum() {
		tnum  = new JTextField(10);
		return tnum;
	}
	
	private JPanel numPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tNumero Persone: "));
		panel.add(fieldNum());
		return panel;
	}
	
	private JTextField fieldCoperto() {
		tcop  = new JTextField(10);
		tcop.setText("si");
		return tcop;
	}
	
	private JPanel copertoPanel() {
		JPanel panel = new JPanel();
		panel.add(label("\t\tCoperto (si/no): "));
		panel.add(fieldCoperto());
		return panel;
	}
	
	private JButton button() {
		invio = new JButton("Invio");
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				dispose();
				area.append("[OPERAZIONE 2: Aggiungere una prenotazione]\n");
				if(check()==null) {
					area.append("    Il cliente non è presente, aggiungerlo\n\n");
					return;
				}
				else {
					int d;
					if(tcop.getText().equals("si"))
						d=1;
					else
						d=0;
					Random r = new Random();
					String query = "INSERT INTO Reservation(Date, NumTable, Seated, InDoor, CF_customer)" + 
								"VALUES('" + tdata.getText() + " " + tora.getText() + "', " + (r.nextInt(13)+1) + ", '" 
								+ tnum.getText() + "', '" + d + "', '" + tcf.getText() + "')";
					c.executeNoResultQuery(query);
					area.append("    " + getReservation() + "\n\n");
				}
			}
		}
		
		invio.addActionListener(new ButtonListener());
		return invio;
	}
	
	private JPanel buttonPanel() {
		JPanel panel = new JPanel();
		panel.add(button());
		return panel;
	}
	
	private String check() {
		try {
			String cf = tcf.getText();
			String query = "SELECT CF_customer FROM Customer WHERE CF_customer='" + cf + "'";
			rs = c.excecuteResultQuery(query);
			if(rs.next()==false)
				return null;
			else
				return rs.getString("CF_customer");
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getReservation() {
		try {
			String s="";
			String query = "SELECT * FROM Reservation WHERE CF_customer='" + tcf.getText() + "' AND Date='"
					+ tdata.getText() + " " + tora.getText() + "'";
			rs = c.excecuteResultQuery(query);
			rs.next();
			rs.getString("Id_reservation");
			s+="Data: " + rs.getString("Date") + ", ";
			s+="Posti: " + rs.getString("Seated") + ", ";
			String c = rs.getString("InDoor");
			if(c.equals("1"))
				s+="Coperto, ";
			else
				s+="Esterno, ";
			s+="CF Cliente: " + rs.getString("CF_customer");
			return s;
		}catch(SQLException e) {
			System.out.print(e.getMessage());
			return null;
		}	
	}
	
	private ResultSet rs;
	private JButton invio;
	private Connection c;
	private JTextArea area;
	private JTextField tcf, tdata, tnum, tcop, tora;
}

