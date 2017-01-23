package objects;

import objects.interfaces.Drawable;
import objects.interfaces.Interactions;

public class Object implements Interactions,Drawable {

	private double x;
	private double y;
	private double z;
	
	private double width;
	private double height;
	
	/**
	 * 
	 * @return the x Position 
	 */
	public double getX() {return x;}
	/**
	 * 
	 * @return the y Position 
	 */
	public double getY() {return y;}
	/**
	 * 
	 * @return the z Position 
	 */
	public double getZ() {return z;}
	/**
	 * 
	 * @return the width of object 
	 */
	public double getWidth() {return width;}
	/**
	 * 
	 * @return the height of object 
	 */
	public double getHeight() {	return height;}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub		
	}

}
