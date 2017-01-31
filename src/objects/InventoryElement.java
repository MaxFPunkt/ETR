package objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class InventoryElement extends Pane {

	public static ObjectProperty<Object> aktive=new SimpleObjectProperty<>(null);
	Object object;
    private static final DropShadow ds = new DropShadow( 20, Color.RED );
    
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
			
			aktive.addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue)->{
				if(newValue==object){
					imageView.setEffect( ds );
				}else{
					imageView.setEffect( null );
				}
			});
		}
		setOnMouseClicked(e->{
			if(aktive.get()==object){
				aktive.set(null);
			}else
				aktive.set(object);
		});
	}
	
}
