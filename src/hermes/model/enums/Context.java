package hermes.model.enums;

public enum Context {
	PISTA("Pista"), 
	HOGAR("Hogar"), 
	ESTABLO_TERAPIA("Establo-Terapia");
	
	private String name;
	
	Context(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	public int getId(){
		return ordinal() + 1;
	}
}
