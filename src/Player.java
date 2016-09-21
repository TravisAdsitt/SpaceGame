import java.util.ArrayList;


public class Player {

	private ArrayList<Ship> ships;
	private ArrayList<Base> bases;
	private String name;
	private int ore;
	
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
	public ArrayList<Base> getBases(){
		return bases;
	}
	public void setBase(Base base){
		bases.add(base);
	}
	public String getName(){
		return name;
	}
}
