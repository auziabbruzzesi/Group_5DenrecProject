package model;
/**
 * 
 * @author Auzi
 *
 */
public class HealthBar {
	//%
	private int health = 100; //default
	private int width = 100;
	private int height = 400;
	private double insideHeight = height;
	private double startingY = 0;

	
	
	public HealthBar(int width, int health){
		this.width = width;
		this.height = height;
	
				
	}
	public void damage(int damage){
		health = health - damage;
		double decDamage = (double)damage/100;
		System.out.println(damage + " percent damage to "+ this.height + " = " + (decDamage * height));
		this.startingY =  (startingY + (decDamage * height));
		this.insideHeight =  (insideHeight - (decDamage * height));
		
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	public int getHeight(){
		return this.height;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	public int getWidth(){
		return this.width;
	}
	public double getStartingY() {
		return startingY;
	}
	public int getHealth(){
		return this.health;
	}
	public double getInsideHeight(){
		return this.insideHeight;
	}
	public void setInsideHeight(double insideHeight){
		this.insideHeight = insideHeight;
	}
}
