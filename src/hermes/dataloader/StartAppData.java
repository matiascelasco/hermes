package hermes.dataloader;

import java.util.ArrayList;
import java.util.Date;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;

public class StartAppData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Notification> list = new ArrayList<Notification>();
		
		Notification n1 = new Notification();		
		n1.setCategory(Category.PREDETERMINADA);
		n1.setContent(Content.ENTUSIASMADO);
		n1.setDateTimeSended(new Date());
		n1.setDateTimeReceived(new Date());
		n1.setKid(Kid.JIMMY);
		n1.setContext(Context.HOGAR);
		
		list.add(n1);
		
		Notification n2 = new Notification();
		n2.setCategory(Category.EMOCIONES);
		n2.setContent(Content.MOLESTO);
		n2.setDateTimeSended(new Date());
		n2.setDateTimeReceived(new Date());
		n2.setKid(Kid.BATMAN);
		n2.setContext(Context.HOGAR);
		
		list.add(n2);
		
		Notification n3 = new Notification();
		n3.setCategory(Category.EMOCIONES);
		n3.setContent(Content.MOLESTO);
		n3.setDateTimeSended(new Date());
		n3.setDateTimeReceived(new Date());
		n3.setKid(Kid.ROBIN);
		n3.setContext(Context.HOGAR);
		
		list.add(n3);
		
		Notification n4 = new Notification();
		n4.setCategory(Category.PREDETERMINADA);
		n4.setContent(Content.ALEGRE);
		n4.setDateTimeSended(new Date());
		n4.setDateTimeReceived(new Date());
		n4.setKid(Kid.ROBIN);
		n4.setContext(Context.HOGAR);
		
		list.add(n4);
		
		DBConnection.getDBConnection();
				
		for(Notification n: list){
			NotificationDAO.persist(n);
			System.out.println(String.valueOf(n.getId()));
		}
		
		
	}

}
