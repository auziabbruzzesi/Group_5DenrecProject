package model;

/**
 * 
 * @author Auzi
 * an enum to tell us what a player or box is holding
 *
 */
public enum HoldingType {
	
	EMPTY(0),
	
	CONCRETE(1),
	TUTORIAL_C(1),
	
	OYSTER(2),
	TUTORIAL_O(2),
	
	TRASH(3),
	RECYCLING(4), 
	BOX(5),
	TUTORIAL_WAVE(6);
	
	private int rank;
	HoldingType(int rank){
		this.rank = rank;
	}
	/**
	 * 
	 * @return rank
	 */
	public int getRank(){
		return rank;
	}


}
