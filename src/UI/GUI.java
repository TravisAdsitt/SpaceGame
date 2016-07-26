package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import GameStuff.Fleet;
import GameStuff.Ship;
import GameStuff.Universe;

public class GUI extends JPanel{
	private MapView map;
	private Universe universe;
	private JList<String> shipList, sectorObjectList, systemObjectList;
	private ArrayList<Fleet> fleets;
	
	public GUI(Universe uni, Object[] playerData){
		universe = uni;
		
		JLabel shipListLabel = new JLabel("Ships", SwingConstants.CENTER);
		JLabel objectSectorListLabel = new JLabel("Sector Objects", SwingConstants.CENTER);
		JLabel objectSystemListLabel = new JLabel("Sector Objects", SwingConstants.CENTER);
		
		map = new MapView(universe.getUniverseData());
		
		fleets = (ArrayList<Fleet>) playerData[0];
		
		DefaultListModel<String> ships = new DefaultListModel<String>();
		DefaultListModel<String> objects = new DefaultListModel<String>();
		
		for(String i : fleets.get(0).getFleetArray()){
			ships.addElement(i);
		}
		
		shipList = new JList<String>(ships);
		shipList.setSelectedIndex(0);
		shipList.setPreferredSize(new Dimension(600,100));
		
		for(String i : universe.getSectorObjectArray(fleets.get(0).getShip(shipList.getSelectedIndex()).getX(), fleets.get(0).getShip(shipList.getSelectedIndex()).getY())){
			objects.addElement(i);
		}
		
		sectorObjectList = new JList<String>(objects);
		sectorObjectList.setPreferredSize(new Dimension(600,100));
		
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		
		JPanel east = new JPanel();
		JPanel west = new JPanel();
		
		east.setLayout(new BoxLayout(east,BoxLayout.Y_AXIS));
		west.setLayout(new BoxLayout(west,BoxLayout.Y_AXIS));
		
		west.add(map);
		east.add(shipListLabel);
		east.add(shipList);
		east.add(objectSectorListLabel);
		east.add(sectorObjectList);
		
		add(west);
		add(east);
		
		
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
		
		String[] objectArray = universe.getSectorObjectArray(fleets.get(0).getShip(shipList.getSelectedIndex()).getX(), fleets.get(0).getShip(shipList.getSelectedIndex()).getY());
		
		if(objectArray.length>0){
			for(String i : objectArray){
				objects.addElement(i);
			}
		}else{
			objects.addElement("No Objects Here!");
		}
		
		tempIndex = sectorObjectList.getSelectedIndex();
		
		sectorObjectList.setModel(objects);
		
		sectorObjectList.setSelectedIndex(tempIndex);
		
		//==========Ship Command Checks and Sends======
		int[] comCoor = map.getComCoord();
		
		if(comCoor[0] != -1&&shipList.getSelectedIndex()>=0){
			
			Ship shipCom = fleets.get(0).getShip(shipList.getSelectedIndex());
			
			shipCom.moveShip(comCoor[0], comCoor[1]);
			
		}
		
		
		
	}
	private class ObjectListListener implements ListSelectionListener
	{
		/* (non-Javadoc)
		 * @see java.awt.event.ListSelectionListener#valueChanged(java.awt.event.ListSelectionEvent)
		 */
		@Override
		public void valueChanged(ListSelectionEvent event)
		{
			
		}
	}
	

}
