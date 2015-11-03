package hermes.dataloader;

import java.util.ArrayList;
import java.util.Date;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Kid;

public class Prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//creo una notificación
		ArrayList<Notification> list = new ArrayList<Notification>();
		
		Notification n1 = new Notification();
		n1.setCategory(Category.PREDETERMINADA);
		n1.setContent(Content.ENTUSIASMADO);
		n1.setFechaHoraEnvio(new Date());
		n1.setKid(Kid.JIMMY);
		
		list.add(n1);
		
		Notification n2 = new Notification();
		n2.setCategory(Category.EMOCIONES);
		n2.setContent(Content.MOLESTO);
		n2.setFechaHoraEnvio(new Date());
		n2.setKid(Kid.BATMAN);
		
		list.add(n2);
		
		Notification n3 = new Notification();
		n3.setCategory(Category.EMOCIONES);
		n3.setContent(Content.MOLESTO);
		n3.setFechaHoraEnvio(new Date());
		n3.setKid(Kid.ROBIN);
		
		list.add(n3);
		
		Notification n4 = new Notification();
		n4.setCategory(Category.PREDETERMINADA);
		n4.setContent(Content.ALEGRE);
		n4.setFechaHoraEnvio(new Date());
		n4.setKid(Kid.ROBIN);
		
		list.add(n4);
		
		for(Notification it: list){
			System.out.println(it.toString());
		}
		
		
	}

}
