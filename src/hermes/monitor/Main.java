package hermes.monitor;

import hermes.model.Model;
import hermes.monitor.view.View;

//TODO: la relación entre tags y notifications debería ser many to many
//TODO: problemas con la tabla de notificacaiones al achivar el frame
//TODO: el ordenamiento de la columna de fecha no anda
//TODO: que los fields de fecha se actualizen automáticamente de manera tal que Desde nunca sea mayor que Hasta
//TODO: ver si se puede usar las fechas que hay en java.time en vez de las de utils en el combo box
//TODO: mencionar como comentario en la entrega la version de java (8) y el compilation level (1.6 en vez de 1.4)

public class Main {
	
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
				try {
					Model model = new Model();
					View view = new View(model);
					Controller controller = new Controller(model, view);
					controller.prepare();
					view.pack();
					view.setVisible(true);
				}
				catch (RuntimeException e) {
					e.printStackTrace();
				}
//            }
//        });
    }
}