package hermes.model.enums;

public enum Content {
	ENTUSIASMADO("Entusiasmado"), 
	ALEGRE("Alegre"), 
	MOLESTO("Molesto");
	
	private String name;
	
	Content(String name){
		this.name = name;
	}

	@Override
	public String toString() {
       return this.name;
   }
	
	public int getId(){
		return ordinal() + 1;
	}
}
