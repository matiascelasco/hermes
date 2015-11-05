package hermes.data.enums;

public enum Content {
	ENTUSIASMADO(1, "Entusiasmado"), 
	ALEGRE(2, "Alegre"), 
	MOLESTO(3, "Molesto");
	
	private int id;
	private String description;
	
	Content(int id, String description){
		this.id = id;
		this.description = description;
	}

	@Override
	public String toString() {
       return this.description;
   }
	
	public String getDescription(){
		return description;
	}
	
	public int getId(){
		return id;
	}
}
