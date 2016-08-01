import java.awt.Point;


public class Ship {

	private Player owner;
	private String id;
	private ShipStates shipState;
	private Object focusedObject;
	private Point coor;
	
	public Ship(Player owner, String id, Point coor, Object focusedObject){
		this.owner = owner;
		this.id = id;
		this.focusedObject = focusedObject;
		shipState = ShipStates.FLYING;
		this.coor = coor;
	}
	
	
	public Player getOwner(){
		return owner;
	}
	public String getId(){
		return id;
	}
	public Point getCoor(){
		return coor;
	}
	public void setCoor(Point coor){
		this.coor = coor;
	}
	public void setState(ShipStates shipState){
		this.shipState = shipState;
	}
	public void setFocusedObject(Object focusedObject){
		this.focusedObject = focusedObject;
		
		switch(focusedObject.getClass().getName()){
		case "Planet":
			shipState = ShipStates.ORBITING_PLANET;
			break;
		case "Sun":
			shipState = ShipStates.ORBITING_SUN;
			break;
		case "SolarSystem":
			shipState = ShipStates.FLYING;
			break;
		case "Sector":
			shipState = ShipStates.FLYING;
			break;
		}
	}
	public ShipStates getState(){
		return shipState;
	}
}
