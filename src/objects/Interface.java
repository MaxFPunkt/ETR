package objects;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import objects.interfaces.Timer;

public class Interface extends Pane implements Timer{
	
	private List<Node> elements;
	
	Button toggleAction;
	
	public Interface() {
		elements = new ArrayList<Node>();
		
		toggleAction = new Button("Ausfahren");
		toggleAction.layoutYProperty().bind(this.heightProperty().subtract(toggleAction.heightProperty()));
		getChildren().add(toggleAction);
	}
	
	public void mouseClicked(double x, double y){}
}
