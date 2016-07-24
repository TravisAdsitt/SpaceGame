package GameStuff;

import java.util.ArrayList;
/**
 * 
 * @author Travis Adsitt
 *
 */
public class Player {
	
	private String playerName;
	private ArrayList<Fleet> fleets;
	
	/**
	 * Constructor to create a player with a name and an empty fleet.
	 * 
	 * @param name of the player
	 */
	public Player(String name){
		playerName = name;
		fleets = new ArrayList<Fleet>();
		fleets.add(new Fleet());
	}
	
	
}
