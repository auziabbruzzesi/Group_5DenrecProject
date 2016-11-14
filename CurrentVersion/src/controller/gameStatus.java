package controller;

public class gameStatus {
	public static final gameStatus WIN = new gameStatus(status.WIN);
	public static final gameStatus LOSE = new gameStatus(status.LOSE);
	public static final gameStatus IN_PROGRESS = new gameStatus(status.IN_PROGRESS);
	
	private status currStatus;
	
	gameStatus(status a){
		currStatus = a;
	}
}
