import java.util.ArrayList;


public class Player {

	private ArrayList<Ship> ships;
	private String name;
	
	public Player(String name){
		ships = new ArrayList<Ship>();
		this.name = name;
	}
	public Player(){
		ships = new ArrayList<Ship>();
	}
	public int getNumShips(){
		return ships.size();
	}
	public ArrayList<Ship> getShips(){
		return ships;
	}
	public void setShip(Ship ship){
		ships.add(ship);
	}
	public String getName(){
		return name;
	}
}
