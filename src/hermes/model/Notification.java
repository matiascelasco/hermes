package hermes.model;

import java.util.Comparator;
import java.util.Date;

import hermes.model.enums.Category;
import hermes.model.enums.Content;
import hermes.model.enums.Context;
import hermes.model.enums.Kid;

public class Notification {
	
	public static Comparator<Notification> byIdComparator = new Comparator<Notification>() {
		@Override
	    public int compare(Notification n1, Notification n2){
			Integer id1 = n1.getId();
			Integer id2 = n2.getId();
			return id1.compareTo(id2);
		}
	};
	
	private int id;
	private Kid kid;
    private Date dateTimeSended;
    private Date dateTimeReceived;
    private Content content;
    private Category category;
    private Context context;
    private Tag tag;
    
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
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

	public boolean hasTag(Tag tag) {
		if (this.tag == null){
			return tag == null;
		}
		return this.tag.getId() == tag.getId();
	}

}
