package objects;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Menu extends Pane {
	
	Button exit=new Button("Exit");
	Button credis=new Button("Credis");
	Button b1=new Button("1");
	Button b2=new Button("2");
	DoubleProperty openProcen=new SimpleDoubleProperty(0);
	
	Timeline timelineOpen = new Timeline( new KeyFrame( Duration.millis( 500 ),
             new KeyValue( openProcen, 0 ),
             new KeyValue( openProcen, 1 ) ) );
	Timeline timelineClose = new Timeline( new KeyFrame( Duration.millis( 500 ),
             new KeyValue( openProcen, 1 ),
             new KeyValue( openProcen, 0 ) ) );
	public Menu(){
		getChildren().addAll(b1,b2,credis,exit);
		this.backgroundProperty().bind(Bindings.createObjectBinding(() -> {
		    BackgroundFill fill = new BackgroundFill(new Color(0, 0, 0, openProcen.divide(2).get()), CornerRadii.EMPTY, Insets.EMPTY);
		    return new Background(fill);
		},openProcen));
		for(int i=0;getChildren().size()>i;++i){
			Button b=(Button) getChildren().get(i);
			b.prefWidthProperty().bind(widthProperty().divide(2));
			b.layoutXProperty().bind(widthProperty().divide(4));
			b.layoutYProperty().bind(heightProperty().divide(getChildren().size()+1).divide(2).multiply(i+1).add(heightProperty().divide(4)).multiply(openProcen).subtract(b.heightProperty()));			
		}	
		openProcen.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue)->{
			if(newValue.doubleValue()>0){
				layoutYProperty().bind(new SimpleDoubleProperty(0));		
			}else{
				layoutYProperty().bind(new SimpleDoubleProperty(0).subtract(heightProperty()));	
			}
		});
	}
	public void open(){	
		timelineOpen.playFromStart();
	}
	public void close(){
		timelineClose.playFromStart();
	}
	public boolean isOpen() {
		return openProcen.isEqualTo(1).get();
	}	
}
