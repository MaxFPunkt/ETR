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
	/**
	 * 
	 * @param windowWidth define the viewPort width
	 * @param windowHeight define the viewPort height
	 * @return the x position form the object on the viewPort
	 */
	public double getDrawX(double windowHeight, double xOffsetWindow){
		double i=getZ()*(29./44.);
		double drawXNormal=i+getX()/880.*(3200-2*i);
		return (drawXNormal/1600.)*windowHeight/900.*1600+xOffsetWindow/900*3200;
	}
	/**
	 * 
	 * @param windowWidth define the viewPort width
	 * @param windowHeight define the viewPort height
	 * @return the y position form the object on the viewPort
	 */
	public double getDrawY(double windowHeight){
		double i=getZ()/1760.*380.;
		double k=120-getZ()/1760.*120;
		double drawYNormal=k+ getY()/880.*(900-i-k);
		return drawYNormal/900.*windowHeight ;
		
	}
	/**
	 * 
	 * @param windowWidth define the viewPort width
	 * @param windowHeight define the viewPort height
	 * @return the width form the object on the viewPort
	 */
	public double getDrawWidth(double windowHeight){
		double i=getZ()*(29./44.);
		return getWidth()/3200.*(3200-2*i)
				/1600*(windowHeight/900*1600);
	}
	/**
	 * 
	 * @param windowWidth define the viewPort width
	 * @param windowHeight define the viewPort height
	 * @return the height form the object on the viewPort
	 */
	public double getDrawHeight( double windowHeight){
		double i=getZ()/1760.*380.;
		double k=120-getZ()/1760.*120;
		return getHeight()/900.*(900-i-k)
				/900*windowHeight;
	}	
	public Object(double x, double y, double z, DoubleProperty width, DoubleProperty height, Image img) {
		 this(x,y,z,width,height);
		 this.img = img;
	}
	public Object(double x, double y, double z, double width, double height, Image img) {
		 this(x,y,z,width,height);
		 this.img = img;
	}
	public Object(double x, double y, double z, double width, double height) {
		 this.x=x;
		 this.y=y;
		 this.z=z;
		 this.width= new SimpleDoubleProperty(width);
		 this.height= new SimpleDoubleProperty(height);
	}
	public Object(double x, double y, double z, DoubleProperty width, DoubleProperty height) {
		 this.x=x;
		 this.y=y;
		 this.z=z;
		 this.width=width;
		 this.height=height;
	}
	
	@Override
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow) {
		gc.drawImage(img,getDrawX( windowHeight,xOffsetWindow), getDrawY( windowHeight), getDrawWidth(windowHeight),getDrawHeight( windowHeight));
	}
	public boolean ifHit(double XAxis, double YAxis, double windowHeight, double xOffsetWindow){
		if(XAxis > getDrawX( windowHeight,xOffsetWindow) 
				&& XAxis < getDrawX( windowHeight,xOffsetWindow)+getDrawWidth(windowHeight) 
				&& YAxis > getDrawY( windowHeight) 
				&& YAxis < getDrawY( windowHeight)+getDrawHeight( windowHeight)){
			return true;
		}
		return  false;
	}
	@Override
	public boolean canGrab(){return true;}
	
	@Override
	public void grab(Interface intface) {
		Interactions.super.grab(intface);
		intface.inventory.add(this);
		intface.resetActiveAction();
	}
}