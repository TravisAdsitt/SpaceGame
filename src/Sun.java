import java.util.Random;

public class Sun extends GameObject{
	private final int MAX_HYDROGEN = 200000000;
	private double hydrogen;
	private int lightAmount;
	private Random ran;
	
	public Sun(SolarSystem owner, String id) {
		this.owner = owner;
		this.id = id;
		ran = new Random();
		
		hydrogen = ran.nextInt(MAX_HYDROGEN)+1;
		lightAmount = 100;
		
	}
	
	public int getHydrogen(int amount){
		int ret = (hydrogen-amount<0)?(int)hydrogen:amount;
		
		hydrogen -= (ret==amount)?amount:0;
		
		return ret;
		
	}
	
	public String toString(){
		
		String ret = null;
		
		ret = id;
		
		return ret;
	}

}
