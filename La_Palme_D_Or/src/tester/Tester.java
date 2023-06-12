package tester;
import java.sql.SQLException;

import javax.swing.JFrame;
import connessione.Connection;
import grafica.MainFrame;

public class Tester {

	public static void main(String[] args) {
		Connection c;
		try {
			c = new Connection();
			c.createConnection();
			JFrame mf = new MainFrame(c);
			mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mf.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
