

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * This is to handle the drawing of the map and the highlighted squares.
 * 
 * 
 * @author Travis Adsitt
 *
 */


public class MapView extends JPanel{

	private final int BLOCK_SIDE = 7;
	private int mapW, mapH;
	private Sector[][] mapData;
	private Random ran;
	private int hX,hY;
	private ArrayList<Ship> ships;
	private ArrayList<int[]> hCoor;
	private boolean rightClicked;
	private Model gameModel;
	
	public MapView(Model gameModel){
		this.gameModel = gameModel;
		
		this.mapData = gameModel.getUniverseSectorArray();
		this.ships = gameModel.getCurrentPlayer().getShips();
		
		hCoor = new ArrayList<int[]>();
		
		ran = new Random();
		rightClicked = false;
		
		mapW = BLOCK_SIDE*(mapData[0].length);
		mapH = BLOCK_SIDE*(mapData[0].length);		
		
		setPreferredSize(new Dimension(mapW, mapH));
		setDoubleBuffered(true);
		
		hX = -1;
		hY = -1;
		
		this.addMouseListener(new MouseMapListener());
		
	}
	/**
	 * Returns an object with the coordinates of a command if there was a right click event on the map
	 * @return int[] object with coordinates in it
	 */
	public int[] getComCoord(){
		int[] ret = new int[2];
		
		if(rightClicked){
			ret[0] = hX;
			ret[1] = hY;
		}else{
			ret[0] = -1;
			ret[1] = -1;
		}
		
		
		hX = hY = -1;
		rightClicked = false;
		
		return ret;
	}
	/**
	 * Used to refresh the highlighted squares on the map
	 */
	public synchronized void refreshHighlights(){
		hCoor.clear();
		
		int[] coor = new int[3]; 
		
		coor[0] = hX;
		coor[1] = hY;
		coor[2] = 0;
		
		hCoor.add(coor);
		
		for(Ship s : ships){
			int[] sH = new int[3];
			
			
			sH[0] = s.getCoor().x;
			sH[1] = s.getCoor().y;
			sH[2] = 1;
			
			hCoor.add(sH);
		}
		
		
	}
	public void update(ArrayList<Ship> ships){
		this.ships = ships;
		
		refreshHighlights();
		repaint();
		
	}
	/**
	 * Draws the stars and highlights
	 */
	public void paintComponent(Graphics g){
		
		
		for(int x = 0 ; x<mapW ; x+=BLOCK_SIDE){
			for(int y = 0 ; y<mapH ; y+=BLOCK_SIDE){
				
				Random sectorRan = new Random(Integer.parseInt(x+""+y));
				
				int numStars = mapData[x/BLOCK_SIDE][y/BLOCK_SIDE].getAllSolarSystems().size();
				
				g.setColor(Color.black);
				
				g.fillRect(x, y, BLOCK_SIDE, BLOCK_SIDE);
				
				g.setColor(Color.white);
				
				
				
				for(int i = 0; i<numStars ; i++){
					int sX = sectorRan.nextInt(BLOCK_SIDE) + x,
							sY = sectorRan.nextInt(BLOCK_SIDE) + y;
					
					g.drawRect(sX, sY, 1, 1);
					
				}
				for(int[] i : hCoor){
					switch(i[2]){
					case 0:
						g.setColor(new Color(200,200,0,100));
						g.fillRect(i[0]*BLOCK_SIDE, i[1]*BLOCK_SIDE, BLOCK_SIDE, BLOCK_SIDE);
						break;
					case 1:
						g.setColor(new Color(0,200,0,100));
						g.fillRect(i[0]*BLOCK_SIDE, i[1]*BLOCK_SIDE, BLOCK_SIDE, BLOCK_SIDE);
						break;
					}
				}

			}
		}

	}
	
	private class MouseMapListener implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {
			hX = (int) arg0.getPoint().getX()/BLOCK_SIDE;
			hY = (int) arg0.getPoint().getY()/BLOCK_SIDE;
			
			//System.out.println(mapData[hX][hY].getNumSolarSystem());
			
			switch(arg0.getButton()){
			case 3:
				rightClicked = true;
				refreshHighlights();
				repaint();
				break;
			}
			
			repaint();
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}
