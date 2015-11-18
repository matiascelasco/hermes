package hermes.model.loader;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONTokener;

public class JsonFileLoader extends JsonLoader {
	
	@Override
	public void load() {
		InputStream in = JsonLoader.class.getResourceAsStream("/hermes.json");
		JSONTokener tokener = new JSONTokener(in);
		saveToDatabase(new JSONArray(tokener));
	}

}
