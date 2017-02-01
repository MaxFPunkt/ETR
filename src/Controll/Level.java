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
import objects.CodeAlphabetPanel;
import objects.CodePanel;
import objects.Combination;
import objects.InventoryElement;
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
	public Content parent;
	
	private List<Combination> combinations=new ArrayList<>();
	
	// TODO: ONLY FOR TEST
	public Level(Content content, DoubleProperty width,DoubleProperty height,int objectAmount){
		this.parent=content;
		DoubleProperty backgrounbdWith=new SimpleDoubleProperty(0);
		backgrounbdWith.bind(height.divide(9).multiply(32));
		
		Object key = new Object(850, 850, 700, 40,40,new Image("keycard.png"));
		key.setCanGrab(true);
		key.setGrabText("Den kann ich später bestimmt noch gut gebrauchen.");

		Object tisch = new Object(775, 650, 900, 600, 300,new Image("Tisch.png"));
		objects.add(tisch);
		
		Object vase = new Object(850, 800, 700, 100, 100,new Image("Vase.png"));
		vase.setLookText("Hm die Vase sieht sehr leicht aus.");
		vase.setGrabText("Viel zu sperrig. Die nehme ich nicht mit!");
		vase.setPushText("Oh da war ein Schlüssel unter der Vase!");
		//vase.setUseText("Die ist so nutzlos wie der Staub auf meinem Boden");
		vase.setCanPush(true);
		vase.setPushAction(()->{
			objects.add(key);
			vase.setX(vase.getX()-25);
		});
		objects.add(vase);
		
		Object vase2 = new Object(50, 750, 1600, 100, 100,new Image("Vase.png"));
		vase2.setLookText("Hm die Vase sieht sehr leicht aus.");
		vase2.setGrabText("Viel zu sperrig. Die nehme ich nicht mit!");
		vase2.setPushText("Oh ich habe die Vase bewegt. Und jetzt?");
		vase2.setCanPush(true);
		vase2.setPushAction(()->{
			vase2.setX(vase2.getX()+25);
		});
		objects.add(vase2);
		
		{ // Kommode
			Object kommode = new Object(-50, 650, 1300, 169*2, 139*2,new Image("schrank/schrank.png"));
			kommode.setLookText("Eine Kommode... Die Schubladen scheinen verschlossen zu sein.");
			kommode.setPushText("Die steht da ganz gut. Außerdem ist die viel zu schwer!");
			{// childs
				BooleanProperty tuerOpen = new SimpleBooleanProperty(false);
				BooleanProperty foundCard = new SimpleBooleanProperty(false);
				Object tuer = new Object(kommode.getX(), kommode.getY(), kommode.getZ(), kommode.getWidth(), kommode.getHeight(), new Image("schrank/tuer_zu.png"));
				tuer.setLookText("Die Türen sind nicht verschlossen!");
				tuer.setSecondaryText("Offen! Oh in der Kommode lag eine Schlüsselkarte. Für die finde ich bestimmt noch das richtige Schloss.");
				tuer.setPushText("So funktioniert das nicht.. Wenn ich nur wüsste, wie ich Türen *benutzen* kannn!");
				tuer.setSecondary(()->{
					
					if(tuerOpen.get()){ 
						tuer.setImg(new Image("schrank/tuer_zu.png"));
						tuer.setSecondaryText("Und wieder zu.");
					} else {
						tuer.setImg(new Image("schrank/tuer_auf.png"));
						if(!foundCard.get()){
							parent.getIntface().getInventory().add(new Object(0,0,0,240,240,new Image("keycard.png")));
							foundCard.set(true);
						}else{
							tuer.setSecondaryText("Schade immer noch leer...");
						}
					}
					
					tuerOpen.set(!tuerOpen.get());
				});
				Object schublade_oben = new Object(kommode.getX(), kommode.getY(), kommode.getZ(), kommode.getWidth(), kommode.getHeight(), new Image("schrank/schub_zu1.png"));
				schublade_oben.setLookText("Die Schublade ist mit einem Hängeschloss verschlossen!");
				schublade_oben.setGrabText("Ich habe schon genug Schubladen zu hause.");
				schublade_oben.setPushText("So funktioniert das nicht..");
				//schublade_oben.setUseText("*rüttel* *rüttel*\nVerschlossen.");

				CodeAlphabetPanel codeAlphabetPanel=new CodeAlphabetPanel("BDF");
				schublade_oben.setSecondary(()->{					
					codeAlphabetPanel.prefWidthProperty().bind(content.getApplication().mainScene.widthProperty());
					codeAlphabetPanel.prefHeightProperty().bind(content.getApplication().mainScene.heightProperty());
					codeAlphabetPanel.setOnClose(()->{
						content.getApplication().mainPane.getChildren().remove(codeAlphabetPanel);
					});
					codeAlphabetPanel.setOnEnter(i->{
						if(i==true){
							content.getApplication().mainPane.getChildren().remove(codeAlphabetPanel);
						}
					});
					content.getApplication().mainPane.getChildren().add(codeAlphabetPanel);
				});
				schublade_oben.setSecondaryText("Ein rotes panel hat sich geöffnet!");
				
				Object schublade_unten = new Object(kommode.getX(), kommode.getY(), kommode.getZ(), kommode.getWidth(), kommode.getHeight(), new Image("schrank/schub_zu2.png"));
				schublade_unten.setLookText("Da ist ein Vorhängeschloss vor!");
				
				kommode.getChilds().add(schublade_oben);
				kommode.getChilds().add(schublade_unten);
				kommode.getChilds().add(tuer);
				
				combinations.add(new Combination(schublade_unten, key, ()->{
					schublade_unten.setPushText("Die Schublade ist mit einem ruck aufgegangen.");
					schublade_unten.setLookText("Eine aufgeschlossende Schublade.");
					schublade_unten.setCanPush(true);
					schublade_unten.setPushAction(()->{
						schublade_unten.setImg(new Image("schrank/schub_auf2.png"));
					});
					
					// Wenn die aktive Aktion immer noch use ist, resete es, weil use *hier* zu ende ist
					if(parent.getIntface().getActiveAction()==Action.USE)
						parent.getIntface().getUseBT().fire();
				}, "Das Vorhängeschloss ist jetzt offen!"));
			}
			objects.add(kommode);
		}
		Object toyBriegeA= new Object( 0, 0, 0, 20, 20,new Image("CodePanel.png"));objects.add(toyBriegeA);
		Object toyBriegeB= new Object(10, 0, 0, 20, 20,new Image("CodePanel.png"));objects.add(toyBriegeB);
		Object toyBriegeC= new Object(20, 0, 0, 20, 20,new Image("CodePanel.png"));objects.add(toyBriegeC);
		Object toyBriegeD= new Object(30, 0, 0, 20, 20,new Image("CodePanel.png"));objects.add(toyBriegeD);
		Object toyBriegeE= new Object(40, 0, 0, 20, 20,new Image("CodePanel.png"));objects.add(toyBriegeE);
		Object toyBriegeF= new Object(50, 0, 0, 20, 20,new Image("CodePanel.png"));objects.add(toyBriegeF);
		Object[] toyBricks={toyBriegeA,toyBriegeB,toyBriegeC,toyBriegeD,toyBriegeE,toyBriegeF};
		for(Object toyBrick: toyBricks){
			toyBrick.setGrabText("Ein neuer Bauklotz für meine Sammlung.");
			toyBrick.setLookText("So einen wollte ich schon immer mal haben.");
			//toyBrick.setUseText("Nicht jetzt, mir lauft die Zeit doch schon weg.");
			toyBrick.setCanGrab(true);
		}
		
		Object codePanelObject =new Object(520, 650, 1600, 100, 50,new Image("CodePanel.png"));
		codePanelObject.setLookText("Ein elektronisches Türschloss mit Pin Code Funktion. Das sieht sehr sicher aus.");
		
		CodePanel codePanel=new CodePanel("888");
		codePanelObject.setSecondary(()->{
			codePanel.prefWidthProperty().bind(content.getApplication().mainScene.widthProperty());
			codePanel.prefHeightProperty().bind(content.getApplication().mainScene.heightProperty());
			codePanel.setOnClose(()->{
				content.getApplication().mainPane.getChildren().remove(codePanel);
			});
			codePanel.setOnEnter(i->{
				if(i==true){
					content.getApplication().mainPane.getChildren().remove(codePanel);
				}
			});
			content.getApplication().mainPane.getChildren().add(codePanel);
			
		});
		objects.add(codePanelObject);
	}
	
	Image imgaeRoom=new Image("room.png");
	
	public Optional<Combination> getCombination(Object a,Object b){
		return combinations.stream()
			.filter(c->(c.a==a&&c.b==b)||(c.a==b&&c.b==a))
			.findAny();
	}
	@Override
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow) {
		lastWindowHeight=windowHeight;
		if(lastXOffsetWindow!=null&&toXOffsetWindow!=xOffsetWindow)
			lastXOffsetWindow+=(xOffsetWindow-toXOffsetWindow)/3.;		
		toXOffsetWindow=xOffsetWindow;
		if(lastXOffsetWindow==null)
			lastXOffsetWindow=xOffsetWindow;
		
		gc.drawImage(imgaeRoom,lastXOffsetWindow/900*3200,0, windowHeight/900*3200,windowHeight);
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
					if(parent.getIntface().getActiveAction()==Action.USE){
						if(getCombination(InventoryElement.aktive.get(),obj).isPresent()){
							parent.getIntface().displayText(getCombination(InventoryElement.aktive.get(),obj).get().text);
							if(getCombination(InventoryElement.aktive.get(),obj).get().f!=null)getCombination(InventoryElement.aktive.get(),obj).get().f.run();
						}else{
							if(InventoryElement.aktive.get()!=null)
								parent.getIntface().displayText("Ich glaube das kann ich so nicht benutzen.");
							else
								parent.getIntface().displayText("Nichts mit etwas zu verbinden, bleibt etwas!");								
						}
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
	}
	public Optional<Object> getClickedObjekt(double x, double y){
		return objects.stream()
				.filter(o->o.ifHit(x,y,lastWindowHeight,lastXOffsetWindow))
				.sorted().findFirst();
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