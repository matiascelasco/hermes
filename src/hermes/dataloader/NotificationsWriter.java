package hermes.dataloader;

public class NotificationsWriter {

	public static void main(String[] args) {						
				//TODO delete method				
			    try{
					DBConnection.getDBConnection();
			    }
			    catch(Exception ex){
			    	System.out.println("there was an error"+ex.getClass()+" "+ex.getMessage());
			    }
					//dump data to CSV file
					/*CSVWriter writer = new CSVWriter(new FileWriter("hermes.csv"));
										
					List<Notification> list = FactoryDAO.getNotificationDAO().findAll();
					
					for(Notification n: list){
					
					String[] entries = (String.valueOf(n.getId())+"#"+ 
									   formatter.format(n.getDateTimeSended().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+"#"+
									   formatter.format(n.getDateTimeReceived().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+"#"+
									   String.valueOf(n.getContent().getId())+"#"+
									   String.valueOf(n.getContext().getId())+"#"+
									   String.valueOf(n.getCategory().getId())+"#"+
									   String.valueOf(n.getKid().getId())+"#"
							           ).split("#"); 
					writer.writeNext(entries);					          					
					}
					writer.close();		 
				}*/
			/*	catch(Exception ex){
					System.out.println("error al intentar escribir registro CSV o al intentar el retrieve de los datos");
				}*/
			}	

}
