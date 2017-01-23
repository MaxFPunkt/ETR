package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.interfaces.Drawable;
import objects.interfaces.Interactions;

public class Object implements Interactions, Drawable {

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