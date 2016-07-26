package GameStuff;

import java.awt.Dimension;
import java.util.ArrayList;

public class Ship {
	private String vesselName, currentCommand;
	private ArrayList<Object[]> commandList;
	private final int DEFAULT_MAX_OX = 10000, DEFAULT_MAX_HY = 100000, DEFAULT_MAX_OR = 1000;
	private final int DEFAULT_MINEPOW = 20, DEFAULT_VACPOW = 20;
	private final int DEFAULT_HPS = 1000,DEFAULT_HPT = 10, DEFAULT_OPT = 1;//HPS = Hydrogen per sector; OPT/HPT = Oxygen/Hydrogen per tick
	private final double DEFAULT_SHIP_SPEED = .1; //Sector per Tick
	private int oxygen, hydrogen, ore, minePow, vacPow, level;
	private double  coorX, coorY;
	private boolean isIdle;
	
	/**
	 * Create a ship with the default max except for ore.
	 */
	public Ship(String name, int x, int y){
		oxygen = DEFAULT_MAX_OX;
		hydrogen = DEFAULT_MAX_HY;
		ore = 0;
		level = 1;
		isIdle = true;
		currentCommand = "none";
		vesselName = name;
		coorX = x;
		coorY = y;
		
		commandList = new ArrayList<Object[]>();
		
	}
	/**
	 * Updates hydrogen and oxygen values if we are moving or if we are just sitting.
	 * 
	 */
	public void update(){
		hydrogen -= DEFAULT_HPT/level;
		oxygen -= DEFAULT_OPT/level;
		
		int tempX = (int) coorX, tempY = (int) coorY;
		
		performCommand();
		
		hydrogen -= ((((int)coorX)-tempX)+(((int)coorY)-tempY))==0?0:(DEFAULT_HPS/level);
		
	}
	/**
	 * Given a planet and the amount to mine this method creates a command for the mining operation.
	 * 
	 * @param plan planet to mine
	 * @param amount to mine
	 * @return announcements for the calling method.
	 */
	public String minePlanet(Planet plan, int amount){
		String ret = "Command Added!";
		if(plan.getOre()>0){
			addCommand("Mine", plan, amount);
			isIdle = false;
		}else{
			ret = "No ore!";
		}
		return ret;
	}
	/**
	 * Given a planet and the amount to vacuum this method creates a command for the vacuuming operation.
	 * 
	 * @param plan planet to vacuum
	 * @param amount to vacuum
	 * @return announcements for the calling method
	 */
	public String vacPlanet(Planet plan, int amount){
		String ret = "Command Added!";
		if(plan.isHasAtmosphere()&&plan.getOxygen()>0){
			addCommand("Vacuum", plan, amount);
			isIdle = false;
		}else{
			ret = "No Atmosphere!";
		}
		return ret;
	}
	/**
	 * Given coordinates this will add the command to move to the specified coordinates.
	 * @param x coordinate
	 * @param y coordinate
	 * @return announcements for the calling method
	 */
	public String moveShip(int x, int y){
		String ret = "Command Added!";
		
		int[] coordinates = new int[2];
		
		coordinates[0] = x;
		coordinates[1] = y;
		
		addCommand("Move",coordinates,DEFAULT_SHIP_SPEED*level);
		
		return ret;
	}
	/**
	 * Perform the current command on the list if we have hydrogen otherwise shutdown.
	 */
	public void performCommand(){
		Planet p;
		double amount, obtained;
		
		if(commandList.size()>0){
			Object[] currCommand = commandList.get(0);
			
			String command = (String) currCommand[0];
			
			switch(command){
			case "Mine":
				p = (Planet) currCommand[1];
				amount = (int) currCommand[2];
				
				obtained = (int) p.minePlanet(minePow);
				
				ore += obtained;
				currCommand[2] = amount - obtained;
				
				obtained = 0;
				
				if(amount==0){
					commandList.remove(currCommand);
				}
				
				break;
			case "Vacuum":
				p = (Planet) currCommand[1];
				amount = (int) currCommand[2];
				
				obtained = p.harvestOxygen(vacPow);
				
				oxygen += obtained;
				currCommand[2] = amount - obtained;
				
				obtained = 0;
				
				if(amount==0){
					commandList.remove(currCommand);
				}
				
				break;
			case "Move":
				int x = ((int[])currCommand[1])[0], y = ((int[])currCommand[1])[1];
				amount = (double)currCommand[2];
				
				coorX += (int)coorX<x?amount:(int)coorX>x?-amount:0;
				coorY += (int)coorY<y?amount:(int)coorY>y?-amount:0;
				
				if((int)coorX==x&&(int)coorY==y){
					commandList.remove(currCommand);
				}
				break;
			}
		}
	}
	/**
	 * Formats the parameters to conform to a command array.
	 * 
	 * @param com Command string
	 * @param obj Object performing operation on
	 * @param amount Amount to perform
	 */
	public void addCommand(String com, Object obj, double amount){
		Object[] command = new Object[3];
		
		command[0] = com;
		command[1] = obj;
		command[2] = amount;
		
		commandList.add(command);
	}
	/**
	 * To get the x coordinate in integer form
	 * 
	 * @return int of x coordinate
	 */
	public int getX(){
		return (int) coorX;
	}
	/**
	 * To get the y coordinate in integer form
	 * 
	 * @return int of y coordinate
	 */
	public int getY(){
		return (int) coorY;
	}
	
	public String toString(){
		String ret = "";
		
		ret = String.format("Ship [id = %5s, oxy = %5s, hyd = %5s, ore = %5s, sector = %5s, action = %7s]", vesselName, oxygen, hydrogen, ore, "S" +(int)coorX+(int)coorY, commandList.size()>0?(String)commandList.get(0)[0]:"Idle");
		
		return ret;
	}
	
}
