import java.awt.Point;

import javax.swing.JOptionPane;


public class GameEngine {

	public static void main(String[] args){
		Model gameModel = new Model();
		for(int x = 0; x<99 ; x++){
			for(int y = 0; y<99 ; y++){
				gameModel.addSector(new Sector(new Point(x,y),gameModel));
			}
			
		}
		
		NameGenerator names = new NameGenerator();
		String playersShipName = names.getRandomName();
		
		
		Player player = new Player(JOptionPane.showInputDialog("What is your name captain?"));
		Ship playersShip = new Ship(player,playersShipName,new Point(0,0),null);
		player.setShip(playersShip);
		gameModel.addShip(playersShip);
		
		gameModel.addPlayer(player);
		
		TextObserver to = new TextObserver(gameModel);
		
		//System.out.println(gameModel.getShipById(playersShipName).getClass().getName());
		
		while(true){
			String key = JOptionPane.showInputDialog("Key to change:");
			String value = JOptionPane.showInputDialog("Value:");
			
			gameModel.setKey(key, value);
		}
	}
}