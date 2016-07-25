package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;

import GameStuff.Fleet;
import GameStuff.Universe;

public class GUI extends JPanel{
	private MapView map;
	private Universe universe;
	private JList shipList;
	
	public GUI(Universe uni, Object[] playerData){
		universe = uni;
		
		map = new MapView(universe.getUniverseData());
		
		@SuppressWarnings("unchecked")
		ArrayList<Fleet> fleets = (ArrayList<Fleet>) playerData[0];
		
		shipList = new JList(fleets.get(0).getFleetArray());
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		add(map);
		add(shipList);
		
		
		
	}
	public void update(){
		shipList.repaint();
		
	}
	
	

}
