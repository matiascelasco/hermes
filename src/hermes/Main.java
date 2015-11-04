package hermes;

import java.sql.SQLException;

import hermes.monitor.MonitorFrame;

public class Main {
	
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MonitorFrame frame;
				try {
					frame = new MonitorFrame();
					frame.pack();
					frame.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
}