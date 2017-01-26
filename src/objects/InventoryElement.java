package objects;

import javafx.scene.layout.Pane;

public class InventoryElement extends Pane {

	Object object;
	public InventoryElement(Object object) {
		this.object=object;
		setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 15, 0.5, 0.0, 0.0);" +
        "-fx-background-color: white;-fx-margin:20;");
		prefHeightProperty().set(100);
	}

}
