import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GameEngine {

	public static void main(String[] args){
		int gridSize = 99;
		boolean debug = true;
		
		Model gameModel = new Model();
		gameModel.setGridSize(gridSize);
		if(debug)System.out.println("Debugging Enabled!");
		if(debug)System.out.println("Creating Universe!");
		for(int x = 0; x<gridSize ; x++){
			for(int y = 0; y<gridSize ; y++){
				gameModel.addSector(new Sector(new Point(x,y),gameModel));
					if(debug)System.out.print("*");
			}
			System.out.println();
			
		}
		
		NameGenerator names = new NameGenerator();
		String playersShipOneName = names.getRandomName();
		String playersShipTwoName = names.getRandomName();
		
		
		Player player = new Player((debug)?"DEBUGGER":JOptionPane.showInputDialog("What is your name captain?"));
		Ship playerShipOne = new Ship(player,playersShipOneName,new Point(0,0),null);
		Ship playerShipTwo = new Ship(player,playersShipTwoName,new Point(0,0),null);
		
		player.setShip(playerShipOne);
		player.setShip(playerShipTwo);
		gameModel.addShip(playerShipOne);
		gameModel.addShip(playerShipTwo);
		
		gameModel.setKey("currPlayer", player);
		gameModel.setKey("debug",debug);
		
		
		if(gameModel.debugMode())System.out.println("Player " + gameModel.getCurrentPlayer().getName() + " created!");
		if(gameModel.debugMode())System.out.println("Ship " + playerShipOne.getId() + " created for Player " + playerShipOne.getOwner().getName() + "!");
		
		gameModel.addPlayer(player);
		
		
		Controller gameControl = new Controller(gameModel);
		//System.out.println(gameModel.getShipById(playersShipName).getClass().getName());
		
		JFrame gameWindow = new JFrame();
		gameWindow.add(new GUI(gameModel));
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.pack();
		gameWindow.setVisible(true);
		
		
	}
}
