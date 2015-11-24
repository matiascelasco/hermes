package hermes.monitor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import hermes.model.Model;
import hermes.monitor.view.View;

public class Main {
	
	public static final Properties properties;
	static {
		properties = new Properties();
		InputStream inputStream = Main.class.getResourceAsStream("/config.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void main(String[] args) {
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
    }
}