package hermes.data.enums;

public enum Kid {
	JIMMY(1, "Jimmy Olsen"), 
	BATMAN(2, "Bruce Wayne"), 
	ROBIN(3, "Dick Grayson");
	
	private int id;
	private String name;
	
	Kid(int id, String name){
		this.id = id;
		this.name= name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
}
