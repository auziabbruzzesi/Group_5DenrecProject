package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controller.Controller.*;
import model.HoldingType;

public class MouseEventListener implements MouseListener {

	@Override
	public void mousePressed(MouseEvent e) {
	
		if(e.getComponent() instanceof button){
			System.out.println("button listener registered a click");
			button b = (button)( e.getComponent() );
//			int Xb = b.getX();
//			int Yb = b.getY();
			
			if(b.getHoldingType() == HoldingType.CONCRETE){
				System.out.println("Concrete button clicked");
				System.out.println(this);
			}
			
			else if(b.getHoldingType() == HoldingType.OYSTER){
				System.out.println("Oyster button clicked");
				System.out.println(this);
			}
			
			else if(b.getHoldingType() == HoldingType.BOX){
				System.out.println("Box button clicked");
				System.out.println(this);
			}
			
			Controller.updatePlayerMV(e.getLocationOnScreen());
			//if distance to button greater than some number (e.g. 2 pixels)
//			if(){
//				
//				
//				// move toward it
//			}
			//then, regardless, if player not holding an object
				//call pickup()
			//if player IS holding an object
				//display help saying you're holding an object already, press some button to put it down
			 
		}//end if button
		else{
			Controller.updatePlayerMV(e.getPoint());
		}
	 }

	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}