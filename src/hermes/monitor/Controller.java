package hermes.monitor;

import hermes.model.Model;
import hermes.model.Notification;
import hermes.model.Tag;
import hermes.model.loader.JsonLoader;
import hermes.monitor.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class Controller {

	private View view;
	private Model model;
	boolean isFiltered = false;

	public Controller(Model model, View view){
		this.view = view;
		this.model = model;
	}

	public void start() throws IOException{

		Properties properties = new Properties();
		InputStream inputStream = Main.class.getResourceAsStream("/config.properties");
		properties.load(inputStream);
		
		HttpServer server;
		server = HttpServer.create(new InetSocketAddress(new Integer(properties.getProperty("port"))), 0);
        server.createContext("/load-notifications", new DataReceivedListener());
        server.setExecutor(null); // creates a default executor
        server.start();

		view.addTagCreatedListener(new TagCreatedListener());
		view.addTagDeletedListener(new TagDeletedListener());
		view.addTagAssignedListener(new TagToggledOnNotificationListener());
		view.addTagRenamedListener(new TagRenamedListener());
		view.addFilterButtonPressedListener(new FilterButtonPressedListener());
		view.addClearButtonPressedListener(new ClearButtonPressedListener());
		
		view.pack();
		view.showAll();
		view.setVisible(true);
		
	}

	private class TagCreatedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String newTagName = view.getNameForNewTag();
			if (model.validateTagName(newTagName) && !model.tagNameAlreadyExists(newTagName)){
				model.createNewTag(newTagName);
				view.updateComboBoxes();
			}
		}

	}

	private class TagDeletedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Tag tag = view.getTagToBeDeleted();
			model.deleteTag(tag);
			view.updateComboBoxes();
			view.updateTable(model.getAllNotifications());
		}

	}

	private class TagToggledOnNotificationListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Tag tag = view.getTagToBeToggled();
			List<Notification> notifications = view.getSelectedNotifications();
			model.toggleTag(tag, notifications);
			view.updateTable(model.getAllNotifications());
		}

	}

	private class TagRenamedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Tag tag = view.getTagToBeRenamed();
			String tagNewName = view.getNewNameToRenameTag();
			if (model.validateTagName(tagNewName) && !model.tagNameAlreadyExists(tagNewName)){
				model.renameTag(tag, tagNewName);
				view.updateComboBoxes();
				view.updateTable(model.getAllNotifications());
			}
		}

	}

	private class FilterButtonPressedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			view.filterTable(view.getFilterToBeApplied());
			isFiltered = true;
		}

	}

	private class ClearButtonPressedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			view.showAll();
			isFiltered = false;
		}

	}

	private class DataReceivedListener implements HttpHandler {

		private void response(HttpExchange t, int code, String msg) throws IOException{

			List<String> ss = new ArrayList<>(1);
			ss.add("application/json");
			ss.add("charset=utf-8");
			t.getResponseHeaders().put("Content-type", ss);
			String json = String.format("{\"msg\": \"%s\"}", msg);
			System.out.println(json);
			t.sendResponseHeaders(code, json.length());
			OutputStream os = t.getResponseBody();
			os.write(json.getBytes());
			os.close();
			
//			t.sendResponseHeaders(code, msg.length());
//			OutputStream os = t.getResponseBody();
//			
//			os.write(msg.getBytes());
//			os.close();
		}

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println(123123123);
			if (!t.getRequestMethod().equalsIgnoreCase("POST")){
				System.out.println(555555);
				response(t, 400, "Bad request");
				return;
			}
			InputStream is = t.getRequestBody();
			JSONTokener tokener = new JSONTokener(is);
			System.out.println(44);
			JSONObject object = new JSONObject(tokener);
			System.out.println(object);
			JSONArray array = object.getJSONArray("notifications");
			System.out.println(array.length());
			try {
				JsonLoader.saveToDatabase(array);
				response(t, 200, String.format("OK. %d notifications were loaded", array.length()));
				if (isFiltered){
					view.showPopupMessage(
						String.format("Se recibieron %d nuevas notificaciones. " +
							"Presione el botón \"Mostrar todo\" para verlas.", 
							array.length()));
				} else {
					view.updateTable(model.getAllNotifications());
					view.updateComboBoxes();
					view.showAll();
				}
			}
			catch (RuntimeException e){
				e.printStackTrace();
				response(t, 500, "Error");
			}
		}

	}
}
