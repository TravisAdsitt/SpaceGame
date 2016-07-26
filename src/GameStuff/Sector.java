package GameStuff;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Travis Adsitt
 *
 *
 */
public class Sector {
	
	private ArrayList<GameObject> sectorObjects;
	private ArrayList<SolarSystem> sectorSolarSystems;
	private Random ran;
	private String id;
	private int xCoor, yCoor;
	public final boolean PARENT = true;
	private final String TYPE = "SECTOR";
	
	/**
	 * Constructor for the sector.
	 * 
	 * @param x coordinate for the sector
	 * @param y coordinate for the sector
	 */
	public Sector(int x, int y, String id){
		ran = new Random();
		sectorObjects = new ArrayList();
		sectorSolarSystems = new ArrayList<SolarSystem>();
		this.id = id;
		
		xCoor = x;
		yCoor = y;
		
		initSector();
		
		
	}
	/**
	 * This is used to initialize the solar system, has a 3% chance of having up to 5 solar systems.
	 */
	public void initSector(){
		
		if(ran.nextDouble()<.003){
			for(int i = 0; i<ran.nextInt(5)+1 ; i++){
				
				SolarSystem littleBang = new SolarSystem();
				
				sectorSolarSystems.add(littleBang);
				
				//System.out.println(littleBang.toString());
				
			}
		}
		
	}
	/**
	 * Get the id of the Sector
	 * 
	 * @return id string.
	 */
	public String getID(){
		
		return id;
	
	}
	/**
	 * Get the number of objects in the sector
	 * 
	 * @return num of objects in the sector
	 */
	public int getNumObjects(){
		
		return sectorObjects.size();
		
	}
	/**
	 * Get the number of solar systems in this sector.
	 * 
	 * @return num of solar systems in the sector
	 */
	public int getNumSolarSystem(){
		
		return sectorSolarSystems.size();
		
	}
	
	/**
	 * Get a solar system object in the sector.
	 * 
	 * @param index of the solar system
	 * @return solar system object selected
	 */
	public SolarSystem getSolarSystem(int index){
		
		return sectorSolarSystems.get(index);
	
	}
	/**
	 * This is where we will return the options for interacting with the sector.
	 * 
	 * @return String arg0 sector arg1 enter system arg2 scan object
	 */
	public String[] options(){
		
		String[] ret = new String[3];
		
		ret[0] = "You are in sector x = %2s y = %2s.";
		ret[1] = sectorSolarSystems.size()>0?"Enter System":"";
		ret[2] = sectorObjects.size()>0?"Scan Object":"";
		
		return ret;
	}
	 /**
	  * Adds an object to the sector
	  * 
	  * @param object to add
	  */
	public void addObjectToSector(GameObject object){
		sectorObjects.add(object);
	}
	/**
	 * Used to find the type of object this is
	 * 
	 * @return string type
	 */
	public String getMyObjectType(){
		return TYPE;
	}
	/**
	 * Used to get a subordinate object
	 * 
	 * @param index of the object to get
	 * @return subordinate object
	 */
	public ArrayList<ArrayList> getSubordinateObjectArrayLists(){
		
		ArrayList<ArrayList> ret = new ArrayList<ArrayList>();
		
		ret.add(sectorSolarSystems);
		ret.add(sectorObjects);
		
		
		
		return ret;
	}
	/**
	 * When trying to figure out what object you are getting from the sector you check here.
	 * 
	 * @param index of the object
	 * @return string of object type
	 */
	public String getSubordinateObjectType(int index){
		String ret = "";
		if(index<sectorSolarSystems.size()){
			ret = sectorSolarSystems.get(index).getMyObjectType();
		}else if(index<sectorSolarSystems.size()+sectorObjects.size()){
			ret = sectorObjects.get(index).getMyObjectType();
		}else{
			ret = "Object Not Found!";
		}
		
		return ret;
	}
	/**
	 * Getting sector objects for the lists on the GUI
	 * 
	 * @return string array for a list
	 */
	public String[] getObjectList(){
		String[] ret = new String[sectorObjects.size()+sectorSolarSystems.size()];
		
		int index = 0;
		
		for(SolarSystem s : sectorSolarSystems){
			ret[index] = s.toString();
			index++;
		}
		
		for(GameObject o : sectorObjects){
			ret[index] = o.toString();
			index++;
		}
		
		return ret;
	}
	
}
