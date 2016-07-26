package GameStuff;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Travis Adsitt
 * 
 * Used to handle planets and a sun.
 *
 */
public class SolarSystem {
	private Planet[] planets;
	private Sun[] suns;
	private Random ran;
	private ArrayList spaceJunk;
	private int sector, id;
	
	
	/**
	 * Setup the solar system and automatically initialize it.
	 */
	public SolarSystem(){
		ran = new Random();
		planets = new Planet[ran.nextInt(11)];
		suns = new Sun[ran.nextInt(2)+1];
		spaceJunk = new ArrayList();
		
		initSolarSystem();
		
	}
	/**
	 * This adds a level of tracking to the Solar System object.
	 * 
	 * @param sector if you want the sector to be stored here
	 * @param id if you want to give the solar system an id
	 */
	public SolarSystem(int sector, int id){
		ran = new Random();
		planets = new Planet[ran.nextInt(11)];
		suns = new Sun[ran.nextInt(2)+1];
		
		this.sector = sector;
		this.id = id;
		
		
		initSolarSystem();
	}
	
	/**
	 * Used to initialize the solar system with random objects.
	 */
	public void initSolarSystem(){
		
		for(int i = 0; i<planets.length ; i++){
			planets[i] = new Planet(i);
		}
		
		for(int i = 0; i<suns.length ; i++){
			suns[i] = new Sun();
		}
		
		
	}
	
	
	
	/**
	 * This is to add space junk to our solar system.
	 * 
	 * @param junk the junk to add to your solar system.
	 */
	public void addSpaceJunk(Object junk){
		spaceJunk.add(junk);
	}
	
	/**
	 * Get the number of planets in the solar system. 
	 * 
	 * @return int Number of planets in the Solar System.
	 */
	public int getNumPlanet(){
		return planets.length;
	}
	
	/**
	 * Get the number of suns(could be a binary star)
	 * 
	 * @return int Number of suns in the Solar System.
	 */
	public int getNumSuns(){
		return suns.length;
	}
	/**
	 * Returns the light level of the solar system.
	 * 
	 * @return light level of the solar system
	 */
	public int getLightLevel(){
		int totalLight = 0;
		
		for(Sun s : suns){
			totalLight += s.getLight();
		}
		
		return totalLight/suns.length;
	}
	
	public String toString(){
		
		String ret = "";
		
		ret += String.format("The solar system appears to have %1s %4s and %2s %7s. \n"+
		"The Solar System %s have enough light for life\n\n", 
				suns.length, 
				suns.length==1?"sun":"suns",
						planets.length,
						planets.length==1?"planet":"planets",
								getLightLevel()>50?"does":"does not");
		//for(Planet p : planets){
		//	ret += p.toString();
		//}
		//for(Sun s : suns){
		//	ret += s.toString();
		//}
		
		return ret;
	}
	
	
}
