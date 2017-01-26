package objects;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import objects.interfaces.Timer;

public class Interface extends Pane implements Timer{
	
	private List<Node> elements;

	private HBox botRow;
	private Button toggleRow, grabBt, lookBt, pushBt;
	private boolean rowExpanded;
	
	public Interface() {
		elements = new ArrayList<Node>();
		
		botRow = new HBox();
		botRow.layoutYProperty().bind(this.heightProperty().subtract(botRow.heightProperty()));
		
		rowExpanded = true;
		
		double inventoryScaling = 0.75; 
		Font font = new Font("Calibri",30);
		
		grabBt = new Button("Nehmen");
		grabBt.setFont(font);
		grabBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		grabBt.setOnAction(e->{
			
		});
		
		lookBt = new Button("Ansehen");
		lookBt.setFont(font);
		lookBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		lookBt.setOnAction(e->{
			
		});
		
		pushBt = new Button("Schieben");
		pushBt.setFont(font);
		pushBt.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		pushBt.setOnAction(e->{
			
		});
		
		toggleRow = new Button("Einfahren");
		toggleRow.setFont(font);
		toggleRow.prefWidthProperty().bind((prefWidthProperty().multiply(inventoryScaling)).divide(4));
		toggleRow.setOnAction(e->{
			if(rowExpanded)collapseBotRow();
			else expandBotRow();
		});
		
		
		botRow.getChildren().addAll(pushBt,grabBt,lookBt,toggleRow);
		getChildren().add(botRow);
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
}
