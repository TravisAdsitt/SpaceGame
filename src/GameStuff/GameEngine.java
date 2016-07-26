package GameStuff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

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
	private static final int TICK_RATE = 100;
	
	public static void main(String[] args){
		player = new Player("Travis",TICK_RATE);
		universe = new Universe(99);
		gui = new GUI(universe,player.getGUIInfo());
		
		gameWindow = new JFrame();
		gameWindow.add(gui);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.pack();
		gameWindow.setVisible(true);
		
	}
}
