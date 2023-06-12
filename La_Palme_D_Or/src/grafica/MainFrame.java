package grafica;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import connessione.Connection;

public class MainFrame extends JFrame{
	
	public MainFrame(Connection conn)
	{
		c = conn;
		setSize(1000, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("La Palme D'Or");
		add(mainPanel());
	}
	
	private JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Operazioni"));
		panel.add(buttonPanel(), BorderLayout.WEST);
		panel.add(areaPanel(), BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel buttonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(12, 1));
		panel.add(button1());
		panel.add(button2());
		panel.add(button3());
		panel.add(button4());
		panel.add(button5());
		panel.add(button6());
		panel.add(button7());
		panel.add(button8());
		panel.add(button9());
		panel.add(button10());
		panel.add(opStampa());
		panel.add(buttonFine());
		return panel;
	}
	
	
	private JPanel areaPanel() {
		JPanel panel = new JPanel();
		ta = new JTextArea(32, 65);
		ta.setEditable(false);
		JScrollPane scroll = new JScrollPane(ta);
		panel.add(scroll);
		return panel;
	}
	
	private JButton button1() {
		op1 = new JButton("Operazione 1");
			
			class ButtonListener implements ActionListener
			{
				public void actionPerformed(ActionEvent e) {
					JFrame f = new ClienteFrame(c, ta);
					f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					f.setVisible(true);
				}
			}
			
		op1.addActionListener(new ButtonListener());
		return op1;
	}

	private JButton button2() {
		op2 = new JButton("Operazione 2");
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				JFrame f = new Op2Frame(c, ta);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setVisible(true);
			}
		}
		
		op2.addActionListener(new ButtonListener());
		return op2;
	}
	
	private JButton button3() {
		op3 = new JButton("Operazione 3");
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				JFrame f = new Op3Frame(c, ta);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setVisible(true);
			}
		}
		
		op3.addActionListener(new ButtonListener());
		return op3;
	}
	
	private JButton button4() {
		op4 = new JButton("Operazione 4");
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				JFrame f = new Op4Frame(c, ta);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setVisible(true);
			}
		}
		
		op4.addActionListener(new ButtonListener());
		return op4;
	}
	
	private JButton button5() {
		op5 = new JButton("Operazione 5");
	
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JFrame f = new Op5Frame(c, ta);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setVisible(true);
			}
			
		}
		
		op5.addActionListener(new ButtonListener());
		return op5;
	}
	
	private JButton button6() {
		op6 = new JButton("Operazione 6");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					String s = "";
					String query = "SELECT c.CF_chef, c.Name, c.Surname, COUNT(*) as nOrdini FROM Chef as c, MadeBy as m WHERE c.CF_chef = m.CF_chef GROUP BY CF_chef";
					rs = c.excecuteResultQuery(query);
					ta.append("[OPERAZIONE 6: Stampare il numero di ordini totali preparati da ogni Chef]\n");
					
					while(rs.next()) {
						s = "  CF: " + rs.getString("c.CF_chef") + ", Nome: " + rs.getString("c.Name") + ", Cognome: " + rs.getString("c.Surname") + "  Numero di ordini preparati: " + rs.getString("nOrdini") + "\n";
						ta.append(s + "");
					}
					ta.append("\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
		}
		
		op6.addActionListener(new ButtonListener());
		return op6;
	}
	
	private JButton button7() {
		op7 = new JButton("Operazione 7");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					String s = "";
					String query = "SELECT CF_waiter, Name, Surname, Number_orders FROM Waiter ORDER BY Number_orders DESC";
					rs = c.excecuteResultQuery(query);
					ta.append("[OPERAZIONE 7: Stampare in  ordine decrescente, per ogni cameriere, il numero di ordini presi]\n");
					while(rs.next()) {
						s = "  CF: " + rs.getString("CF_waiter") + ", Nome: " + rs.getString("Name") + ", Cognome: " + rs.getString("Surname") + "  Numero di ordini presi: " + rs.getString("Number_orders") + "\n";
						ta.append(s);
					}
					ta.append("\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
				
			}
		}
		
		op7.addActionListener(new ButtonListener());
		return op7;
	}
	
	private JButton button8() {
		op8 = new JButton("Operazione 8");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JFrame f = new Op8Frame(c, ta);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setVisible(true);
			}
		}
		
		op8.addActionListener(new ButtonListener());
		return op8;
	}
	
	private JButton button9() {
		op9 = new JButton("Operazione 9");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JFrame f = new Op9Frame(c, ta);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setVisible(true);
			}
		}
		
		op9.addActionListener(new ButtonListener());
		return op9;
	}

	private JButton button10() {
		finito = new JButton("Operazione 10");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) 
			{
				try {
					String s = "";
					String query = "SELECT c.CF_cashier, c.Name, c.Surname FROM Cashier AS c WHERE NOT EXISTS ("
							+ "SELECT * FROM Receipt AS r WHERE c.CF_cashier=r.CF_cashier)";
					rs = c.excecuteResultQuery(query);
					ta.append("[OPERAZIONE 10: Stampare il codice fiscale, nome e cognome di tutti i cassieri che non hanno stampato nessuno scontrino]\n");
					while(rs.next()) {
						s = "  CF: " + rs.getString("CF_cashier") + ", Nome: " + rs.getString("Name") + ", Cognome: " + rs.getString("Surname") + "\n";
						ta.append(s);
					}
					if(s.equals("")) {
						ta.append("    Tutti i cassieri hanno stampato almeno uno scontrino\n"); 
					}
					ta.append("\n");
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
		}
		
		finito.addActionListener(new ButtonListener());
		return finito;
	}
	
	private JButton opStampa() {
		finito = new JButton("Operazioni stampa");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) 
			{
				StampaFrame sf = new StampaFrame(c, ta);
				sf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				sf.setVisible(true);
			}
		}
		
		finito.addActionListener(new ButtonListener());
		return finito;
	}
	
	private JButton buttonFine() {
		finito = new JButton("Finito");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		}
		
		finito.addActionListener(new ButtonListener());
		return finito;
	}
	
	private ResultSet rs;
	private Connection c;
	private JButton op1, op2, op3, op4, op5, op6, op7, op8, op9, finito;
	private JTextArea ta;
}
