package GameStuff;
import java.util.Random;

/**
 * This class defines the properties of a planet.
 * 
 * 
 * @author Travis Adsitt
 *
 */
public class Planet extends GameObject{
	private final int MAX_ORE = 10000, MAX_OXYGEN = 100000;
	private boolean hasLife, isAlive, hasAtmosphere;
	private int ore, oxygen, id;
	private double density;
	private Random ran;
	private final String TYPE = "PLANET";
	private String name;
	private NameGenerator nameMaker;
	/**
	 * Constructor for the planets initialization.
	 */
	public Planet(int id){
		nameMaker = new NameGenerator();
		ran = new Random();
		this.id = id;
		name = nameMaker.getRandomName();
		hasLife = ran.nextBoolean();
		isAlive = hasLife?true:false;
		hasAtmosphere = hasLife?true:ran.nextBoolean(); //if the planet has life then we should have an atmosphere...
		oxygen = hasAtmosphere?ran.nextInt(MAX_OXYGEN) + 1:0; //if we have an atmosphere then we need to have oxygen...
		ore = ran.nextInt(MAX_ORE) + 1; //set the ore available on the planet...
		density = ran.nextDouble(); //set the density of the planet...
		
	}
	/**
	 * Used to mine the planet, this is to ensure the density is used in the mining process.
	 * 
	 * @return amount of ore that came from the mining operation.
	 */
	public double minePlanet(int minePow){
		
		double amount = minePow * density;
		
		double ret = ore-amount>0?amount:ore;
		
		ore -= ret;
		
		return ret;
		
	}
	
	/**
	 * Much easier to harvest oxygen.
	 * 
	 * @param vacuumPow
	 * @return amount of oxygen harvested
	 */
	public int harvestOxygen(int vacuumPow){
		
		int ret = oxygen-vacuumPow>0?vacuumPow:oxygen;
		
		oxygen -= ret;
		
		return ret;
		
		
	}
	/**
	 * @return the hasLife
	 */
	public boolean isHasLife() {
		return hasLife;
	}

	/**
	 * @return the hasAtmosphere
	 */
	public boolean isHasAtmosphere() {
		return hasAtmosphere;
	}

	/**
	 * @return the ore
	 */
	public int getOre() {
		return ore;
	}

	/**
	 * @return the oxygen
	 */
	public int getOxygen() {
		return oxygen;
	}

	/**
	 * @return the density
	 */
	public double getDensity() {
		return density;
	}
	
	public String toString(){
		String ret = "";
		
		ret = String.format("Planet [id = %-2s, name = %-15s, hasLife = %-5s, hasAtmosphere = %-5s, isAlive = %-5s, ore = %-5s, oxygen = %-6s]\n", 
				id, name, hasLife, hasAtmosphere, isAlive, ore, oxygen);
		
		return ret;
	}
	/**
	 * Used to find the type of object this is
	 * 
	 * @return string type
	 */
	public String getMyObjectType(){
		return TYPE;
	}
}
