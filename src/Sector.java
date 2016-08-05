import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class Sector extends GameObject{
	public ArrayList<Ship> ships;
	public ArrayList<SolarSystem> solarSystems;
	public ArrayList<Object> spaceJunk;
	private Point coor;
	
	public Sector(Point coor, Model model){
		this.coor = coor;
		solarSystems = new ArrayList<SolarSystem>();
		spaceJunk = new ArrayList<Object>();
		ships = new ArrayList<Ship>();
		
		initSector(model);
	}
	
	
	public void removeShip(Ship ship){
		ships.remove(ship);
	}
	public void setShip(Ship ship){
		if(!ships.contains(ship)){
			ships.add(ship);
		}
	}
	public ArrayList<SolarSystem> getAllSolarSystems(){
		return solarSystems;
	}
	public ArrayList<Ship> getAllShips(){
		return ships;
	}
	public ArrayList<Object> getSpaceJunk(){
		return spaceJunk;
	}
	/**
	 * Returns the point that the sector is located
	 * @return Point for coordinates
	 */
	public Point getCoor(){
		return coor;
	}
	/**
	 * Gets a seed based on the sectors coordinates
	 * 
	 * @return int seed
	 */
	public int getSeed(){
		return coor.x * coor.y;
	}
	/**
	 * Initialize the sector randomly
	 */
	private void initSector(Model model){
		Random ran = new Random();
		
		if(ran.nextDouble()<.003){
			int numSolarSystems = ran.nextInt(5)+1;
			
			for(int i = 0 ; i<numSolarSystems ; i++){
				SolarSystem newSolar = new SolarSystem(this,names.getRandomName(),model);
				solarSystems.add(newSolar);
				model.addSolarSystem(newSolar);
			}
		}
		
		
	}
	
	
}
