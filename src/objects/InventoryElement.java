package objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import objects.interfaces.Interactions.Action;

public class InventoryElement extends Pane {

	public static ObjectProperty<Object> aktive=new SimpleObjectProperty<>(null);
	Object object;
	private static DropShadow ds1 = new DropShadow(BlurType.GAUSSIAN,Color.RED,3,20,0.,0.);
	private static DropShadow ds2 = new DropShadow(5,4.,4.,Color.gray(0.4));
    
	public InventoryElement(Object object,Inventory inventory) {
		this.object=object;
		setStyle("-fx-background-color: white;-fx-margin:20;");
		prefHeightProperty().set(100);
        setEffect(ds2);
		if(object.getImage() != null){
			ImageView imageView=new ImageView(object.getImage());
			getChildren().add(imageView);
			imageView.fitWidthProperty().bind(widthProperty());
			imageView.fitHeightProperty().bind(heightProperty());
			aktive.addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue)->{
				if(newValue==object) setEffect(ds1);
				else setEffect(ds2);
			});
		}
		setOnMouseClicked(e->{
			if(inventory.intface.getActiveAction()!=Action.USE)return;
			if(aktive.get()==object){
				aktive.set(null);
			}else
				aktive.set(object);
		});
	}
}