package objects;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Inventory extends Pane {
	private List<InventoryElement> inventoryElements=new ArrayList<>();
	private Button switchButton = new Button("Inventory");
	private ScrollPane scrollPane =new ScrollPane();
	private VBox vBox=new VBox();
	DoubleProperty openProcen=new SimpleDoubleProperty(0);
	
	Timeline timelineOpen = new Timeline( new KeyFrame( Duration.millis( 200 ),
            new KeyValue( openProcen, 0 ),
            new KeyValue( openProcen, 1 ) ) );
	Timeline timelineClose = new Timeline( new KeyFrame( Duration.millis( 200 ),
            new KeyValue( openProcen, 1 ),
            new KeyValue( openProcen, 0 ) ) );
	
	public void add(Object object){
		InventoryElement inventoryElement=new InventoryElement(object);
		inventoryElements.add(inventoryElement);
		vBox.getChildren().add(inventoryElement);
	}
	Inventory(){

		getChildren().addAll(scrollPane,switchButton);
		
		layoutYProperty().bind(heightProperty().multiply(-1).add(switchButton.heightProperty()).multiply(new SimpleDoubleProperty(1).subtract(openProcen)));
		
		scrollPane.setContent(vBox);
		scrollPane.prefWidthProperty().bind(widthProperty());
		scrollPane.prefHeightProperty().bind(heightProperty().subtract(switchButton.heightProperty()));
		
		switchButton.layoutYProperty().bind(heightProperty().subtract(switchButton.heightProperty()));
		switchButton.prefWidthProperty().bind(widthProperty());
		switchButton.setOnAction(e->{
			if(openProcen.isEqualTo(0).get()){
				timelineOpen.playFromStart();
			}else if(openProcen.isEqualTo(1).get()){
				timelineClose.playFromStart();
				
			}
		});
		
		vBox.prefWidthProperty().bind(widthProperty());
		vBox.setStyle("-fx-padding: 5px;");
		
	}
}
