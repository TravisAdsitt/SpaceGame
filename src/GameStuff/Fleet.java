package GameStuff;

import java.util.ArrayList;
/**
 * Class that handles a lot of ships, this is for when different types of ships is implemented.
 * 
 * @author Travis Adsitt
 *
 */
public class Fleet {
	private ArrayList<Ship> ships;
	private String fleetName;
	/**
	 * Constructor to take in an arraylist for building the ships in the fleet. Also a string to name your fleet (for future ship type fleets)
	 * 
	 * @param ships to be added to the fleet.
	 * @param name the name of your fleet.
	 */
	public Fleet(ArrayList<Ship> ships, String name){
		this.ships = ships;
		fleetName = name;
	}
	/**
	 * Constructor to take in an arraylist for building the ships in the fleet.
	 * 
	 * @param ships to be added to the fleet.
	 */
	public Fleet(ArrayList<Ship> ships){
		this.ships = ships;
	}
	/**
	 * Creates a new fleet that is empty.
	 */
	public Fleet(){
		ships = new ArrayList<Ship>();
	}
	/**
	 * Get the ships in arraylist form
	 * 
	 * @return arraylist of ships
	 */
	public ArrayList<Ship> getFleet(){
		return ships;
	}
	/**
	 * Adds a ship to the fleet
	 * 
	 * @param newShip ship to be added
	 */
	public void addShip(Ship newShip){
		ships.add(newShip);
	}
	/**
	 * Removes the specified ship object
	 * 
	 * @param remShip ship to be removed
	 */
	public void removeShip(Ship remShip){
		ships.remove(remShip);
	}
	/**
	 * 
	 * @param index of the ship to get.
	 * @return Ship that you want.
	 */
	public Ship getShip(int index){
		return ships.get(index);
	}
	
	public void update(){
		for(int i = 0; i<ships.size() ;i++){
			ships.get(i).update();
		}
	}
	public String[] getFleetArray(){
		String[] ret = new String[ships.size()];
		
		for(int i = 0; i<ships.size() ;i++){
			ret[i] = ships.get(i).toString();
		}
		
		return ret;
	}
}
