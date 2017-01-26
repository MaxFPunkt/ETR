package objects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import objects.interfaces.Interactions.Action;
import objects.interfaces.Timer;

public class Interface extends Pane implements Timer{

	private HBox botRow;
	private Button toggleRow, grabBt, lookBt, pushBt;
	private boolean rowExpanded;
	private Label labelBox;
	Inventory inventory=new Inventory();
	private Property<Action> activeAction=new SimpleObjectProperty<Action>(Action.NONE);
	
	private String cssButtonAktive="-fx-background-color:#123";
	private String cssButtonInAKtive="-fx-background-color:#321";
	
	public Interface() {
		
		double inventoryScaling = 0.8; 
		inventory.layoutXProperty().bind(widthProperty().multiply(inventoryScaling));
		inventory.prefWidthProperty().bind(widthProperty().multiply(1-inventoryScaling));

		inventory.prefHeightProperty().bind(heightProperty());
		
		botRow = new HBox();
		botRow.layoutYProperty().bind(this.heightProperty().subtract(botRow.heightProperty()));
		
		rowExpanded = true;
		
		Font font = new Font("Calibri",30);
		
		grabBt = new Button("Nehmen");
		grabBt.setFont(font);
		grabBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		grabBt.setOnAction(e->{
			activeAction.setValue(activeAction.getValue()==Action.GRAB?Action.NONE:Action.GRAB);
		});
		
		lookBt = new Button("Ansehen");
		lookBt.setFont(font);
		lookBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		lookBt.setOnAction(e->{
			activeAction.setValue(activeAction.getValue()==Action.LOOK?Action.NONE:Action.LOOK);
		});
		
		pushBt = new Button("Schieben");
		pushBt.setFont(font);
		pushBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		pushBt.setOnAction(e->{
			activeAction.setValue(activeAction.getValue()==Action.PUSH?Action.NONE:Action.PUSH);
		});
		toggleRow = new Button("Einfahren");
		toggleRow.setFont(font);
		toggleRow.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
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
			}
		});
		activeAction.setValue(Action.PUSH);activeAction.setValue(Action.NONE);

		labelBox = new Label("asdsfgjhgftdfdfg");
		labelBox.prefWidthProperty().bind(widthProperty().multiply(0.5));
		labelBox.prefHeightProperty().bind(heightProperty().multiply(0.1));
		labelBox.layoutYProperty().bind(botRow.layoutYProperty().subtract(labelBox.prefHeightProperty().add(50)));
		labelBox.layoutXProperty().bind(widthProperty().divide(2).subtract(labelBox.prefWidthProperty().divide(2)));
		labelBox.setFont(new Font("Calibri",25));
		labelBox.setStyle("-fx-background-color: black;-fx-text-fill:white;");
		botRow.getChildren().addAll(pushBt,grabBt,lookBt,toggleRow );
		getChildren().addAll(botRow,inventory,labelBox);
	}
	
	public void displayText(String text){
		final IntegerProperty i = new SimpleIntegerProperty(0);
		Timeline timeline = new Timeline();
		KeyFrame keyFrame = new KeyFrame(Duration.millis(40),e->{
			if(i.get()>text.length()) timeline.stop();
			else {
				labelBox.setText(text.substring(0,i.get()));
				i.set(i.get()+1);
			}
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	public HBox collapseBotRow(){
		TranslateTransition tt = new TranslateTransition(Duration.millis(300),botRow);
		tt.setToX(-1*botRow.widthProperty().subtract(toggleRow.widthProperty()).doubleValue());
		tt.setAutoReverse(false);
		tt.setCycleCount(1);
		tt.play();
		
		toggleRow.setText("Ausfahren");
		
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
		
		toggleRow.setText("Einfahren");

		rowExpanded = true;
		return botRow;
	}
	
	public void mouseClicked(double x, double y){}

	public Action getActiveAction() {return activeAction.getValue();}

	@SuppressWarnings("incomplete-switch")
	public boolean action(Object object) {
		if(object.can(getActiveAction())){
			switch(getActiveAction()){
			case GRAB:
				object.grab(this);
				displayText("Haha das ist jetzt meins!");
				break;
			case LOOK:
				object.look();
				break;
			case PUSH:
				object.push();
				break;			
			}
			return true;
		}
		return false;
	}

	public void resetActiveAction() {
		this.activeAction.setValue(Action.NONE);
	}
}
