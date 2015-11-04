package hermes.enums;

public enum Context {
	PISTA(1, "Pista"), 
	HOGAR(2, "Hogar"), 
	ESTABLO_TERAPIA(3, "Establo-Terapia");
	
	private int id;
	private String description;
	
	Context(int id, String description){
		this.id = id;
		this.description = description;
	}
	
	@Override
	public String toString(){
		return this.description;
	}
	
	public String getDescription(){
		return description;
	}
	
	public int getId(){
		return id;
	}
}
