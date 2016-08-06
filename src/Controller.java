import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Controller {
	private Model gameModel;

	public Controller(Model gameModel){
		this.gameModel = gameModel;
		Timer tmr = new Timer(1000,new TickListener());
		tmr.start();
	}
	public void game(){
		if(gameModel.hasNextCommand()){
			
			for(Object[] o : gameModel.getCommands()){
				executeCommand(o);
			}
		}

	}
	public void executeCommand(Object[] currCommand){
		Commands commandString = null;

		if(currCommand[0] instanceof Commands){
			commandString = (Commands) currCommand[0];
			if(gameModel.debugMode())System.out.println("Controller is executing the command " + ((Commands)currCommand[0]) + "!");
			switch(commandString){
			case MoveShip:
				Point dest = (currCommand[1] instanceof Point)?(Point)currCommand[1]:null;
				Ship toMove = (currCommand[2] instanceof Ship)?(Ship)currCommand[2]:null;

				if(dest!=null&&toMove!=null){
					if(!toMove.getCoor().equals(dest)){
						toMove.setState(ShipStates.FLYING);
						
						int x = toMove.getCoor().x + (toMove.getCoor().x>dest.x?-1:toMove.getCoor().x<dest.x?1:0);
						int y = toMove.getCoor().y + (toMove.getCoor().y>dest.y?-1:toMove.getCoor().y<dest.y?1:0);
						
						moveShipFromToSector(toMove,toMove.getCoor(),new Point(x,y));
						toMove.setCoor(new Point(x,y));
						
						gameModel.setKey("SHIPMOVED", toMove);
						
					}else{
						toMove.setState(ShipStates.IDLE);
						gameModel.setKey("SHIPMOVECOMPLETE", toMove);
						gameModel.removeCommand(currCommand);
						if(gameModel.debugMode())System.out.println("Finished moving!");
					}
				}
				break;
			}
		}
		
		
	}
	public void moveShipFromToSector(Ship ship, Point from, Point to){
		Sector fromSector = gameModel.getSector(from),
				toSector = gameModel.getSector(to);
		
		fromSector.removeShip(ship);
		toSector.setShip(ship);
		
		if(gameModel.debugMode())System.out.println("There was a Ship object moved from Sector: " + 
		fromSector.getCoor().x + "," + fromSector.getCoor().y + " To: " +
		toSector.getCoor().x + "," + toSector.getCoor().y);
		
	}
	public void enterSolarSystem(Ship ship, SolarSystem solarSystem){
		//TODO write code to make a ship enter a solar system
	}
	
	public void orbitPlanet(Ship ship, Planet planet){
		//TODO write code to make a ship orbit a planet
	}
	
	public void orbitSun(Ship ship, Sun sun){
		//TODO write code to make a ship orbit a sun
	}
	
	public void mineOre(Ship ship, Planet planet){
		//TODO Write code to mine the planet
	}
	
	public void mineOxygen(Ship ship, Planet planet){
		//TODO write code to mine oxygen from a planet
	}
	
	public void mineHydrogen(Ship ship, Sun sun){
		//TODO write code to mine from a sun
	}
	private class TickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			game();
			
		}
		
	}
}
