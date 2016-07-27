package GameStuff;

import java.awt.Dimension;
import java.util.ArrayList;
/**
 * Class that handles the ship object
 * 
 * @author Travis Adsitt
 *
 */
public class Ship {
	private String vesselName, currentCommand;
	private ShipStates state;
	private ArrayList<Object[]> commandList;
	private final int DEFAULT_MAX_OX = 10000, DEFAULT_MAX_HY = 100000, DEFAULT_MAX_OR = 1000;
	private final int DEFAULT_MINEPOW = 20, DEFAULT_VACPOW = 20;
	private final int DEFAULT_HPS = 1000,DEFAULT_HPT = 10, DEFAULT_OPT = 1;//HPS = Hydrogen per sector; OPT/HPT = Oxygen/Hydrogen per tick
	private final double DEFAULT_SHIP_SPEED = .5, DEFAULT_LANDING_SPEED = 10000; //Sector per Tick
	private int oxygen, hydrogen, ore, minePow, vacPow, level, landingProgress;
	private double  coorX, coorY;
	private boolean isIdle;
	private Planet plan;
	private Sun sun;
	
	
	/**
	 * Create a ship with the default max except for ore.
	 */
	public Ship(String name, int x, int y){
		oxygen = DEFAULT_MAX_OX;
		hydrogen = DEFAULT_MAX_HY;
		ore = 0;
		landingProgress = 0;
		level = 1;
		isIdle = true;
		currentCommand = "none";
		vesselName = name;
		state = ShipStates.FLYING;
		
		coorX = x;
		coorY = y;
		
		commandList = new ArrayList<Object[]>();
		
	}
	/**
	 * Updates hydrogen and oxygen values if we are moving or if we are just sitting.
	 * 
	 */
	public void update(){
		if(state == ShipStates.LANDED && plan.isHasAtmosphere()){
			performCommand();
		}else{
			hydrogen -= DEFAULT_HPT/level;
			oxygen -= DEFAULT_OPT/level;

			int tempX = (int) coorX, tempY = (int) coorY; //storing the coordinates for movement

			performCommand();

			hydrogen -= ((((int)coorX)-tempX)+(((int)coorY)-tempY))==0?0:(DEFAULT_HPS/level);// if we have moved a sector we want to subtract more hydrogen for using the rockets.
		}

		
	}
	public void leaveOrbit(){
		commandList.clear();
		state = ShipStates.FLYING;
	}
	public String orbitSun(Sun sun){
		String ret = "Command Added!";
		if(state == ShipStates.FLYING){
		this.sun = sun;
		
		setState(ShipStates.ORBITING_SUN);
		}else{
			ret = "You need to be flying!";
		}
		return ret;
	}
	public String orbitPlanet(Planet plan){
		String ret = "Command Added!";
		
		if(state == ShipStates.FLYING){
			this.plan = plan;
			
			setState(ShipStates.ORBITING_PLANET);
		}else{
			ret = "You need to be flying!";
		}
		
		return ret;
	}
	public void setState(ShipStates state){
		this.state = state;
	}
	public ShipStates getState(){
		return state;
	}
	/**
	 * Create a command to land on a planet
	 * 
	 * @param plan planet to land on
	 * @return announcements for the calling method
	 */
	public String landOnPlanet(){
		String ret = "Command Added!";
		if(plan.getOre()>100){
			addCommand("Land",plan,DEFAULT_LANDING_SPEED*level);
		}else{
			ret = "Not Enough Room!";
		}
		return ret;
	}
	/**
	 * Given a planet and the amount to mine this method creates a command for the mining operation.
	 * 
	 * @param plan planet to mine
	 * @param amount to mine
	 * @return announcements for the calling method.
	 */
	public String minePlanet(int amount){
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
	public String vacPlanet(int amount){
		String ret = "Command Added!";
		if(plan.isHasAtmosphere()&&plan.getOxygen()>0){
			addCommand("Vacuum", plan, amount);
			isIdle = false;
		}else{
			ret = "No Atmosphere!";
		}
		return ret;
	}
	public String vacSun(int amount){
		String ret = "Command Added!";
		if(sun.getHydrogen()>0){
			addCommand("Vacuum", sun, amount);
			isIdle = false;
		}else{
			ret = "No Atmosphere!";
		}
		return ret;
	}
	/**
	 * Given coordinates this will add the command to move to the specified coordinates.
	 * 
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
				if(state.canMine()){
					p = (Planet) currCommand[1];
					amount = (double) currCommand[2];

					obtained = (int) p.minePlanet(DEFAULT_MINEPOW*level);

					ore += obtained>amount?amount:obtained;
					
					currCommand[2] = amount - obtained;

					obtained = 0;
					
					commandList.set(0, currCommand);
					
					if(amount<=0||p.getOre()==0){
						commandList.remove(currCommand);
					}
				}else{
					commandList.remove(currCommand);
				}

				
				break;
			case "Vacuum":
				if(state == ShipStates.ORBITING_PLANET || state == ShipStates.LANDED){
					p = (Planet) currCommand[1];
					amount = (double) currCommand[2];

					obtained = p.harvestOxygen(DEFAULT_VACPOW*level);

					oxygen += obtained>amount?amount:obtained;
					currCommand[2] = amount - obtained;

					obtained = 0;

					if(amount==0){
						commandList.remove(currCommand);
					}
				}else if(state == ShipStates.ORBITING_SUN){
					Sun s = (Sun) currCommand[1];
					amount = (double) currCommand[2];

					obtained = s.harvestHydrogen(DEFAULT_VACPOW*level);

					hydrogen += obtained>amount?amount:obtained;
					currCommand[2] = amount - obtained;

					obtained = 0;

					if(amount==0){
						commandList.remove(currCommand);
					}
				}else{
					commandList.remove(currCommand);
				}
				
				
				break;
			case "Move":
				if(state.canMoveSector()){
					int x = ((int[])currCommand[1])[0], y = ((int[])currCommand[1])[1];
					amount = (double)currCommand[2];

					coorX += (int)coorX<x?amount:(int)coorX>x?-amount:0;
					coorY += (int)coorY<y?amount:(int)coorY>y?-amount:0;

					if((int)coorX==x&&(int)coorY==y){
						commandList.remove(currCommand);
					}
				}else{
					commandList.remove(currCommand);
				}
				
				break;
			case "Land":
				if(state.canLand()){
					landingProgress += (double) currCommand[2];
					Planet plan = (Planet) currCommand[1];
					if(plan.getOxygen()<landingProgress){
						state = ShipStates.LANDED;
						commandList.remove(currCommand);
						this.plan = plan;
						landingProgress = 0;
					}
					
				}else{
					commandList.remove(currCommand);
				}
				
				
				
				
				
				//if(landingProgress>)
				
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
		
		ret = String.format("Ship [id = %5s, oxy = %5s, hyd = %5s, ore = %5s, sector = %5s, action = %7s, state = %10s]", 
				vesselName, oxygen, hydrogen, ore, 
				"S" +(int)coorX+(int)coorY, 
				commandList.size()>0?(String)commandList.get(0)[0]:"Idle", 
						state.toString().toLowerCase());
		
		return ret;
	}
	
}
