package GameStuff;

public class GameObject {
	private String type;
	private boolean isParent;
	
	
	
	/**
	 * Used to find the type of object this is
	 * 
	 * @return string type
	 */
	public String getMyObjectType(){
		return type;
	}
	/**
	 * Find out if the object is a parent
	 * 
	 * @return boolean true if is a parent object false otherwise
	 */
	public boolean isParent(){
		return isParent;
	}
}
