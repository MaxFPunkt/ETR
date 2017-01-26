package objects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class InventoryElement extends Pane {

	Object object;
	public InventoryElement(Object object) {
		this.object=object;
		setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 15, 0.5, 0.0, 0.0);" +
        "-fx-background-color: white;-fx-margin:20;");
		prefHeightProperty().set(100);
		if(object.getImage() != null){
			ImageView imageView=new ImageView(object.getImage());
			getChildren().add(imageView);
			imageView.fitWidthProperty().bind(widthProperty());
			imageView.fitHeightProperty().bind(heightProperty());
		}
		setOnMouseClicked(e->{
			System.out.println("click");
		});
	}
	
}
