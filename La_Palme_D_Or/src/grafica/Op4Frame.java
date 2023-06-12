package grafica;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import connessione.Connection;

public class Op4Frame extends JFrame{
    public Op4Frame(Connection conn, JTextArea a) {
        c = conn;
        area = a;
        setSize(1000,800);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Registazione dell'ordine di un cliente");
        add(areeTxtPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
    }

    private JPanel areeTxtPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,2));
        p.add(menuAreaTxt());
        p.add(ordineClienteAreaTxt());
        
        return p;
    }
    
    private JPanel menuAreaTxt() {
        JPanel p = new JPanel();
        p.setBorder(new TitledBorder(new EtchedBorder(), "Menu"));
        JTextArea areaDish = new JTextArea(35,35);
        areaDish.setEditable(false);
        
        try {
            String s = "";
            String query = "SELECT * FROM Dish";
            rs = c.excecuteResultQuery(query);
            areaDish.append("Menu\n");
            while(rs.next()) {
                s = "  Id_Piatto: " + rs.getString("Id_dish") + "\n  Nome: " + rs.getString("Name") + ", Prezzo: " + rs.getString("Price") + "\n  Descrizione: " + rs.getString("Description") + "\n  Allergeni: " + rs.getString("Allergens") + "\n";
                areaDish.append(s + "\n");
            }
            } catch(SQLException a) {
                a.printStackTrace();
            }
        JScrollPane scroll = new JScrollPane(areaDish);
        p.add(scroll);
        
        return p;
    }
    
    private JPanel ordineClienteAreaTxt() {
        JPanel p = new JPanel();
        areaOrder = new JTextArea(35,35);
        p.setBorder(new TitledBorder(new EtchedBorder(), "Ordine del cliente"));
        areaOrder.setEditable(false);
        //si aggiorna man mano che il cameriere aggiunge un dish
        p.add(areaOrder);
        
        return p;
    }
    
    private JPanel inputFieldPanel() {
        JPanel p = new JPanel();
        p.add(inputPanel());
        p.add(descrizioneAreaTxt());
        
        return p;
    }
    
    private JPanel inputPanel() {
        JPanel p = new JPanel();
        JLabel l = new JLabel("Id del Piatto: ");
        sceltaDish = new JTextField(15);
        p.add(cfClienteBox());
        p.add(l);
        p.add(sceltaDish);
        
        return p;
    }
    
    private JPanel descrizioneAreaTxt() {
        JPanel p = new JPanel();
        p.setBorder(new TitledBorder(new EtchedBorder(), "Modifiche al piatto"));
        descrizioneDish = new JTextArea(2, 25);
        descrizioneDish.setEditable(true);
        JScrollPane scroll = new JScrollPane(descrizioneDish);
        p.add(scroll);
        
        return p;
    }

    private JPanel confermaButtons() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2,1));
        JButton b1 = new JButton("Aggiungi piatto");
        JButton b2 = new JButton("Completa ordinazione");
        
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		
                    String s = "";
                    String query = "SELECT Name FROM Dish WHERE Id_dish='" + sceltaDish.getText() + "'";
                    rs = c.excecuteResultQuery(query);
                    if(!rs.next())
                    	JOptionPane.showMessageDialog(null, "Id errato, riprova");
                    else {
                    	areaOrder.append(rs.getString("Name") + ", ");
                    	if(descrizioneDish.getText()!=null)
                    		areaOrder.append(descrizioneDish.getText());
                    	areaOrder.append("\n");
                    	ordiniList.add(sceltaDish.getText() + " " + descrizioneDish.getText());
                    	descrizioneDish.setText("");
                    }
                    } catch(SQLException a) {
                        a.printStackTrace();
                    }
            }
            
        });
        
        b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
                    String s = "";
                    String query = "SELECT CF_waiter FROM Customer WHERE CF_customer='" + selectedCF + "'";
                    rs = c.excecuteResultQuery(query);
                    rs.next();
                    s = rs.getString("CF_waiter");
                    query = "INSERT INTO CustomerOrder(CF_waiter, CF_customer)" +
                    		"VALUES ('" + s + "', '"  + selectedCF + "')";
                    c.executeNoResultQuery(query);
                    
                    int count = 0;
                    query = "SELECT * FROM CustomerOrder";
                    rs = c.excecuteResultQuery(query);
                    while(rs.next())
                    	count++;
                    query = "SELECT Id_customerOrder FROM CustomerOrder WHERE Id_customerOrder=" + count + " AND CF_waiter='" + s + "' AND CF_customer='" + selectedCF + "'";
                    rs = c.excecuteResultQuery(query);
                    rs.next();
                   
                    s = rs.getString("Id_customerOrder");
                    while(ordiniList.size()>0) {
                    	String str = ordiniList.get(0);
                    	ordiniList.remove(0);
                    	String id = str.substring(0, str.indexOf(" "));
                    	String des = str.substring(str.indexOf(" "));
                    	query = "INSERT INTO ComposedBy(Id_customerOrder, Id_dish, Description)" + 
                    			"VALUES('" + s + "', '" + id + "', '" + des + "')";
                    	c.executeNoResultQuery(query);
                    }
                    
                    query = "SELECT * FROM CustomerOrder WHERE Id_customerOrder='" + s + "'";
                    area.append("[OPERAZIONE 4: Un cameriere prende un ordine da un cliente]\n");
                    rs = c.excecuteResultQuery(query);
                    rs.next();
                    area.append("    Id_Ordine: " + rs.getString("Id_customerOrder") + ", Cliente: " + rs.getString("CF_customer") + "\n");
                    query = "SELECT * FROM ComposedBy WHERE Id_customerOrder='" + s + "'";
                    rs = c.excecuteResultQuery(query);
                    while(rs.next()) {
                    	area.append("        Id_Piatto: " + rs.getString("Id_dish") + ", Descrizione: " + rs.getString("Description") + "\n");
                    }
                    
                    query = "SELECT SUM(p.Price) FROM Dish AS p, ComposedBy AS c WHERE p.Id_dish=c.Id_dish AND Id_customerOrder=" + count;
                    rs = c.excecuteResultQuery(query);
                    rs.next();
                    int sum = (int)Double.parseDouble(rs.getString(1));
                    query = "INSERT INTO Receipt(Date, Tot, CF_cashier, CF_customer)"
                    		+ "VALUES ('2022-2-9', " + sum + ", 'SRRCRL01E31L245B', '" + selectedCF + "')";
                    c.executeNoResultQuery(query);
                    query = "SELECT COUNT(*) FROM Receipt";
                    rs = c.excecuteResultQuery(query);
                    rs.next();
                    int idr = (int)Double.parseDouble(rs.getString(1));
                    query = "SELECT * FROM Receipt WHERE SerialNumber_recipt='" + idr + "'";
                    
                    rs = c.excecuteResultQuery(query);
                    rs.next();
                    area.append("      [Scontrino] Data: " + rs.getString("Date") + ", Totale: " + rs.getString("Tot") + ", CF_cliente: " + rs.getString("CF_customer") + "\n");
                    area.append("\n");
                    } catch(SQLException a) {
                        a.printStackTrace();
                    }  
				
					
				
			}
        	
        });
        
        p.add(b1);
        p.add(b2);
        
        return p;
    }
    
    private JPanel createCenterPanel() {
    	JPanel p = new JPanel();
    	p.setLayout(new GridLayout(1,2));
    	p.add(inputFieldPanel());
    	p.add(confermaButtons());
    	
    	return p;
    }

    private JPanel cfClienteBox()
	{
		JPanel panel = new JPanel();
		JComboBox cB = new JComboBox();
		cB.setEditable(false);
		try {
			String s = "";
			String query = "SELECT CF_customer FROM Customer";
			rs = c.excecuteResultQuery(query);
			while(rs.next()) {
				cB.addItem(rs.getString("CF_customer"));
			}
		} catch(SQLException a) {
				a.printStackTrace();
		}
		
		class Listener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				selectedCF = (String) cB.getSelectedItem();
			}
			
		}
		cB.setSelectedIndex(0);
		cB.addActionListener(new Listener());
		panel.add(cB);
		
		return panel;
	}
    
    private ArrayList<String> ordiniList = new ArrayList<String>();
    private String selectedCF;
    private JTextField sceltaDish;
    private JTextArea descrizioneDish, areaOrder;
    private ResultSet rs;
    private Connection c;
    private JTextArea area;
}