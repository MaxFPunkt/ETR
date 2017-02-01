package objects;

import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.interfaces.Drawable;
import objects.interfaces.Interactions;
import objects.interfaces.Timer;


public class Object extends Interactions implements Drawable, Timer, Comparable<Object> {
	private double x;
	private double y;
	private double z;
	
	private ArrayList<Object> childs;
	
	private DoubleProperty width;
	private DoubleProperty height;
	private Image img;
	

	public Image getImage(){ return img; }
	public void setImg(Image image) { this.img = image; }
	
	public ArrayList<Object> getChilds(){ return childs; }
	
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
	public void setX(double x) {this.x = x;}
	public void setY(double y) {this.y = y;}
	public void setZ(double z) {this.z = z;}
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
		 this(x,y,z,new SimpleDoubleProperty(width),new SimpleDoubleProperty(height));
	}
	public Object(double x, double y, double z, DoubleProperty width, DoubleProperty height) {
		 this.x=x;
		 this.y=y;
		 this.z=z;
		 this.width=width;
		 this.height=height;
		 this.grabText = "Da ist mein Rucksack zu klein für!";
		 this.lookText = "Das sieht sehr verdächtig aus!";
		 this.pushText = "Das steht da eigentlich schon ganz gut. Ich lasse es lieber stehen.";
		 this.canLook = true;
		 this.canGrab = false;
		 this.canPush = false;
		 
		 grabAction = intface->intface.inventory.add(this);
		 
		 childs = new ArrayList<>();
	}
	/**
	 * 
	 * @param x mouse coordinate x
	 * @param y mouse coordinate y
	 * @param totalHeight height of the window
	 * @param xOffsetWindow the offset of the level
	 * @return the element it self if no child exist of no child hit, otherwise it will return the any object witch is hit.
	 */
	public Object whichChildHit(double x, double y, double totalHeight,double xOffsetWindow){
		if(childs.isEmpty())return this;
		int localX= (int) ((x-getDrawX(totalHeight, xOffsetWindow))*getImage().getWidth()/getDrawWidth(totalHeight));
		int localY= (int) ((y-getDrawY(totalHeight))*getImage().getHeight()/getDrawHeight(totalHeight));
		
		Optional<Object> optional= childs.stream()
			.filter(c->c.isPixelSet(localX,localY))
			.findAny();
		return optional.isPresent()?optional.get():this;
	}
	/**
	 * 
	 * @param localX describe the x position
	 * @param localY describe the y position
	 * @return return true if a Pixel on x,y has an opacity greater then 0 
	 */	
	private boolean isPixelSet(int localX, int localY) {
		return this.img.getPixelReader().getColor(localX, localY).getOpacity() > 0;
	}
	
	/**
	 *  draw the object
	 */
	@Override
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow) {
		gc.drawImage(img,getDrawX( windowHeight,xOffsetWindow), getDrawY( windowHeight), getDrawWidth(windowHeight),getDrawHeight( windowHeight));
		for(Object child:childs) child.draw(gc, windowWidth, windowHeight, xOffsetWindow);
	}
	/**
	 * 
	 * @param XAxis mouse coordinate x
	 * @param YAxis mouse coordinate y
	 * @param windowHeight height of the window
	 * @param xOffsetWindow the offset of the level
	 * @return return true if the mouse is in the rectangle, define by the x,y,width,height of the object
	 */
	public boolean ifHit(double XAxis, double YAxis, double windowHeight, double xOffsetWindow){
		if(XAxis > getDrawX( windowHeight,xOffsetWindow) 
				&& XAxis < getDrawX( windowHeight,xOffsetWindow)+getDrawWidth(windowHeight) 
				&& YAxis > getDrawY( windowHeight) 
				&& YAxis < getDrawY( windowHeight)+getDrawHeight( windowHeight)){
			
			if(whichChildHit(XAxis,YAxis,windowHeight,xOffsetWindow)!=this)
				return true;

			int localX= (int) ((XAxis-getDrawX(windowHeight, xOffsetWindow))*getImage().getWidth()/getDrawWidth(windowHeight));
			int localY= (int) ((YAxis-getDrawY(windowHeight))*getImage().getHeight()/getDrawHeight(windowHeight));
			
			return isPixelSet(localX,localY);
		}
		return  false;
	}
	@Override
	public int compareTo(Object obj) {
		return Double.compare(getZ(), obj.getZ());
	}
}