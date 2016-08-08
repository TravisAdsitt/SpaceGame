
public class GameObject {
	protected String id;
	protected Object owner;
	protected NameGenerator names;
	
	public GameObject(){
		id = "";
		owner = null;
		names = new NameGenerator();
	}
	
	public String getId(){
		return id;
	}
	public Object getOwner(){
		return owner;
	}
}
