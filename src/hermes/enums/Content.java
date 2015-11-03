package hermes.enums;

public enum Content {
	ENTUSIASMADO("Entusiasmado"), 
	ALEGRE("Alegre"), 
	MOLESTO("Molesto");
	
	private String description;
	
	Content(String description){
		this.description = description;
	}

	@Override
	public String toString() {
	       return this.description;
	   }
}
