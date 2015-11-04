package hermes.enums;

public enum Category {
	EMOCIONES(1, "Emociones"), 
	PREDETERMINADA(2, "Predeterminada");
	
	private int id;
	private String description;
	
	Category(int id, String description){
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
