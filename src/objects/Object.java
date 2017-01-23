package objects;

import objects.interfaces.Drawable;
import objects.interfaces.Interactions;

public class Object implements Interactions,Drawable {

	private double x;
	private double y;
	private double z;
	
	private double width;
	private double height;

	public double getX() {return x;}
	public double getY() {return y;}
	public double getZ() {return z;}
	public double getWidth() {return width;}
	public double getHeight() {	return height;}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub		
	}


}
