package hermes.monitor;

import hermes.model.Model;
import hermes.monitor.view.View;

//TODO: si achico el frame la tabla se descuajeringa toda
//TODO: el ordenamiento de la columna de fecha no anda
//TODO: que los fields de fecha se actualizen automáticamente de manera tal que Desde nunca sea mayor que Hasta
//TODO: mencionar como comentario en la entrega la version de java (8) y el compilation level (1.6 en vez de 1.4)
//TODO: la relación entre tags y notifications debería ser many to many
//TODO: ver si se puede usar las fechas que hay en java.time en vez de las de utils en el combo box
//TODO: centralizar toda la mierda de la conversión de fechas a string en un solo punto
//TODO: no andan los filtrados por contenido ni contexto

public class Main {

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				try {
					Model model = new Model();
					View view = new View(model);
					Controller controller = new Controller(model, view);
					controller.prepare();
					view.pack();
					view.setVisible(true);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
            }
        });
    }
}