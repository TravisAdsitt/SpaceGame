import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;


public class Model extends Observable{
 private HashMap<String, Object> topModel;
 
 private ArrayList<Ship> ships;
 private ArrayList<Sector> universe;
 private ArrayList<SolarSystem> solarSystems;
 private ArrayList<Planet> planets;
 private ArrayList<Sun> suns;
 private ArrayList<Player> players;
 public int numSolar, numPlan, numSun;
 
 public Model(){
	 ships = new ArrayList<Ship>();
	 universe = new ArrayList<Sector>();
	 solarSystems = new ArrayList<SolarSystem>();
	 planets = new ArrayList<Planet>();
	 players = new ArrayList<Player>();
	 suns = new ArrayList<Sun>();
	 
	 numSun = 0;
	 numPlan = 0;
	 numSolar = 0;
	 
	 
	 topModel = new HashMap();
 }
 
 public void setKey(String key, String value){
	 
	 
	 topModel.put(key, value);
	 
	 hasChanged();
 }
 
 /**
	 * Returns an ArrayList of Players who are not the player provided
	 * @param player the player to exclude from the list
	 * @return ArrayList of ships not owned by the player provided
	 */
	public ArrayList<Ship> getAllButOwnersShips(Player player){
		ArrayList<Ship> ret = new ArrayList<Ship>();
		
		for(Ship s : ships){ //Does this reference the original object?
			Player shipOwner = s.getOwner();
			
			if(player != shipOwner){
				ret.add(s);
			}
		}
		
		return ret;
	}
 
 public ArrayList<SolarSystem> getSolarSystemsInSector(Point coor){
	 ArrayList<SolarSystem> ret = null;
	 
	 if(coor.x*coor.y < universe.size()){
		 ret = universe.get(coor.x*coor.y).getAllSolarSystems();
	 }
		 
	 return ret;
 }
 public void addPlayer(Player  player){
	 if(!topModel.containsKey(player.getName())){
		 players.add(player);
	 }
 }
 public void addSector(Sector sector){
	 universe.add(sector);
 }
 public void addShip(Ship ship){
	 ships.add(ship);
	 topModel.put("SHIP" + ship.getId(), ship);
 }
 //TODO I forsee a bug here, what if two ships have the same ID? (Also if those ships are owned by different people)
 public Ship getShipById(String id){
	 Ship ret = null;
	 
	 if(topModel.get("SHIP" + id) instanceof Ship){
		 ret = (Ship) topModel.get("SHIP" + id);
		 }
	 
	 return ret;
 }
 public void addSolarSystem(SolarSystem solarSystem){
	 numSolar++;
	 solarSystems.add(solarSystem);
	 topModel.put("SOLARSYSTEM" + solarSystem.getId(), solarSystem);
 }
 public SolarSystem getSolarSystemById(String id){
	 SolarSystem ret = null;
	 
	 if(topModel.get("SOLARSYSTEM" + id) instanceof SolarSystem){
		 ret = (SolarSystem) topModel.get("SOLARSYSTEM" + id);
	 }
	 
	 return ret;
	 
 }
 public void addSun(Sun sun){
	 numSun++;
	 suns.add(sun);
	 topModel.put("SUN" + sun.getId(), sun);
 }
 public void addPlanet(Planet planet){
	 numPlan++;
	 planets.add(planet);
	 topModel.put("PLANET" + planet.getId(), planet);
 }
 public Planet getPlanetId(String id){
	 Planet ret = null;
	 
	 if(topModel.get(id) instanceof Planet){
		 ret = (Planet) topModel.get(id);
	 }
	 
	 return ret;
 }
 
 
}
