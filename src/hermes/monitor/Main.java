package hermes.monitor;

import java.io.IOException;

import hermes.model.Model;
import hermes.monitor.view.View;

public class Main {
	
    public static void main(String[] args) {
		try {
			Model model = new Model();
			View view = new View(model);
			Controller controller = new Controller(model, view);
			controller.start();
		}
		catch (IOException | RuntimeException e) {
			e.printStackTrace();
		}
    }
}