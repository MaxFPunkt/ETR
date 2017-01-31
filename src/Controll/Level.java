package Controll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
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
		
		Object vase = new Object(500, 800, 700, 100, 100,new Image("001.png"));
		vase.setLookText("Hm die Vase sieht sehr leicht aus.");
		vase.setGrabText("Viel zu sperrig. Die nehme ich nicht mit!");
		vase.setPushText("Oh da war ein Schlüssel unter der Vase! Den kann ich später bestimmt noch gut gebrauchen.");
		vase.setUseText("Die ist so nutzlos wie der Staub auf meinem Boden");
		vase.setCanPush(true);
		objects.add(vase);
		
		{ // Kommode
			Object kommode = new Object(-50, 650, 1300, 169*2, 139*2,new Image("kommode_1.png"));
			kommode.setLookText("Eine Kommode... Die Schubladen scheinen verschlossen zu sein.");
			kommode.setPushText("Die steht da ganz gut. Außerdem ist die viel zu schwer!");
			{// childs
				BooleanProperty tuerOpen = new SimpleBooleanProperty(false);
				BooleanProperty foundCard = new SimpleBooleanProperty(false);
				Object tuer = new Object(kommode.getX(), kommode.getY(), kommode.getZ(), kommode.getWidth(), kommode.getHeight(), new Image("türen_zu.png"));
				tuer.setLookText("Die Türen sind nicht verschlossen!");
				tuer.setSecondaryText("Offen! Oh in der Kommode lag eine Schlüsselkarte. Für die finde ich bestimmt noch das richtige Schloss.");
				tuer.setPushText("So funktioniert das nicht.. Wenn ich nur wüsste, wie ich Türen *benutzen* kannn!");
				tuer.setSecondary(()->{
					
					if(tuerOpen.get()){ 
						tuer.setImg(new Image("türen_zu.png"));
						tuer.setSecondaryText("Und wieder zu.");
					} else {
						tuer.setImg(new Image("türen.png"));
						if(!foundCard.get()){
							parent.getIntface().getInventory().add(new Object(0,0,0,240,240,new Image("keycard.jpg")));
							foundCard.set(true);
						}else{
							tuer.setSecondaryText("Schade immer noch leer...");
						}
					}
					
					tuerOpen.set(!tuerOpen.get());
				});
				Object schublade_oben = new Object(kommode.getX(), kommode.getY(), kommode.getZ(), kommode.getWidth(), kommode.getHeight(), new Image("schublade_oben.png"));
				schublade_oben.setLookText("Die Schublade ist mit einem Hängeschloss verschlossen!");
				schublade_oben.setGrabText("Ich habe schon genug Schubladen zu hause.");
				schublade_oben.setPushText("So funktioniert das nicht..");
				schublade_oben.setUseText("*rüttel* *rüttel*\nVerschlossen.");
				
				Object schublade_unten = new Object(kommode.getX(), kommode.getY(), kommode.getZ(), kommode.getWidth(), kommode.getHeight(), new Image("schublade_unten.png"));
				schublade_unten.setLookText("Da ist ein Vorhängeschloss vor!");
				
				kommode.getChilds().add(schublade_oben);
				kommode.getChilds().add(schublade_unten);
				kommode.getChilds().add(tuer);
			}
			objects.add(kommode);
		}
		Object codePanel =new Object(520, 650, 1600, 100, 50,new Image("CodePanel.png"));
		codePanel.setLookText("Ein elektronisches Türschloss mit Pin Code Funktion. Das sieht sehr sicher aus.");
		codePanel.setSecondary(()->{
			
		});
		objects.add(codePanel);
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
	public void mouseClicked(MouseButton mouseButton, double x, double y, double totalWidth, double totalHeight) {
		if(parent.getIntface().getActiveAction()!=Action.NONE||mouseButton==MouseButton.SECONDARY){
			Optional<Object> object=getClickedObjekt(x, y);
			if(object.isPresent()){
				Object obj = object.get().whichChildHit(x,y,totalHeight,lastXOffsetWindow);
				if(mouseButton==MouseButton.SECONDARY){
					obj.secondaryAction();
					parent.getIntface().displayText(obj.getSecondaryText());
				}else{
					Action action = parent.getIntface().action(obj);
					if(action!=null && action.equals(Action.GRAB)) {
						partikelEffekts.add(new PartikelEffekt(obj,lastWindowHeight, lastXOffsetWindow));
						objects.remove(obj);
					}
				}
			}
		}
	}
	public Optional<Object> getClickedObjekt(double x, double y){
		return objects.stream()
				.filter(o->o.ifHit(x,y,lastWindowHeight,lastXOffsetWindow))
				.sorted((o1,o2)->{
					return Double.compare(o2.getZ(), o1.getZ());
				}).sorted().findFirst();
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