import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;


public class GameEngine {

	public static void main(String[] args){
		int gridSize = 99;
		boolean debug = false;
		
		/*
		 * Stack overflow code 
		 */
		Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  

	    try {  

	        fh = new FileHandler("MyLogFile.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        
	        logger.info("Log started successfully!");

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    
	    JProgressBar jpb = new JProgressBar();
	    jpb.setMinimum(0);
	    jpb.setMaximum((gridSize+1));
	    
	    JFrame progress = new JFrame("Loading The UNIVERSE!");
	    progress.setPreferredSize(new Dimension(400,100));
	    progress.add(jpb);
	    progress.pack();
	    progress.setVisible(true);
	    
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
			jpb.setValue(x);
		}
		
		NameGenerator names = new NameGenerator();
		
		
		/*
		String playersShipOneName = names.getRandomName();
		String playersShipTwoName = names.getRandomName();
		*/
		progress.setVisible(false);
		progress.dispose();
		
		Player player = new Player((debug)?"DEBUGGER":JOptionPane.showInputDialog("What is your name captain?"));
		
		logger.info("New player created! Name : " + player.getName());
		
		/*
		Ship playerShipOne = new Ship(player,playersShipOneName,new Point(0,0),null);
		Ship playerShipTwo = new Ship(player,playersShipTwoName,new Point(0,0),null);
		
		player.setShip(playerShipOne);
		player.setShip(playerShipTwo);
		gameModel.addShip(playerShipOne);
		gameModel.addShip(playerShipTwo);
		*/
		gameModel.setKey("currPlayer", player);
		gameModel.setKey("debug",debug);
		/*
		if(gameModel.debugMode())System.out.println("Player " + gameModel.getCurrentPlayer().getName() + " created!");
		if(gameModel.debugMode())System.out.println("Ship " + playerShipOne.getId() + " created for Player " + playerShipOne.getOwner().getName() + "!");
		*/
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
