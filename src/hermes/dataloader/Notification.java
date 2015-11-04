package hermes.dataloader;

import java.util.Date;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;
import hermes.enums.Tag;

public class Notification {
	
	private static int instanceNumber = 1;
	
	private int id;
	private Kid kid;
    private Date dateTimeSended;
    private Date dateTimeReceived;
    private Content content;
    private Category category;
    private Context context;
    private Tag tag;
    
    public Notification(){
    	this.id = instanceNumber;
    	instanceNumber++;
    }
    
	public int getId() {
		return id;
	}
	
	public Kid getKid() {
		return kid;
	}
	public void setKid(Kid kid) {
		this.kid = kid;
	}
	public Date getDateTimeSended() {
		return dateTimeSended;
	}
	public void setDateTimeSended(Date fechaHoraEnvio) {
		this.dateTimeSended = fechaHoraEnvio;
	}
	public Date getDateTimeReceived() {
		return dateTimeReceived;
	}
	public void setDateTimeReceived(Date fechaHoraRecepcion) {
		this.dateTimeReceived = fechaHoraRecepcion;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
	public String toString(){
		return this.content.toString(); 
	}
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	} 

}
