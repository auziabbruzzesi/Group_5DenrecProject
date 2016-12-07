package model;
/**
 * 
 * @author Auzi
 *
 */
public class HealthBar extends GameObject {
	private int health = 100; //default
	private int startHeight;
	
	public HealthBar(int w, int h){
		this.setWidth(w);
		this.setHeight(h);	
		this.setStartingY(0);
		this.setInsideHeight(h);
		startHeight = h;
	}
	public void damage(int damage){
		health = health - damage;
		double decDamage = (double)damage/100;
		this.setStartingY(this.getStartingY() + (decDamage * this.getHeight()));
		this.setInsideHeight(this.getInsideHeight() - (decDamage * this.getHeight()));
		
	}
	
	public void reset(){
		setStartingY(0);
		setInsideHeight(startHeight);
	}

	public int getHealth(){
		return this.health;
	}

}
