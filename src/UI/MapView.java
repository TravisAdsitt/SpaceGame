package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;

import GameStuff.Sector;

public class MapView extends JPanel{

	private final int INI_HEIGHT = 600, INI_WIDTH = 800;
	private int blockW, blockH;
	private Sector[][] mapData;
	private Random ran;
	private int hX,hY;
	
	public MapView(Sector[][] mapData){
		this.mapData = mapData;
		
		ran = new Random();
		
		blockW = getWidth()/(mapData.length);
		blockH = getHeight()/(mapData.length);
		
		setPreferredSize(new Dimension(INI_WIDTH-blockW*2, INI_HEIGHT-blockH*2));
		
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
	 * Draws the stars and 
	 */
	public void paintComponent(Graphics g){
		
		for(int x = 0 ; x<blockW*(mapData.length-1) ; x+=blockW){
			for(int y = 0 ; y<blockH*(mapData.length-1) ; y+=blockH){
				
				Random sectorRan = new Random(Integer.parseInt(x+""+y));
				
				int numStars = mapData[x/blockW][y/blockH].getNumSolarSystem();
				
				g.setColor(Color.black);
				
				g.fillRect(x, y, blockW, blockH);
				
				g.setColor(Color.white);
				
				
				
				for(int i = 0; i<numStars ; i++){
					int sX = sectorRan.nextInt(blockW) + x,
							sY = sectorRan.nextInt(blockH) + y;
					
					g.drawRect(sX, sY, 1, 1);
					
				}
				
				if(x/blockW == hX && y/blockH == hY){
					g.setColor(new Color(255,255,0,175));
					
					g.fillRect(x, y, blockW, blockH);
					
				}
				
			}
		}
		
	}
	
	private class MouseMapListener implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {
			hX = (int) arg0.getPoint().getX()/blockW;
			hY = (int) arg0.getPoint().getY()/blockH;
			
			//System.out.println(hX + " : " + hY);
			
			//System.out.println(mapData[hX][hY].getNumSolarSystem());
			
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
