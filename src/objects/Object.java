package objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.interfaces.Drawable;
import objects.interfaces.Interactions;
import objects.interfaces.Timer;

public class Object implements Interactions,Drawable,Timer {

	private double x;
	private double y;
	private double z;
	
	private DoubleProperty width;
	private DoubleProperty height;
	
	private Image img;
	
	/**
	 * 
	 * @return the x Position 
	 */
	public Image getImage(){ return img; }
	
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
	public double getWidth() {return width.get();}
	/**
	 * 
	 * @return the height of object 
	 */
	public double getHeight() {	return height.get();}
	
	public Object(double x, double y, double z, DoubleProperty width, DoubleProperty height, Image img) {
		 this(x,y,z,width,height);
		 this.img = img;
	}
	public Object(double x, double y, double z, double width, double height) {
		 this.x=x;
		 this.y=y;
		 this.width= new SimpleDoubleProperty(width);
		 this.height= new SimpleDoubleProperty(height);
	}
	public Object(double x, double y, double z, DoubleProperty width, DoubleProperty height) {
		 this.x=x;
		 this.y=y;
		 this.width=width;
		 this.height=height;
	}
	
	@Override
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow) {
		gc.strokeRect(x, y, width.get(), height.get());
		gc.drawImage(img,x,y, width.get(), height.get());
	}
	
	//y+height x+width x,y y,x x-width y-height 
	public void ifHit(double XAxis, double YAxis){
		if(XAxis > x && XAxis < x+width.get() && YAxis > y && YAxis < y+height.get())
		{System.out.println("getroffen");}
//		else
//		{System.out.println("nicht getroffen");}
	}
}

