import java.awt.Point;


public class Ship {

	private Player owner;
	private String id;
	private final int MAX_DEFAULT_HYDROGEN = 10000, MAX_DEFAULT_OXYGEN = 1000, MAX_DEFAULT_ORE = 1000;
	private int hydrogen, oxygen, ore, level, minePow, vacPow;
	private ShipStates shipState;
	private Object focusedObject;
	private Point coor, dest;
	
	public Ship(Player owner, String id, Point coor, Object focusedObject){
		this.owner = owner;
		this.id = id;
		this.focusedObject = focusedObject;
		this.coor = coor;
		dest = coor;
		shipState = ShipStates.IDLE;
		hydrogen = oxygen = ore = 0;
		level = 1;
	}
	
	public void addOre(int amount){
		ore += (amount + ore)>(MAX_DEFAULT_ORE*level)?(MAX_DEFAULT_ORE*level):amount;
	}
	public void addOxygen(int amount){
		oxygen += (amount + oxygen)>(MAX_DEFAULT_OXYGEN*level)?(MAX_DEFAULT_OXYGEN*level):amount;
	}
	public void addHydrogen(int amount){
		hydrogen += (amount + hydrogen)>(MAX_DEFAULT_HYDROGEN*level)?(MAX_DEFAULT_HYDROGEN*level):amount;
	}
	public void update(){
		switch(shipState){
		case FLYING:
			oxygen -= 100/level;
			hydrogen -= 1000/level;
			break;
		case LANDED:
			if(focusedObject instanceof Planet){
				Planet landedon = (Planet) focusedObject;

				oxygen -= landedon.isHasAtmosphere()?0:(10/level);
				hydrogen -= landedon.isHasAtmosphere()?0:(100/level);
			}
			break;
		default:
			oxygen -= 10/level;
			hydrogen -= 100/level;
			break;
		}
		
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
		if(this.focusedObject!=null){
			switch(focusedObject.getClass().getName()){
			case "Planet":
				shipState = ShipStates.ORBITING_PLANET;
				break;
			case "Sun":
				shipState = ShipStates.ORBITING_SUN;
				break;
			default:
				shipState = ShipStates.IDLE;
				break;
			}
		}
		
	}
	public Object getFocusedObject(){
		return focusedObject;
	}
	public ShipStates getState(){
		return shipState;
	}
	public String toString(){
		String ret = "";
		
		ret += "Ship [";
		
		ret += "id = " + id + ", ";
		ret += "state = " + shipState + ", ";
		ret += "sector = " + coor.x + "," + coor.y + " ]";
		
		return ret;
	}
}
