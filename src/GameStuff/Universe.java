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
	
	
	
	/**
	 * Gets the data pertinent for the map
	 * 
	 * @return sector information for map data
	 */
	public Sector[][] getUniverseData(){
		return universe;
	}
	public String[] getSectorObjectArray(int x, int y){
		
		return universe[x][y].getSectorObjectStringArray();
		
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
