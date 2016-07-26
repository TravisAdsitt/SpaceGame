package GameStuff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
/**
 * Player of the game
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
	public Player(String name, int tick){
		playerName = name;
		fleets = new ArrayList<Fleet>();
		fleets.add(new Fleet());
		fleets.get(0).addShip(new Ship("1",0,0));
		Timer tmr = new Timer(tick, new RefreshGUI());
		tmr.start();
	}
	
	public void update(){
		for(Fleet f : fleets){
			f.update();
		}
	}
	/**
	 * Gets the number of fleets the player has
	 * 
	 * @return int fleets
	 */
	public int getNumFleets(){
		return fleets.size();
	}
	/**
	 * For drawing purposes I found this to be easiest just to pass the fleet in array form.
	 * 
	 * @param index of the fleet
	 * @return String array of fleet ships toString values.\
	 */
	public Fleet getFleet(int index){
		return fleets.get(index);
	}
	/**
	 * Get info for for the GUI
	 * 
	 * @return object full of the players info.
	 */
	public Object[] getGUIInfo(){
		Object[] ret = new Object[1];
		
		ret[0] = fleets;
		
		return ret;
	}
	private class RefreshGUI implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			update();
			
		}
		
	}
}
