package UI;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import GameStuff.Universe;

public class GameWindow {
	private final static int INI_HEIGHT = 600;
	private final static int INI_WIDTH = 800;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame gameWindow = new JFrame("Sky Map!");
		
		MapView map = new MapView(new Universe(99).getUniverseData());
		
		gameWindow.getContentPane().add(map);
		
		gameWindow.pack();
		gameWindow.setSize(map.getWidth(),map.getHeight());
		gameWindow.setVisible(true);
		
		

	}

}
