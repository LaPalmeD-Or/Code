package grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import connessione.Connection;

public class Op8Frame extends JFrame{
	public Op8Frame(Connection conn, JTextArea a) {
		c = conn;
		area = a;
		setSize(300,150);
		setTitle("Guadagno Mensile");
		setResizable(false);
		setLocationRelativeTo(null);
		add(mainPanel());
	}
	
	private JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(mesePanel());
		panel.add(annoPanel());
		panel.add(buttonPanel());
		return panel;
	}
	
	private JPanel annoPanel()
	{
		JPanel panel = new JPanel();
		JLabel lb2= new JLabel("Anno: ");
		anno = new JTextField(10);
		panel.add(lb2);
		panel.add(anno);
		return panel;
	}
	
	private JPanel mesePanel()
	{
		JPanel panel = new JPanel();
		JLabel lb1 = new JLabel("Mese: ");
		mese = new JTextField(10);
		panel.add(lb1);
		panel.add(mese);
		return panel;
	}
	
	private JPanel buttonPanel()
	{
		JPanel p = new JPanel();
		p.add(startButton());
		return p;
	}
	
	private JButton startButton() {
		JButton button = new JButton("Invio");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					String s = "";
					String m = getMese(mese.getText());
					String a = anno.getText();
					if(m==null) {
						area.append("  Mese non valido riprovare" + "\n");
						return;
					}
					double i = 0;
					String query = "SELECT SUM(Tot) as totM FROM Receipt WHERE Date like '" + a + "-" + m + "%'";
					rs = c.excecuteResultQuery(query);
					area.append("[OPERAZIONE 8: Stampare il guadagno totale del mese dell'anno scelto]\n");
					rs.next();
					s = rs.getString(1);
					area.append("  Incasso: ");
					if(s==null)
						area.append("0\n\n");
					else 
						area.append(s + "\n\n");
					dispose();
					} catch(SQLException a) {
						a.printStackTrace();
					}
			}
		}
	
		
		button.addActionListener(new ButtonListener());
		return button;

	}

	
	private String getMese(String str) {
		switch(str.toLowerCase()) {
			case "gennaio" : return "01";
			case "febbraio" : return "02";
			case "marzo" : return "03";
			case "aprile" : return "04";
			case "maggio" : return "05";
			case "giugno" : return "06";
			case "luglio" : return "07";
			case "agosto" : return "08";
			case "settembre" : return "09";
			case "ottobre" : return "10";
			case "novembre" : return "11";
			case "dicembre" : return "12";
			default: return null;
		}
	}
	
	private ResultSet rs;
	private JTextField mese, anno;
	private Connection c;
	private JTextArea area;
}
