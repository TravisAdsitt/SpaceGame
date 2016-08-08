

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This is used for the handling of all GUI stuff... At this moment it does a lot of game enginy stuff.
 * 
 * 
 * 
 * @author Travis Adsitt
 *
 */
public class GUI extends JPanel implements Observer{
	private MapView map;
	private Sector[][] universe;
	private JList<String> shipList, sectorObjectList, systemObjectList;
	private ArrayList<GameObject> listObjects;
	private JLabel announcements;
	private JPanel buttons;
	private JPanel northPanel, southPanel, eastPanel, westPanel, centerPanel;
	private JButton mine, vac, takeoff, land, exitorbit, exitSystem;
	private Model gameModel;
	
	public GUI(Model model){
		
		gameModel = model;
		gameModel.addObserver(this);
		
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
		
		map.refreshHighlights();
		
	}
	
	/**
	 * This is for initializing the Center Panel
	 */
	public void initCenterPanel(){
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
		
		map = new MapView(gameModel, new CoordinateListener());
		centerPanel.add(map);
		
	}
	public void initEastPanel(){
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		
		JLabel shipListLabel = new JLabel("Ships", SwingConstants.CENTER);
		JLabel objectSectorListLabel = new JLabel("Objects", SwingConstants.CENTER);
		
		DefaultListModel<String> ships = new DefaultListModel<String>();
		DefaultListModel<String> objects = new DefaultListModel<String>();


		for(Ship s : gameModel.getCurrentPlayer().getShips()){
			ships.addElement(s.toString());
		}

		shipList = new JList<String>(ships);
		shipList.setFont(new Font("monospaced", Font.PLAIN, 12));
		shipList.setSelectedIndex(0);
		shipList.addListSelectionListener(new ShipListListener());
		JScrollPane shipScroll = new JScrollPane();
		shipScroll.add(shipList);
		shipScroll.setPreferredSize(new Dimension(500,200));

		sectorObjectList = new JList<String>(objects);
		sectorObjectList.setFont(new Font("monospaced", Font.PLAIN, 12));
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
		announcements.setPreferredSize(new Dimension(300,200));
		
		westPanel.add(announcements);
		
	}
	/**
	 * Used for updating which buttons should be shown based on the currently selected ships state.
	 */
	@SuppressWarnings("unchecked")
	public void updateCommandButtonsPanel(){
	Ship currShip = gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex());

	switch(gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex()).getState()){
	case IDLE:
		buttons.removeAll();
		if(currShip.getFocusedObject() instanceof SolarSystem){
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
	default:
		buttons.removeAll();
		break;
	}


}
/**
 * Used to refresh the ship lists model if anything changed.
 */
private void refreshShipList(){
		DefaultListModel<String> ships = new DefaultListModel<String>();

		for(Ship s : gameModel.getCurrentPlayer().getShips()){
			ships.addElement(s.toString());
		}
		
		int selection = shipList.getSelectedIndex();
		shipList.setModel(ships);
		shipList.setSelectedIndex(selection);
	}
	/**
	 * Used for updating the objects list
	 */
	public void refreshObjectList(){
		DefaultListModel<String> objectListRefresh = new DefaultListModel<String>();
		listObjects = new ArrayList<GameObject>();
		Ship currShip = gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex());
		
		if(currShip.getFocusedObject() != null){
			if(currShip.getFocusedObject() instanceof SolarSystem){
				
				SolarSystem systemSelected = (SolarSystem) currShip.getFocusedObject();
				
				for(Sun s : systemSelected.getSuns()){
					objectListRefresh.addElement(s.toString());
					listObjects.add(s);
				}
				for(Planet p : systemSelected.getPlanets()){
					objectListRefresh.addElement(p.toString());
					listObjects.add(p);
				}
			}else if(currShip.getFocusedObject() instanceof Planet){
				
			}
		}else if(currShip.getFocusedObject() == null){
			
			if(currShip.getState() == ShipStates.IDLE){
				Sector currSector = gameModel.getSector(currShip.getCoor());
				
				for(SolarSystem s : currSector.getAllSolarSystems()){
					objectListRefresh.addElement(s.id);
					listObjects.add(s);
				}
				
			}
			
		}
		
		sectorObjectList.setModel(objectListRefresh);
	}
	private class ShipListListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(shipList.getSelectedIndex() != -1){
				updateCommandButtonsPanel();
				refreshObjectList();
			}
		}
		
	}
	/**
	 * This is to listen for any changes on the objects in sector list. This may get more complicated in the future this is just for the beginning that it is so simplistic.
	 * 
	 * @author Travis Adsitt
	 *
	 */
	private class ObjectListListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent event){
			Ship currShip = gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex());
			
			if(sectorObjectList.getSelectedIndex() != -1){
				
				currShip.setFocusedObject(listObjects.get(sectorObjectList.getSelectedIndex()));
				
				refreshObjectList();
				updateCommandButtonsPanel();
				
			}
		}
	}
	/**
	 * Dedicated to listening for the command buttons and creating commands for the controller to interpret.
	 * 
	 * @author Travis Adsitt
	 *
	 */
	private class ButtonsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			Ship currShip = gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex());
			
			switch(event.getActionCommand()){
			case"Land":
				currShip.land();
				break;
			case"Mine":
				int amount = Integer.parseInt(JOptionPane.showInputDialog("How much?"));
				
				gameModel.addCommand(Commands.Mine, currShip, currShip.getFocusedObject(), amount);
				break;
			case"Take Off":
				currShip.takeOff();
				break;
			case"Vacuum":
				
				break;
			case"Exit Orbit":
				currShip.setFocusedObject(((GameObject)currShip.getFocusedObject()).owner);
				refreshObjectList();
				break;
			case"Exit System":
				currShip.setFocusedObject(null);
				refreshObjectList();
				break;
			}
			
			updateCommandButtonsPanel();

		}
		
	}
	
	/**
	 * All this is dedicated to is grabbing the coordinates clicked if the map is rightclicked.
	 * 
	 * @author Travis Adsitt
	 *
	 */
	private class CoordinateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e){
			
			for(Object[] o : gameModel.getCommands()){
				if(o[0] == Commands.MoveShip && o[2] == gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex())){
					gameModel.getCommands().remove(o);
					if(gameModel.debugMode())System.out.println("Had to remove the last move command to add the new one!");
				}
			}
			
			gameModel.addCommand(Commands.MoveShip, map.getComCoord(), gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex()));
			if(gameModel.debugMode())System.out.println("Command added!");
		}
		
	}
	
	/**
	 * This is the update method used since this class implements observable, this looks at the keys that changed and updates the proper portions of the screen.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		String change = (arg1 instanceof String)?(String)arg1:null;
		
		if(gameModel.debugMode())System.out.println("Gui is doing something!");
		
		if(change!=null){
			switch(change){
			case"SHIPMOVED":
				map.refreshHighlights();
				refreshShipList();
				break;
			case"SHIPMOVECOMPLETE":
				refreshObjectList();
				break;
			case"UPDATESHIPLIST":
				refreshShipList();
				break;
			}
		}
		
	}

}
