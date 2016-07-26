package GameStuff;
import java.util.ArrayList;


/**
 * This class handles all of the sectors in the universe.
 * 
 * @author Travis Adsitt
 *
 */
public class Universe {
	private int gridSize, numSolarSystem, numPlanet;
	private Sector[][] universe;
	
	
	
	public Universe(int gridSize){
		this.gridSize = gridSize;
		universe = new Sector[gridSize][gridSize];
		
		initUniverse();
		
		System.out.println("Num Solar: " + numSolarSystem + " Num Planet: " + numPlanet);
	}
	
	
	public Sector getSector(int x, int y){
		if(x<gridSize&&y<gridSize){
			return universe[x][y];
		}else{
			return universe[0][0];
		}
		
	}
	/**
	 * Gets the data pertinent for the map
	 * 
	 * @return sector information for map data
	 */
	public Sector[][] getUniverseData(){
		return universe;
	}
	/**
	 * Gets a string array for the objects in the sector
	 * 
	 * @param x sector coordinate
	 * @param y sector coordinate
	 * @return string array for the objects in the sector
	 */
	public String[] getSectorObjectArray(int x, int y){
		
		return universe[x][y].getObjectList();
		
	}
	/**
	 * Initializes the universe to the gridsize specified.
	 */
	public void initUniverse(){
		
		for(int x = 0; x<universe.length ; x++){
			for(int y = 0; y<universe.length; y++){
				Sector letThereBeLight = new Sector(x,y,"S" + x + y);
				
				numSolarSystem += letThereBeLight.getNumSolarSystem();
				
				for(int i = 0; i<letThereBeLight.getNumSolarSystem() ; i++){
					numPlanet += letThereBeLight.getSolarSystem(i).getNumPlanet();
				}
				
				universe[x][y] = letThereBeLight;
			}
		}
	}
}
