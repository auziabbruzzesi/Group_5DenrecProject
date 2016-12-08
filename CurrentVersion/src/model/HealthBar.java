package model;
/**
 * 
 * @author Auzi
 * a healthbar to track the health of the estuary
 */
public class HealthBar extends GameObject {
	private int health = 100; //default
	private int startHeight;
	/**
	 * @author Auzi
	 * @param w - width
	 * @param h - height
	 * 
	 */
	public HealthBar(int w, int h){
		this.setWidth(w);
		this.setHeight(h);	
		this.setStartingY(0);
		this.setInsideHeight(h);
		startHeight = h;
	}
	/**
	 * @author Auzi
	 * @param damage
	 * purpose: translate an integer damage into a change in the components
	 * of the health bar. 5 damage brings the healthbar down 5%
	 */
	public void damage(int damage){
		health = health - damage;
		double decDamage = (double)damage/100;
		this.setStartingY(this.getStartingY() + (decDamage * this.getHeight()));
		this.setInsideHeight(this.getInsideHeight() - (decDamage * this.getHeight()));
		
	}
	/**
	 * @author Auzi
	 * purpose: reset the healthbar back to 100%
	 */
	public void reset(){
		this.health = 100;
		setStartingY(0);
		setInsideHeight(startHeight);
	}
	/**
	 * 
	 * @return percent health
	 */
	public int getHealth(){
		return this.health;
	}

}
