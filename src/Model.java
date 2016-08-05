import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;


public class Model extends Observable{
 private HashMap<String, Object> topModel;
 
 private ArrayList<Ship> ships;
 private ArrayList<Sector> universe;
 private ArrayList<SolarSystem> solarSystems;
 private ArrayList<Planet> planets;
 private ArrayList<Sun> suns;
 private ArrayList<Player> players;
 private ArrayList<Object[]> commands;
 private int gridSize;
 
 public Model(){
	 ships = new ArrayList<Ship>();
	 universe = new ArrayList<Sector>();
	 solarSystems = new ArrayList<SolarSystem>();
	 planets = new ArrayList<Planet>();
	 players = new ArrayList<Player>();
	 suns = new ArrayList<Sun>();
	 commands = new ArrayList<Object[]>();
	 topModel = new HashMap();
 }

 public Sector[][] getUniverseSectorArray(){
	 int gridSize = (int) Math.ceil(Math.sqrt(universe.size()));
	 
	 Sector[][] ret = new Sector[gridSize][gridSize];
	 
	 for(int y = 0; y<gridSize ; y++){
		 for(int x = 0; x<gridSize ; x++){
			 ret[x][y] = universe.get(gridSize*y+x);
		 }
	 }
	 return ret;
 }
 public Sector getSector(Point sectorCoor){
	 return universe.get(99*sectorCoor.y+sectorCoor.x);
 }
 public void setGridSize(int gridSize){
	 this.gridSize = gridSize;
 }
 public void setKey(String key, Object value){
	 topModel.put(key, value);
	 
	 notifyObservers(key);
	 setChanged();
 }
 public Object[] getCurrentCommand(){
	 return commands.get(0);
 }
 public void removeCurrentCommand(){
	 if(commands.size()>0){
		 commands.remove(0);
		 if(debugMode())System.out.println("Command Removed!");
	 }
 }
 public Player getCurrentPlayer(){
	 return (Player) topModel.get("currPlayer");
 }
 public boolean hasNextCommand(){
	 return commands.size()>0?true:false;
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
 public boolean debugMode(){
	 return (boolean) topModel.get("debug");
 }
 public void addPlayer(Player  player){
	 if(!topModel.containsKey(player.getName())){
		 players.add(player);
		 setKey("currPlayer",player);
	 }
 }
 public void addSector(Sector sector){
	 universe.add(sector);
 }
 public void addShip(Ship ship){
	 ships.add(ship);
	 topModel.put("SHIP" + ship.getId(), ship);
 }
 public void addSolarSystem(SolarSystem solarSystem){
	 solarSystems.add(solarSystem);
	 topModel.put("SOLARSYSTEM" + solarSystem.getId(), solarSystem);
 }
 public SolarSystem getSolarSystemByID(String id){
	SolarSystem ret = null;
	
	for(SolarSystem s : solarSystems){
		ret = s.getId().equals(id)?s:ret;
	}
	
	return ret;
 }
 public void addSun(Sun sun){
	 suns.add(sun);
	 topModel.put("SUN" + sun.getId(), sun);
 }
 public void addPlanet(Planet planet){
	 planets.add(planet);
	 topModel.put("PLANET" + planet.getId(), planet);
 }
 public void addCommand(Commands commandEnum, Object obj1, Object obj2){
	 Object[] command = new Object[3];
	 
	 command[0] = commandEnum;
	 command[1] = obj1;
	 command[2] = obj2;
	 
	 commands.add(command);
	 
 }
}
