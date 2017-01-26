package objects;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
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
	Inventory inventory=new Inventory();
	private Action activeAction=Action.NONE;
	
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
			activeAction = Action.GRAB;
		});
		
		lookBt = new Button("Ansehen");
		lookBt.setFont(font);
		lookBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		lookBt.setOnAction(e->{
			activeAction = Action.LOOK;
		});
		
		pushBt = new Button("Schieben");
		pushBt.setFont(font);
		pushBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		pushBt.setOnAction(e->{
			activeAction = Action.PUSH;
		});
		
		toggleRow = new Button("Einfahren");
		toggleRow.setFont(font);
		toggleRow.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		toggleRow.setOnAction(e->{
			if(rowExpanded)collapseBotRow();
			else expandBotRow();
		});
		
		
		botRow.getChildren().addAll(pushBt,grabBt,lookBt,toggleRow);
		getChildren().addAll(botRow,inventory);
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

	public Action getActiveAction() {return activeAction;}

	@SuppressWarnings("incomplete-switch")
	public boolean action(Object object) {
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
			return true;
		}
		return false;
	}

	public void resetActiveAction() {
		this.activeAction = Action.NONE;
	}
}
