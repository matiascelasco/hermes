package hermes.model.enums;

public enum Category {
	EMOCIONES("Emociones"), 
	PREDETERMINADA("Predeterminada");
	
	
	private String name;
	
	Category(String name){
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
