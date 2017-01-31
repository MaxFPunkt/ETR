package objects;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import objects.interfaces.Interactions.Action;

public class Interface extends Pane implements objects.interfaces.Timer{

	private HBox botRow;
	private Button toggleRow, grabBt, lookBt, pushBt,useBT;
	private boolean rowExpanded;
	private Label labelBox;
	private Timeline stopDisplay;
	private ArrayList<Animation> currentTransitions;
	private ArrayList<Animation> currentTextTransitions;
	Inventory inventory=new Inventory();
	
	private Property<Action> activeAction=new SimpleObjectProperty<Action>(Action.NONE);	
	private String cssButtonAktive="-fx-background-insets: 0,0 0 5 0, 0 0 5 0, 0 0 5 0;";
	private String cssButtonInAKtive="";
	
	private Pane dark;
	
	private FadeTransition fadeIn;
	private FadeTransition fadeOut;
	
	public Interface() {
		
		currentTransitions = new ArrayList<>();
		currentTextTransitions = new ArrayList<>();
		double inventoryScaling = 0.85; 
		inventory.layoutXProperty().bind(widthProperty().multiply(inventoryScaling));
		inventory.prefWidthProperty().bind(widthProperty().multiply(1-inventoryScaling));

		inventory.prefHeightProperty().bind(heightProperty());
		
		botRow = new HBox();
		botRow.layoutYProperty().bind(this.heightProperty().subtract(botRow.heightProperty()));
		
		rowExpanded = true;

		Font font = new Font("Calibri",30);
		
		grabBt = new Button("Nehmen");
		grabBt.setFont(font);
		grabBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(7));
		grabBt.setOnAction(e->{
			activeAction.setValue(activeAction.getValue()==Action.GRAB?Action.NONE:Action.GRAB);
		});
		
		lookBt = new Button("Ansehen");
		lookBt.setFont(font);
		lookBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(7));
		lookBt.setOnAction(e->{
			activeAction.setValue(activeAction.getValue()==Action.LOOK?Action.NONE:Action.LOOK);
		});
		
		pushBt = new Button("Schieben");
		pushBt.setFont(font);
		pushBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(7));
		pushBt.setOnAction(e->{
			activeAction.setValue(activeAction.getValue()==Action.PUSH?Action.NONE:Action.PUSH);
		});
		useBT = new Button("Use");
		useBT.setFont(font);
		useBT.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(7));
		useBT.setOnAction(e->{
			activeAction.setValue(activeAction.getValue()==Action.USE?Action.NONE:Action.USE);
			
			if(activeAction.getValue()==Action.USE){
				if(!getChildren().contains(dark))getChildren().add(dark);

				dark.setOpacity(0);
				currentTransitions.add(fadeIn);
				fadeIn.play();
				
				ObjectProperty<ChangeListener<? super Object>> cc=new SimpleObjectProperty<>();
				ChangeListener<? super Object> c = (ObservableValue<? extends Object> obValue, Object oldValue, Object newValue)->{
					fadeOut.play();
					InventoryElement.aktive.removeListener(cc.get());
				};
				cc.set(c);	
				
				InventoryElement.aktive.addListener(c);
				if(inventory.getOpenProcen().get()<=0)
					inventory.getSwitchButton().fire();
			}else{
				getChildren().remove(dark);
			}
		});
		
		toggleRow = new Button("❰");
		toggleRow.setFont(font);
		//toggleRow.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(7));
		toggleRow.prefWidth(50);
		toggleRow.setOnAction(e->{
			if(rowExpanded)collapseBotRow();
			else expandBotRow();
		});
		activeAction.addListener(new ChangeListener<Action>() {
			@Override
			public void changed(ObservableValue<? extends Action> observable, Action oldValue, Action newValue) {
				if(newValue==Action.GRAB)
					grabBt.setStyle(cssButtonAktive);
				else 
					grabBt.setStyle(cssButtonInAKtive);
				if(newValue==Action.LOOK)
					lookBt.setStyle(cssButtonAktive);
				else 
					lookBt.setStyle(cssButtonInAKtive);
				if(newValue==Action.PUSH)
					pushBt.setStyle(cssButtonAktive);
				else 
					pushBt.setStyle(cssButtonInAKtive);
				if(newValue==Action.USE)
					useBT.setStyle(cssButtonAktive);
				else 
					useBT.setStyle(cssButtonInAKtive);
			}
		});
		activeAction.setValue(Action.PUSH);activeAction.setValue(Action.NONE);

		labelBox = new Label("");
		labelBox.prefWidthProperty().bind(widthProperty().multiply(0.5));
		labelBox.prefHeightProperty().bind(heightProperty().multiply(0.1));
		labelBox.layoutYProperty().bind(botRow.layoutYProperty().subtract(labelBox.prefHeightProperty().add(50)));
		labelBox.layoutXProperty().bind(widthProperty().divide(2).subtract(labelBox.prefWidthProperty().divide(2)));
		labelBox.setAlignment(Pos.TOP_LEFT);
		labelBox.setWrapText(true);
		labelBox.setPadding(new Insets(2,15,0,15));
		labelBox.setFont(new Font("Calibri",25));
		stopDisplay = new Timeline(new KeyFrame( Duration.millis(3000),x->{
			FadeTransition ft = new FadeTransition(Duration.millis(750),labelBox);
			ft.setFromValue(1);
			ft.setToValue(0);
			ft.setOnFinished(eve->{
				labelBox.setText("");
				getChildren().remove(labelBox);
				currentTextTransitions.remove(ft);
				cancelTransitiosn();
			});
			currentTransitions.add(ft);
			ft.play();
		}));
		
		labelBox.setStyle("-fx-background-radius:15; -fx-background-color: rgba(20,20,20,0.8);-fx-text-fill:white;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 15, 0.5, 0.0, 0.0);");
		botRow.getChildren().addAll(pushBt,grabBt,lookBt,useBT,toggleRow);
		
		dark = new Pane();
		dark.prefWidthProperty().bind(prefWidthProperty().subtract(inventory.prefWidthProperty()));
		dark.prefHeightProperty().bind(prefHeightProperty().subtract(botRow.heightProperty()));
		dark.setStyle("-fx-background-color: rgba(20,20,20,0.7)");

		fadeIn = new FadeTransition(Duration.millis(500),dark);
		fadeIn.setToValue(1);
		fadeIn.setCycleCount(1);
		fadeIn.setOnFinished(e->currentTransitions.remove(fadeIn));
		
		fadeOut = new FadeTransition(Duration.millis(500),dark);
		fadeOut.setToValue(0);
		fadeOut.setCycleCount(1);
		fadeOut.setOnFinished(e->{currentTransitions.remove(fadeOut); getChildren().remove(dark);});
		
		getChildren().addAll(botRow,inventory);
	}
	public void cancelTransitiosn(){
		for(Animation a:currentTextTransitions) a.stop();
		labelBox.setText("");
		stopDisplay.stop();
		currentTextTransitions.clear();
	}
	public void displayText(String text){
		if(text==null||text.equalsIgnoreCase(""))
			return;
		if(!getChildren().contains(labelBox))getChildren().add(labelBox);
		cancelTransitiosn();
		FadeTransition ft = new FadeTransition(Duration.millis(750),labelBox);
		currentTextTransitions.add(ft);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.playFromStart();
		ft.setOnFinished(eve->{
			final IntegerProperty i = new SimpleIntegerProperty(0);
			Timeline timeline = new Timeline();
			currentTextTransitions.add(timeline);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(25),e->{
				if(i.get()>text.length()){
					timeline.stop();
					currentTextTransitions.remove(timeline);
					currentTextTransitions.add(stopDisplay);
					stopDisplay.playFromStart();
				} else {
					labelBox.setText(text.substring(0,i.get()));
					i.set(i.get()+1);
				}
			});
			timeline.getKeyFrames().add(keyFrame);
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.playFromStart();
		});
	}
	
	public HBox collapseBotRow(){
		TranslateTransition tt = new TranslateTransition(Duration.millis(300),botRow);
		tt.setToX(-1*botRow.widthProperty().subtract(toggleRow.widthProperty()).doubleValue());
		tt.setAutoReverse(false);
		tt.setCycleCount(1);
		tt.play();
		
		toggleRow.setText("❱");
		
		rowExpanded = false;
		return botRow;
	}
	
	public HBox expandBotRow(){
		TranslateTransition tt = new TranslateTransition(Duration.millis(300),botRow);
		tt.setFromX( -1*botRow.widthProperty().subtract(toggleRow.widthProperty()).doubleValue() );
		tt.setToX(0);
		tt.setAutoReverse(false);
		tt.setCycleCount(1);
		tt.play();
		
		toggleRow.setText("❰");

		rowExpanded = true;
		return botRow;
	}
	
	public void mouseClicked(double x, double y){}

	public Action getActiveAction() {return activeAction.getValue();}

	
	/**
	 * @return null if failed, action if success
	 */
	@SuppressWarnings("incomplete-switch")
	public Action action(Object object) {
		displayText(object.getText(getActiveAction()));
		if(object.can(getActiveAction())){
			switch(getActiveAction()){
			case GRAB:
				object.grab(this);
				break;
			case LOOK:
				object.look();
				break;
			case PUSH:
				object.push();
				break;			
			}
			Action executedAction = getActiveAction();
			resetActiveAction();
			return executedAction;
		}
		return null;
	}

	public void resetActiveAction() {
		this.activeAction.setValue(Action.NONE);
	}
	public Inventory getInventory() {
		return inventory;
	}
}
