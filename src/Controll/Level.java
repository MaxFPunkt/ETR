package Controll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import objects.Object;
import objects.interfaces.Drawable;
import objects.interfaces.Timer;

public class Level implements Drawable,Timer{
	private List<Object>  objects= new ArrayList<>();
	private double lastWindowHeight;
	private Double lastXOffsetWindow=null;
	private double toXOffsetWindow;
	
	// TODO: ONLY FOR TEST
	public Level(DoubleProperty width,DoubleProperty height,int objectAmount){
		
		DoubleProperty backgrounbdWith=new SimpleDoubleProperty(0);
		backgrounbdWith.bind(height.divide(9).multiply(32));
	}
	
	Image imgaeRoom=new Image("room.png");
	Image imgaeBode=new Image("bode.png");
	
	@Override
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow) {
		lastWindowHeight=windowHeight;
		toXOffsetWindow=xOffsetWindow;
		if(lastXOffsetWindow==null)
			lastXOffsetWindow=xOffsetWindow;
		
		gc.drawImage(imgaeRoom,lastXOffsetWindow/900*3200,0, windowHeight/900*3200,windowHeight);
		gc.drawImage(imgaeBode,lastXOffsetWindow/900*3200,0, windowHeight/900*3200,windowHeight);
		objects.stream()
			.sorted((o1,o2)->{
				return Double.compare(o2.getZ(), o1.getZ());
			})
			.forEachOrdered(o->o.draw(gc,windowWidth,windowHeight,lastXOffsetWindow));
	}
	public void mouseClicked(double x, double y) {
		for(Object o:objects){
			o.ifHit(x,y,lastWindowHeight,lastXOffsetWindow);
		}
	}
	public Optional<Object> getClickedObjekt(double x, double y){
		return objects.stream()
				.filter(o->o.ifHit(x,y,lastWindowHeight,lastXOffsetWindow))
				.sorted((o1,o2)->{
					return Double.compare(o2.getZ(), o1.getZ());
				})
				.sorted()
				.findFirst();
	}
	@Override
	public void update(double pastTime) {
		objects.stream()
			.forEach(o->o.call(pastTime));
		if(lastXOffsetWindow!=null)lastXOffsetWindow+=(lastXOffsetWindow-toXOffsetWindow)/20.*pastTime;
	}

	public void keyEvent(KeyEvent e) {
		
	}
}
