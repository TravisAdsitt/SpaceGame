package GameStuff;
import java.util.Random;

/**
 * A class to handle suns and the harvesting of hydrogen.
 * 
 * @author Travis Adsitt
 *
 */
public class Sun extends GameObject{
	private final int MAX_HYDROGEN = 200000000;
	private double hydrogen;
	private int lightAmount;
	private Random ran;
	private final String TYPE = "SUN";
	
	public Sun(){
		ran = new Random();
		lightAmount = 100;
		hydrogen = ran.nextInt(MAX_HYDROGEN);
	}
	/**
	 * Used to harvest hydrogen from the sun, also effects the light.
	 * 
	 * @param harvestPow the amount to harvest.
	 * @return amount harvested
	 */
	public double harvestHydrogen(int harvestPow){
		
		double ret = hydrogen-harvestPow>0?harvestPow:hydrogen;
		
		hydrogen -= hydrogen-harvestPow>0?harvestPow:hydrogen;
		
		int lightLoss = (int) (ret/hydrogen)*lightAmount; // Using the percentage of loss of hydrogen to reduce the light emitted.
		
		lightAmount -= lightLoss;
		
		return ret;
	}
	public int getLight(){
		return lightAmount;
	}
	public String toString(){
		String ret = "";
		
		ret = String.format("Sun [hydrogen = %10s, lightAmount = %3s]\n", hydrogen, lightAmount);
		
		
		return ret;
	}
	/**
	 * Used to find the type of object this is
	 * 
	 * @return string type
	 */
	public String getMyObjectType(){
		return TYPE;
	}
}
