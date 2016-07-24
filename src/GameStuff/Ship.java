package GameStuff;

import java.util.ArrayList;

public class Ship {
	private String vesselName, currentCommand;
	private ArrayList<Object[]> commandList;
	private final int DEFAULT_MAX_OX = 10000, DEFAULT_MAX_HY = 100000, DEFAULT_MAX_OR = 1000;
	private final int DEFAULT_MINEPOW = 20, DEFAULT_VACPOW = 20;
	private final int DEFAULT_HPS = 1000,DEFAULT_HPT = 100, DEFAULT_OPT = 10;//HPS = Hydrogen per sector; OPT/HPT = Oxygen/Hydrogen per tick
	private final double DEFAULT_SPT = .1;
	private int oxygen, hydrogen, ore, minePow, vacPow, level, coorX, coorY, destX, destY;
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
		coorX = destX = x;
		coorY = destY = y;
		
		commandList = new ArrayList<Object[]>();
		
	}
	
	public void update(){
		//TODO subtract OPT and HPT, if moving subtract hydrogen
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
	 * Perform the current command on the list.
	 */
	public void performCommand(){
		Planet p;
		int amount, obtained;
		
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
				break;
			case "Vacuum":
				p = (Planet) currCommand[1];
				amount = (int) currCommand[2];
				
				obtained = p.harvestOxygen(vacPow);
				
				oxygen += obtained;
				currCommand[2] = amount - obtained;
				
				obtained = 0;
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
	public void addCommand(String com, Object obj, int amount){
		Object[] command = new Object[3];
		
		command[0] = com;
		command[1] = obj;
		command[2] = amount;
		
		commandList.add(command);
	}
	
}
