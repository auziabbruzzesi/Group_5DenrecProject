package model;

public class EstuaryHP {

	private int HP = 50; // initial HP of estuary
	public static final int concreteDecInterval = 10;
	public static final int oysterDecInterval = 5;
	private boolean isPassed = false; // has the line reached the threshold? 

	// getters and setters

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	
	/**
	 * Erode: moves the shoreline west(toward boxes) - once the shoreline passes the gabians
	 * and/or concrete walls game over. HP decrement interval depends on the box a wave hits
	 * 
	 */
/**	public void erode() {
		// Point shoreLine = new Point();
		Wave wave = new Wave(getCurrentPos());
		
		Box barrier = new Box(getCurrentPos());
		// Box oyster = new Box(getCurrentPos());
		// Box concrete = new Box(getCurrentPos());
		// oyster.setH(HoldingType.OYSTER);
		// concrete.setH(HoldingType.CONCRETE);

		// or while isPassed = false;
		//while (EstuaryHP.HP != 0) {
		while(!isPassed){
			

			if (wave.getCurrentPos() == barrier.getPosition()) {
				
				barrier.getH();
				if (barrier.getH() == HoldingType.CONCRETE) {
					HP = HP - concreteDecInterval;
					int currentCount = barrier.getCapacity();
					currentCount = barrier.getCount() - 1;
				} else if(barrier.getH() == HoldingType.OYSTER) {
					// oyster. health decrements by 5 since gabians are stronger

					HP = HP - oysterDecInterval;
				}
				

			
				if(barrier.getH() == HoldingType.CONCRETE && barrier.isfull()){
					HP = HP - (concreteDecInterval - 5); // now decrementing by 5 because the wall is made 
				} else if(barrier.getH() == HoldingType.OYSTER && barrier.isfull()){
					HP = HP - (oysterDecInterval - 3); 
					
				}
			}
			HP--;

		}
		if(HP == 0){
		//	gameOver();
		}

	}
	*/

}
