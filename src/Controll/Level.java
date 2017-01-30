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
import objects.PartikelEffekt;
import objects.interfaces.Drawable;
import objects.interfaces.Interactions.Action;
import objects.interfaces.Timer;
import view.Content;

public class Level implements Drawable,Timer{
	private List<Object>  objects= new ArrayList<>();
	private List<PartikelEffekt>  partikelEffekts= new ArrayList<>();
	private double lastWindowHeight;
	private Double lastXOffsetWindow=null;
	private double toXOffsetWindow;
	private Content parent;
	
	// TODO: ONLY FOR TEST
	public Level(Content content, DoubleProperty width,DoubleProperty height,int objectAmount){
		this.parent=content;
		DoubleProperty backgrounbdWith=new SimpleDoubleProperty(0);
		backgrounbdWith.bind(height.divide(9).multiply(32));
		Object vase = new Object(0, 0, 0, 100, 100,new Image("001.png"));
		vase.setLookText("Hm die Vase sieht sehr leicht aus.");
		vase.setGrabText("Haha die gehört jetzt mir!");
		vase.setCanGrab(true);
		objects.add(vase);
		objects.add(new Object(120, 50, 0, 100, 100,new Image("001.png")));
	}
	
	Image imgaeRoom=new Image("room.jpg");
	Image imgaeBode=new Image("bode.png");
	
	@Override
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow) {
		lastWindowHeight=windowHeight;
		if(lastXOffsetWindow!=null&&toXOffsetWindow!=xOffsetWindow)
			lastXOffsetWindow+=(xOffsetWindow-toXOffsetWindow)/3.;		
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
		partikelEffekts.forEach(o->o.draw(gc,windowWidth,windowHeight,lastXOffsetWindow));
	}
	public void mouseClicked(double x, double y) {
		if(parent.getIntface().getActiveAction()!=Action.NONE){
			Optional<Object> object=getClickedObjekt(x, y);
			if(object.isPresent()){
				Action action = parent.getIntface().action(object.get());
				System.out.println("Action: "+action.toString());
				if(action!=null && action.equals(Action.GRAB)) {
					partikelEffekts.add(new PartikelEffekt(object.get(),lastWindowHeight, lastXOffsetWindow));
					objects.remove(object.get());
				}
			}
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
		partikelEffekts.forEach(o->o.call(pastTime));
		if(lastXOffsetWindow!=null)lastXOffsetWindow+=(toXOffsetWindow-lastXOffsetWindow)/100000000000000.*pastTime;
	}

	public void keyEvent(KeyEvent e) {
		
	}
}
