package objects;

import java.util.function.Consumer;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CloseUp extends Pane {
	protected Consumer<Boolean> function;
	protected Pane content=new Pane();
	public CloseUp(String name) {
		content.layoutYProperty().bind(heightProperty().divide(2).subtract(250));
		content.layoutXProperty().bind(widthProperty().divide(2).subtract(250));
		content.prefHeightProperty().bind(new SimpleDoubleProperty(500));
		content.prefWidthProperty().set(500);
		BackgroundFill fill = new BackgroundFill(new Color(0, 0, 0, .5), CornerRadii.EMPTY, Insets.EMPTY);
		setBackground(new Background(fill));
		content.setBackground(new Background(fill));
		getChildren().add(content);
		Button b= new Button("X");
		getChildren().add(b);

		b.layoutYProperty().bind(heightProperty().divide(2).subtract(330));
		b.layoutXProperty().bind(widthProperty().divide(2).add(250));
		b.setOnAction(e->{
			if(function!=null)function.accept(true);
		});
		
	}
	public void setOnClose(Consumer<Boolean> function){
		this.function=function;
	}
}
