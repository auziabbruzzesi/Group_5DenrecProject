package model;
/**
 * 
 * @author Auzi
 *
 */
public class HealthBar {
	private int health = 100;
	private int height = 100;
	private int width = 25;
	private int innerHeight = height;
	
	//Constructor
	public HealthBar(int health){
		this.health = health;
	}
	
	
	
	
	/**
	 * 
	 * @param the amount of damage done to whatever
	 * 	has the healthbar
	 */
	public void decreaseHealth(int damage){
		innerHeight -= damage;
		
	}
	
	/**
	 * 
	 * @param the amount adding to the health bar
	 */
	public void increaseHealth(int increase){
		
	}

	public int gethealth() {
		return health;
	}

	public void sethealth(int health) {
		this.health = health;
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
	
	public void setInnerHeight(int height){
		this.innerHeight = height;
	}
	
	public int getInnerHeight(){
		return this.innerHeight;
	}
	
	
	
	
	
	

}
