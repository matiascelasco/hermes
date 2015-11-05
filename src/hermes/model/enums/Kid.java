package hermes.model.enums;

public enum Kid {
	JIMMY("Jimmy Olsen"), 
	BATMAN("Bruce Wayne"), 
	ROBIN("Dick Grayson");
	
	private String name;
	
	Kid(String name){
		this.name= name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public int getId(){
		return ordinal() + 1;
	}
	
	/*
	 * TODO: in a future version this shouldn't be an enumerative, 
	 * but a persistent model instead
	 * */
}
