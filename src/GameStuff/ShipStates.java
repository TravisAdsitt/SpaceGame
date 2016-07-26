package GameStuff;

public enum ShipStates {
	LANDED(true,true,false,false),
	FLYING(false,false,true,false),
	ORBITING(false,true,true,true);
	
	
	private boolean canMine, canVac, canMoveSector, canLand;
	
	ShipStates(boolean canMine, boolean canVac, boolean canMoveSector, boolean canLand){
		this.canMine = canMine;
		this.canVac = canVac;
		this.canMoveSector = canMoveSector;
		this.canLand = canLand;
		
	}
	
	/**
	 * @return the canLand
	 */
	public boolean canLand() {
		return canLand;
	}
	/**
	 * @return the canMine
	 */
	public boolean canMine() {
		return canMine;
	}

	/**
	 * @return the canVac
	 */
	public boolean canVac() {
		return canVac;
	}

	/**
	 * @return the canMoveSector
	 */
	public boolean canMoveSector() {
		return canMoveSector;
	}
}
