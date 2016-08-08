import java.awt.Point;


public class Ship {

	private Player owner;
	private String id;
	private final int MAX_DEFAULT_HYDROGEN = 10000, MAX_DEFAULT_OXYGEN = 1000, MAX_DEFAULT_ORE = 1000,
			MAX_DEFAULT_MINEPOW = 20, MAX_DEFAULT_VACPOW = 20;
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
		hydrogen = MAX_DEFAULT_HYDROGEN;
		oxygen = MAX_DEFAULT_OXYGEN;
		ore = 0;
		minePow = MAX_DEFAULT_MINEPOW;
		vacPow = MAX_DEFAULT_VACPOW;
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
			oxygen -= oxygen<=0?0:100/level;
			hydrogen -= hydrogen<=0?0:1000/level;
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
	public void land(){
		if(focusedObject instanceof Planet){
			shipState = ShipStates.LANDED;
		}
	}
	public void takeOff(){
		if(shipState.equals(ShipStates.LANDED)){
			setFocusedObject(focusedObject);
		}
	}
	public Object getFocusedObject(){
		return focusedObject;
	}
	public ShipStates getState(){
		return shipState;
	}
	public int getMinePow(){
		return minePow;
	}
	public int getVacPow(){
		return vacPow;
	}
	public int getMaxOre(){
		return (MAX_DEFAULT_ORE * level);
	}
	public int getMaxOxygen(){
		return (MAX_DEFAULT_OXYGEN * level);
	}
	public int getMaxHydrogen(){
		return (MAX_DEFAULT_HYDROGEN * level);
	}
	public int getOre(){
		return ore;
	}
	public String toString(){
		String ret = "";
		
		ret += "Ship [";
		
		ret += "id = " + id + ", ";
		ret += "state = " + shipState + ", ";
		ret += "sector = " + coor.x + "," + coor.y + ", ";
		ret += "hyd = " + hydrogen + ", oxy = " + oxygen + ", ore = " + ore + " ]";
		
		return ret;
	}
}
