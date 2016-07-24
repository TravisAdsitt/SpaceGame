package UI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import GameStuff.Universe;

public class GUI extends JPanel{
	private MapView map;
	private Universe universe;
	
	public GUI(Universe uni){
		map = new MapView(uni.getUniverseData());
		
		universe = uni;
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		add(map);
		
		
	}
}
