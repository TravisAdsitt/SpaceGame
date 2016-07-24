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
	
	private ArrayList sectorObjects;
	private ArrayList<SolarSystem> sectorSolarSystems;
	private Random ran;
	private String id;
	private int xCoor, yCoor;
	
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
	public void addObjectToSector(Object object){
		sectorObjects.add(object);
	}
}
