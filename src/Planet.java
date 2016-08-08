import java.util.Random;



public class Planet extends GameObject{
	private final int MAX_ORE = 10000, MAX_OXYGEN = 100000;
	private String name;
	private boolean hasLife, isAlive, hasAtmosphere;
	private int ore, oxygen;
	private double density;
	
	public Planet(SolarSystem owner, String id){
		Random ran = new Random();
		this.owner = owner;
		this.id = owner.id + id;
		name = id;
		hasLife = ran.nextBoolean();
		isAlive = hasLife?true:false;
		hasAtmosphere = hasLife?true:ran.nextBoolean(); //if the planet has life then we should have an atmosphere...
		oxygen = hasAtmosphere?ran.nextInt(MAX_OXYGEN) + 1:0; //if we have an atmosphere then we need to have oxygen...
		ore = ran.nextInt(MAX_ORE) + 1; //set the ore available on the planet...
		density = ran.nextDouble(); //set the density of the planet...
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the hasLife
	 */
	public boolean isHasLife() {
		return hasLife;
	}
	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}
	/**
	 * @return the hasAtmosphere
	 */
	public boolean isHasAtmosphere() {
		return hasAtmosphere;
	}
	/**
	 * Set the amount of ore on the planet
	 * 
	 * @param amount
	 */
	public void setOre(int amount){
		ore = amount;
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
		
		ret = String.format("Planet [id = %-2s, hasLife = %-5s, hasAtmosphere = %-5s, isAlive = %-5s, ore = %-5s, oxygen = %-6s]\n", 
				id, hasLife, hasAtmosphere, isAlive, ore, oxygen);
		
		return ret;
	}
}
