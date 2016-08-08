
public class Base extends GameObject{
	private int ore, hydrogen, oxygen;
	private Sector homeSector;
	private Planet homePlanet;
	
	public Base(Player owner, Planet homePlanet, Sector homeSector){
		this.id = names.getRandomName();
		this.owner = owner;
		this.homePlanet = homePlanet;
		this.homeSector = homeSector;
		ore=hydrogen=oxygen=0;
		
	}
}
