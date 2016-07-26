package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import GameStuff.Fleet;
import GameStuff.Ship;
import GameStuff.Universe;

public class GUI extends JPanel{
	private MapView map;
	private Universe universe;
	private JList<String> shipList, sectorObjectList;
	private ArrayList<Fleet> fleets;
	
	public GUI(Universe uni, Object[] playerData){
		universe = uni;
		
		map = new MapView(universe.getUniverseData());
		
		fleets = (ArrayList<Fleet>) playerData[0];
		
		DefaultListModel<String> ships = new DefaultListModel<String>();
		DefaultListModel<String> objects = new DefaultListModel<String>();
		
		for(String i : fleets.get(0).getFleetArray()){
			ships.addElement(i);
		}
		
		shipList = new JList<String>(ships);
		shipList.setSelectedIndex(0);
		
		for(String i : universe.getSectorObjectArray(fleets.get(0).getShip(shipList.getSelectedIndex()).getX(), fleets.get(0).getShip(shipList.getSelectedIndex()).getY())){
			objects.addElement(i);
		}
		
		sectorObjectList = new JList<String>(objects);
		
		
		
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		add(map);
		add(shipList);
		add(sectorObjectList);
		
		
		
	}
	public void update(){
		//==========Ship List Update Section==========
		DefaultListModel<String> list = new DefaultListModel<String>();
		
		for(String i : fleets.get(0).getFleetArray()){
			list.addElement(i);
		}
		int tempIndex = shipList.getSelectedIndex();
		
		shipList.setModel(list);
		shipList.setSelectedIndex(tempIndex);
		
		//==========Sector List Update Section=========
		DefaultListModel<String> objects = new DefaultListModel<String>();
		
		for(String i : universe.getSectorObjectArray(fleets.get(0).getShip(shipList.getSelectedIndex()).getX(), fleets.get(0).getShip(shipList.getSelectedIndex()).getY())){
			objects.addElement(i);
		}
		tempIndex = sectorObjectList.getSelectedIndex();
		
		sectorObjectList.setModel(objects);;
		
		sectorObjectList.setSelectedIndex(tempIndex);
		
		//==========Ship Command Checks and Sends======
		int[] comCoor = map.getComCoord();
		
		if(comCoor[0] != -1&&shipList.getSelectedIndex()>=0){
			
			Ship shipCom = fleets.get(0).getShip(shipList.getSelectedIndex());
			
			shipCom.moveShip(comCoor[0], comCoor[1]);
			
		}
		
		
		
	}
	
	

}
