package controller;

import java.util.EventObject;

public class statusEvent extends EventObject{
	private gameStatus gStatus;
	
	public statusEvent(Object source, gameStatus gS){
		super(source);
		gStatus = gS;
	}
	
	public gameStatus getGameStatus(){
		return gStatus;
	}
}
