package objects;

import javafx.scene.canvas.GraphicsContext;
import objects.interfaces.Drawable;
import objects.interfaces.Interactions;
import objects.interfaces.Timer;

public class Object implements Interactions,Drawable,Timer {

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
	
	public Object(double x, double y, double width, double height) {
		 this.x=x;
		 this.y=y;
		 this.width =width;
		 this.height = height;
	}
	
	@Override
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow) {
		gc.fillRect(x, y, width, height);
	}
}
