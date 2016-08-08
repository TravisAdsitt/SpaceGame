import java.util.ArrayList;
import java.util.Random;


public class SolarSystem extends GameObject{

	
	private ArrayList<Ship> ships;
	private ArrayList<Planet> planets;
	private ArrayList<Sun> suns;
	
	public SolarSystem(Sector owner, String id, Model model){
		this.owner = owner;
		this.id = id;
		
		planets = new ArrayList<Planet>();
		suns = new ArrayList<Sun>();
		ships = new ArrayList<Ship>();
		
		initSolarSystem(model);
	}
	
	public ArrayList<Planet> getPlanets(){
		return planets;
	}
	
	public ArrayList<Sun> getSuns(){
		return suns;
	}
	public void initSolarSystem(Model model){
		Random ran = new Random();
		
		int numPlanets = ran.nextInt(10)+1; //1 to 10 Planets
		int numSuns = ran.nextInt(1)+1; //1 to 2 Suns
		
		for(int i = 0; i<numPlanets ; i++){
			Planet newPlanet = new Planet(this,names.getRandomName());
			planets.add(newPlanet);
			model.addPlanet(newPlanet);
			
		}
		
		for(int i = 0; i<numSuns ; i++){
			Sun newSun = new Sun(this,names.getRandomName());
			suns.add(newSun);
			model.addSun(newSun);
		}
		
		
	}
	public String getId(){
		return id;
	}

}
