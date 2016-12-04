package model;

public enum HoldingType {
	
	EMPTY(0),
	CONCRETE(1),
	OYSTER(2),
	TRASH(3),
	RECYCLING(4), 
	BOX(5);
	
	private int rank;
	HoldingType(int rank){
		this.rank = rank;
	}
	public int getRank(){
		return rank;
	}


}
