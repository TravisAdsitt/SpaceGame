
public enum Crafting {
	Base(1000, ObjectType.STRUCTURE),
	Ship(500, ObjectType.STRUCTURE);
	
	private int oreRequired;
	private ObjectType type;
	
	Crafting(int oreRequired,ObjectType type){
		this.oreRequired = oreRequired;
		this.type = type;
	}
	
	public boolean canIBuild(int oreIHave){
		return oreIHave>=oreRequired;
	}
	
	public ObjectType myType(){
		return type;
	}
	
	public int getRequiredOre(){
		return this.oreRequired;
	}
}
