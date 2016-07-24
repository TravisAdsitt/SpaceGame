package UI;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import GameStuff.Universe;

public class GameWindow {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame gameWindow = new JFrame("Sky Map!");
		
		GUI gui = new GUI(new Universe(99));
		
		gameWindow.getContentPane().add(gui);
		
		gameWindow.pack();
		
		gameWindow.setVisible(true);
		
		

	}

}
