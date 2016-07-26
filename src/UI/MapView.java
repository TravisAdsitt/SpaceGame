package UI;

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

import GameStuff.Sector;

public class MapView extends JPanel{

	private final int BLOCK_SIDE = 7;
	private int mapW, mapH;
	private Sector[][] mapData;
	private Random ran;
	private int hX,hY;
	private ArrayList<int[]> hCoor;
	private boolean rightClicked;
	
	public MapView(Sector[][] mapData){
		this.mapData = mapData;
		
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
	 * Get the highlighted squares x value.
	 * 
	 * @return highlighted squares x value.
	 */
	public int getHX(){
		return hX;
	}
	/**
	 * Get the highlighted squares y value.
	 * 
	 * @return highlighted squares y value.
	 */
	public int getHY(){
		return hY;
	}
	/**
	 * Returns an object with the coordinates of a command if there was a right click event on the map
	 * @return int[] object with coordinates in it
	 */
	public int[] getComCoord(){
		int[] ret = new int[2];
		
		if(rightClicked){
			ret[0] = getHX();
			ret[1] = getHY();
		}else{
			ret[0] = -1;
			ret[1] = -1;
		}
		
		
		hX = hY = -1;
		rightClicked = false;
		
		return ret;
	}
	public void refreshHighlights(){
		hCoor.clear();
		
		int[] coor = new int[3]; 
		
		coor[0] = hX;
		coor[1] = hY;
		coor[2] = 0;
		
		hCoor.add(coor);
		
		
	}
	/**
	 * Draws the stars and highlights
	 */
	public void paintComponent(Graphics g){
		refreshHighlights();
		for(int x = 0 ; x<mapW ; x+=BLOCK_SIDE){
			for(int y = 0 ; y<mapH ; y+=BLOCK_SIDE){
				
				Random sectorRan = new Random(Integer.parseInt(x+""+y));
				
				int numStars = mapData[x/BLOCK_SIDE][y/BLOCK_SIDE].getNumSolarSystem();
				
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
						g.setColor(new Color(255,255,0,100));
						g.fillRect(i[0]*BLOCK_SIDE, i[1]*BLOCK_SIDE, BLOCK_SIDE, BLOCK_SIDE);
						break;
					case 1:
						
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
