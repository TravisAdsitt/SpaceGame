package GameStuff;

import javax.swing.JFrame;

import UI.GUI;
/**
 * Manages the playing of the game.
 * 
 * TODO: implement the ability to read game modes from a file.
 * 
 * 
 * @author Travis Adsitt
 *
 */
public class GameEngine {
	private static Player player;
	private static Universe universe;
	private static GUI gui;
	private static JFrame gameWindow; 
	private static final int TICK_RATE = 1000;
	
	public static void main(String[] args){
		player = new Player("Travis");
		universe = new Universe(99);
		gui = new GUI(universe,player.getGUIInfo());
		
		gameWindow = new JFrame();
		gameWindow.add(gui);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.pack();
		gameWindow.setVisible(true);
		
		update();
	}
	public static void update(){
		Thread run = new Thread(new Runnable() {
			@Override
			public void run() {
				gui.update();
				player.update();
				//System.out.println("Tick");
				try {
					Thread.sleep(TICK_RATE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				run();
			}
			
		});
		
		run.run();
	}
	
}
