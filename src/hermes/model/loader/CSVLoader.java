package hermes.model.loader;

import hermes.model.Model;
import hermes.model.Notification;
import hermes.model.dao.FactoryDAO;
import hermes.model.enums.Category;
import hermes.model.enums.Content;
import hermes.model.enums.Context;
import hermes.model.enums.Kid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.opencsv.CSVReader;

public class CSVLoader implements Loader {

	public void load(){

		try {
			// TODO: remove this generateRandomCSV method and load from a static csv file
//			generateRandomCSV(path);
			
			//load from CSV file
			InputStream in = CSVLoader.class.getResourceAsStream("/hermes.csv");
			BufferedReader breader = new BufferedReader(new InputStreamReader(in));		 
			CSVReader reader = new CSVReader(breader);
			String [] nextLine;
			while ((nextLine = reader.readNext()) != null) {
			    //creates notification with readed data
				Date sended;
				Date received;
				int content_id, context_id, category_id, kid_id;
				//id sended received content_id context_id, category_id, kid_id

				content_id = Integer.valueOf(nextLine[3]);
				context_id = Integer.valueOf(nextLine[4]);
				category_id = Integer.valueOf(nextLine[5]);
				kid_id = Integer.valueOf(nextLine[6]);
				sended = Date.from(LocalDate.parse(nextLine[1], Model.dateTimeFormatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
				received = Date.from(LocalDate.parse(nextLine[2], Model.dateTimeFormatter).atStartOfDay(ZoneId.systemDefault()).toInstant());


				Notification n = new Notification();
				n.setDateTimeSended(sended);
				n.setDateTimeReceived(received);
				n.setKid(Kid.values()[kid_id - 1]);
				n.setContent(Content.values()[content_id - 1]);
				n.setCategory(Category.values()[category_id - 1]);
				n.setContext(Context.values()[context_id - 1]);

				FactoryDAO.getNotificationDAO().persist(n);
			}
			reader.close();
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

//	private static void generateRandomCSV(String path) throws IOException{
//		CSVWriter writer = new CSVWriter(new FileWriter(path+"/"+"hermes.csv"));
//		Random random = new Random();
//		for (int i = 0; i < 50; i++){
//			Notification n = new Notification();
//			n.setDateTimeSended(new GregorianCalendar(2013 + random.nextInt(3),
//											   random.nextInt(12),
//											   random.nextInt(28),
//											   random.nextInt(24),
//											   random.nextInt(60),
//											   random.nextInt(60)).getTime());
//			n.setDateTimeReceived(new GregorianCalendar(2013 + random.nextInt(3),
//											   random.nextInt(12),
//											   random.nextInt(28),
//											   random.nextInt(24),
//											   random.nextInt(60),
//											   random.nextInt(60)).getTime());
//			n.setContent(Content.values()[random.nextInt(Content.values().length)]);
//			n.setContext(Context.values()[random.nextInt(Context.values().length)]);
//			n.setCategory(Category.values()[random.nextInt(Category.values().length)]);
//			n.setKid(Kid.values()[random.nextInt(Kid.values().length)]);
//			//save the notification in data file
//			String[] entries = (String.valueOf(n.getId()) + "#" + 
//				Model.dateTimeFormatter.format(n.getDateTimeSended().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+"#"+
//				Model.dateTimeFormatter.format(n.getDateTimeReceived().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+"#"+
//				String.valueOf(n.getContent().getId()) + "#" +
//				String.valueOf(n.getContext().getId()) + "#" +
//				String.valueOf(n.getCategory().getId()) + "#" + 
//				String.valueOf(n.getKid().getId()) + "#"
//		        ).split("#");
//		    writer.writeNext(entries);
//		}
//		writer.close();
//	}
}
