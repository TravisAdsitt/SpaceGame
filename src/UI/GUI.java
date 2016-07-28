package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import GameStuff.Fleet;
import GameStuff.GameObject;
import GameStuff.Planet;
import GameStuff.Ship;
import GameStuff.ShipStates;
import GameStuff.SolarSystem;
import GameStuff.Sun;
import GameStuff.Universe;
/**
 * This is used for the handling of all GUI stuff... At this moment it does a lot of game enginy stuff.
 * 
 * TODO: I think that we need to add states to the game... might require some rewritting :(
 * 
 * @author Travis Adsitt
 *
 */
public class GUI extends JPanel{
	private MapView map;
	private Universe universe;
	private JList<String> shipList, sectorObjectList, systemObjectList;
	private ArrayList<Fleet> fleets;
	private ArrayList<GameObject> listObjects;
	private boolean systemSelected;
	private JLabel announcements;
	private Ship selectedShip;
	private JPanel buttons;
	private JPanel northPanel, southPanel, eastPanel, westPanel, centerPanel;
	JButton mine, vac, takeoff, land, exitorbit, exitSystem;
	
	public GUI(Universe uni, Object[] playerData){
		
		systemSelected = false;
		universe = uni; 
		listObjects = new ArrayList<GameObject>();
		fleets = (ArrayList<Fleet>) playerData[0];
		
		initCenterPanel();
		initEastPanel();
		initSouthPanel();
		initWestPanel();
		
		
		JPanel superPanel = new JPanel();
		superPanel.setLayout(new BorderLayout());
		
		superPanel.add(centerPanel,BorderLayout.CENTER );
		superPanel.add(eastPanel, BorderLayout.EAST);
		superPanel.add(southPanel, BorderLayout.SOUTH);
		superPanel.add(westPanel, BorderLayout.WEST);
		
		add(superPanel);
		
		Timer tmr = new Timer(100,new RefreshGUI());
		tmr.start();
		
		
	}
	/**
	 * This is for initializing the Center Panel
	 */
	public void initCenterPanel(){
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
		
		map = new MapView(universe.getUniverseData(),fleets.get(0).getFleet());
		centerPanel.add(map);
		
	}
	public void initEastPanel(){
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		
		JLabel shipListLabel = new JLabel("Ships", SwingConstants.CENTER);
		JLabel objectSectorListLabel = new JLabel("Objects", SwingConstants.CENTER);
		
		
		
		
		
		DefaultListModel<String> ships = new DefaultListModel<String>();
		DefaultListModel<String> objects = new DefaultListModel<String>();
		
		for(String i : fleets.get(0).getFleetArray()){
			ships.addElement(i);
		}
		
		shipList = new JList<String>(ships);
		shipList.setSelectedIndex(0);
		shipList.setPreferredSize(new Dimension(600,100));
		
		for(ArrayList<GameObject> i : universe.getSector(fleets.get(0).getShip(shipList.getSelectedIndex()).getX(), fleets.get(0).getShip(shipList.getSelectedIndex()).getY()).getSubordinateObjectArrayLists()){
			for(GameObject o : i){
				listObjects.add(o);
			}
		}
		
		sectorObjectList = new JList<String>(objects);
		sectorObjectList.setPreferredSize(new Dimension(600,100));
		sectorObjectList.addListSelectionListener(new ObjectListListener());
		
		eastPanel.add(shipListLabel);
		eastPanel.add(shipList);
		eastPanel.add(objectSectorListLabel);
		eastPanel.add(sectorObjectList);
		
	}
	public void initSouthPanel(){
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		
		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons,BoxLayout.X_AXIS));
		buttons.setPreferredSize(new Dimension(500,100));
		
		ButtonsListener controls = new ButtonsListener();
		
		JLabel buttonsLabel = new JLabel("Commands Available");
		mine = new JButton("Mine");
		vac = new JButton("Vacuum");
		takeoff = new JButton("Take Off");
		land = new JButton("Land");
		exitorbit = new JButton("Exit Orbit");
		exitSystem = new JButton("Exit System");
		
		buttons.setAlignmentX(CENTER_ALIGNMENT);
		buttonsLabel.setAlignmentX(CENTER_ALIGNMENT);
		mine.addActionListener(controls);
		vac.addActionListener(controls);
		takeoff.addActionListener(controls);
		land.addActionListener(controls);
		exitorbit.addActionListener(controls);
		exitSystem.addActionListener(controls);
		
		southPanel.add(buttonsLabel);
		southPanel.add(buttons);
	}
	public void initWestPanel(){
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel,BoxLayout.Y_AXIS));
		
		announcements = new JLabel();
		announcements.setPreferredSize(new Dimension(500,200));
		
		westPanel.add(announcements);
		
	}
	/**
	 * Used for updating and checking for ship commands.
	 */
	@SuppressWarnings("unchecked")
	public void update(){
		
		map.update(fleets.get(0).getFleet());
		
		
		//==========Ship List Update Section==========
		DefaultListModel<String> list = new DefaultListModel<String>();
		
		for(String i : fleets.get(0).getFleetArray()){
			list.addElement(i);
		}
		
		int tempIndex = shipList.getSelectedIndex();
		
		selectedShip = fleets.get(0).getShip(tempIndex);
		
		shipList.setModel(list);
		
		shipList.setSelectedIndex(tempIndex);
		
		//==========Sector List Update Section=========
		DefaultListModel<String> objects = new DefaultListModel<String>();
		
		
		if(listObjects.size()==0){
			for(ArrayList<GameObject> i : universe.getSector(fleets.get(0).getShip(tempIndex).getX(),fleets.get(0).getShip(tempIndex).getY()).getSubordinateObjectArrayLists()){
				for(GameObject l : i){
					listObjects.add(l);
				}
			}
		}
		
		if(listObjects.size()>0){
			for(GameObject i : listObjects){
				objects.addElement(i.toString());
			}
		}else{
			objects.addElement("No Objects Here!");
		}
		sectorObjectList.setModel(objects);
		
		
		//==========Ship Command Checks and Sends======
		int[] comCoor = map.getComCoord();
		
		if(comCoor[0] != -1&&shipList.getSelectedIndex()>=0&&fleets.get(0).getShip(shipList.getSelectedIndex()).getState().canMoveSector()){
			
			Ship shipCom = fleets.get(0).getShip(shipList.getSelectedIndex());
			
			announcements.setText(shipCom.moveShip(comCoor[0], comCoor[1]));
			
			shipCom.setState(ShipStates.FLYING);
			
			listObjects.clear();
			
		}
		//==========Buttons Panel=====================
		switch(fleets.get(0).getShip(shipList.getSelectedIndex()).getState()){
		case FLYING:
			buttons.removeAll();
			if(systemSelected){
				buttons.add(exitSystem);
			}
			
			break;
		case ORBITING_PLANET:
			buttons.removeAll();
			buttons.add(land);
			buttons.add(exitorbit);
			break;
		case ORBITING_SUN:
			buttons.removeAll();
			buttons.add(vac);
			buttons.add(exitorbit);
			break;
		case LANDED:
			buttons.removeAll();
			buttons.add(takeoff);
			buttons.add(mine);
			buttons.add(vac);
			break;
		}
		
		
		
	}
	public void leaveSystem(){
		systemSelected = false;
		listObjects.clear();
		sectorObjectList.removeAll();
		int tempIndex = shipList.getSelectedIndex();
		for(ArrayList<GameObject> i : universe.getSector(fleets.get(0).getShip(tempIndex).getX(),fleets.get(0).getShip(tempIndex).getY()).getSubordinateObjectArrayLists()){
			for(GameObject l : i){
				listObjects.add(l);
			}
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
			if(sectorObjectList.getSelectedIndex()>=0){
				int index = sectorObjectList.getSelectedIndex();
				GameObject obj = listObjects.get(index);
				

				if(obj.isParent()){
					switch(obj.getMyObjectType()){
					case "SOLARSYSTEM":
						SolarSystem objS = (SolarSystem) obj;
						listObjects.clear();
						for(ArrayList<GameObject> ago : objS.getSubordinateObjectArrayLists()){
							for(GameObject go : ago){
								listObjects.add(go);
							}
						}
						sectorObjectList.clearSelection();
						systemSelected = true;
						break;
					}
				}else{
					switch(obj.getMyObjectType()){
					case "PLANET":
						
						Planet objP = (Planet) obj;				
						
						announcements.setText(selectedShip.orbitPlanet(objP));
						
						break;
					case "SUN":
						Sun objS = (Sun) obj;
						
						announcements.setText(selectedShip.orbitSun(objS));
						
						break;
					}
				}
			}
		}
	}
	private class ButtonsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			switch(event.getActionCommand()){
			case"Land":
				fleets.get(0).getFleet().get(shipList.getSelectedIndex()).landOnPlanet();
				break;
			case"Mine":
				fleets.get(0).getFleet().get(shipList.getSelectedIndex()).minePlanet(Integer.valueOf(JOptionPane.showInputDialog("How Much?")));
				break;
			case"Take Off":
				fleets.get(0).getFleet().get(shipList.getSelectedIndex()).setState(ShipStates.ORBITING_PLANET);
				break;
			case"Vacuum":
				if(fleets.get(0).getFleet().get(shipList.getSelectedIndex()).getState() == ShipStates.ORBITING_SUN){
					fleets.get(0).getFleet().get(shipList.getSelectedIndex()).vacSun(Integer.valueOf(JOptionPane.showInputDialog("How Much?")));
				}else{
					fleets.get(0).getFleet().get(shipList.getSelectedIndex()).vacPlanet(Integer.valueOf(JOptionPane.showInputDialog("How Much?")));
				}
				break;
			case"Exit Orbit":
				fleets.get(0).getFleet().get(shipList.getSelectedIndex()).leaveOrbit();
				break;
			case"Exit System":
				leaveSystem();
				break;
			}


		}
		
	}
	//private class RefreshGUI implements Action
	private class RefreshGUI implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			update();
			
		}
		
	}

}
