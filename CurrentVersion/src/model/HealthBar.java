package model;
/**
 * 
 * @author Auzi
 *
 */
public class HealthBar {
	private int initialHealth = 100;
	private int height;
	private int width;
	
	//Constructor
	public HealthBar(int initialHealth){
		this.initialHealth = initialHealth;
	}
	
	
	
	
	/**
	 * 
	 * @param the amount of damage done to whatever
	 * 	has the healthbar
	 */
	public void decreaseHealth(int damage){
		
		
	}
	
	/**
	 * 
	 * @param the amount adding to the health bar
	 */
	public void increaseHealth(int increase){
		
	}

	public int getInitialHealth() {
		return initialHealth;
	}

	public void setInitialHealth(int initialHealth) {
		this.initialHealth = initialHealth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	
	
	
	
	

}
