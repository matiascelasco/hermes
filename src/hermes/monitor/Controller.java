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
import java.util.List;

import org.json.JSONArray;
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

	public void prepare(){

		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(new Integer(Main.properties.getProperty("port"))), 0);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        server.createContext("/load-notifications", new DataReceivedListener());
        server.setExecutor(null); // creates a default executor
        server.start();

		view.addTagCreatedListener(new TagCreatedListener());
		view.addTagDeletedListener(new TagDeletedListener());
		view.addTagAssignedListener(new TagToggledOnNotificationListener());
		view.addTagRenamedListener(new TagRenamedListener());
		view.addFilterButtonPressedListener(new FilterButtonPressedListener());
		view.addClearButtonPressedListener(new ClearButtonPressedListener());
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
			view.clearFiltersForm();
			view.filterTable(view.getFilterToBeApplied());
			isFiltered = false;
		}

	}

	private class DataReceivedListener implements HttpHandler {

		private void response(HttpExchange t, int code, String msg) throws IOException{
			t.sendResponseHeaders(code, msg.length());
			OutputStream os = t.getResponseBody();
			os.write(msg.getBytes());
			os.close();
		}

		@Override
		public void handle(HttpExchange t) throws IOException {
			if (!t.getRequestMethod().equalsIgnoreCase("POST")){
				response(t, 400, "Bad request\n");
				return;
			}
			InputStream is = t.getRequestBody();
			JSONTokener tokener = new JSONTokener(is);
			JSONArray array = new JSONArray(tokener);
			try {
				JsonLoader.saveToDatabase(array);
				response(t, 200, String.format("OK. %d notifications were loaded\n", array.length()));
				if (isFiltered){
					view.showPopupMessage(
						String.format("Se recibieron %d nuevas notificaciones. " +
							"Presione el botón \"Mostrar todo\" para verlas.", 
							array.length()));
				} else {
					view.updateTable(model.getAllNotifications());
					view.updateComboBoxes();					
				}
			}
			catch (RuntimeException e){
				e.printStackTrace();
				response(t, 500, "Error\n");
			}
		}

	}
}
