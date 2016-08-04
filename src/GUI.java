

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
	private boolean systemSelected;
	private JLabel announcements;
	private Ship selectedShip;
	private JPanel buttons;
	private JPanel northPanel, southPanel, eastPanel, westPanel, centerPanel;
	JButton mine, vac, takeoff, land, exitorbit, exitSystem;
	private Model gameModel;
	
	public GUI(Model model){
		
		gameModel = model;
		
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
	 * Used for updating and checking for ship commands.
	 */
	@SuppressWarnings("unchecked")
	public void update(){
		
		/*map.update(fleets.get(0).getFleet());
		
		
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
		*/
		//==========Buttons Panel=====================
		switch(gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex()).getState()){
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
	private class ButtonsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			switch(event.getActionCommand()){
			case"Land":
				
				break;
			case"Mine":

				break;
			case"Take Off":
				
				break;
			case"Vacuum":
				
				break;
			case"Exit Orbit":
				
				break;
			case"Exit System":
				
				break;
			}


		}
		
	}
	//private class RefreshGUI implements Action
	private class CoordinateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e){
			gameModel.addCommand("Move", map.getComCoord(), gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex()));
			System.out.println(gameModel.getCurrentPlayer().getShips().get(shipList.getSelectedIndex()).getId());
		}
		
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		String change = (arg1 instanceof String)?(String)arg1:null;
		
		if(change!=null){
			switch(change){
			case"SHIPMOVED":
				map.refreshHighlights();
				shipList.invalidate();
				break;
			}
		}
		
	}

}
