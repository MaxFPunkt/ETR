package objects;

import java.util.function.Consumer;

import de.cossijns.LibJavaFxMC.FontController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class CodePanel extends CloseUp {

	protected Consumer<Boolean> input;
	
	public CodePanel(String code) {
		super("Code Panel");
		if(!code.matches("[0-9]*")) throw new NumberFormatException();
		Label codeLabel=new Label();
		codeLabel.prefWidthProperty().bind(content.widthProperty());
		codeLabel.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, .5), CornerRadii.EMPTY, Insets.EMPTY)));
		FontController fontController=new FontController("Arial",codeLabel.textProperty() , codeLabel.widthProperty(), codeLabel.heightProperty());
		codeLabel.fontProperty().bind(fontController.getFontProperty());
		codeLabel.prefHeightProperty().bind(content.heightProperty().divide(5));
		codeLabel.minHeightProperty().bind(content.heightProperty().divide(5));
		content.getChildren().add(codeLabel);
		for(int i=9;i>0;--i){
			Button b= new Button(i+"");
			b.prefWidthProperty().bind(content.widthProperty().divide(3));
			b.prefHeightProperty().bind(content.heightProperty().divide(5));
			b.layoutXProperty().bind(content.widthProperty().divide(3).multiply((i-1)%3));
			b.layoutYProperty().bind(content.heightProperty().divide(5).multiply(3-(i-1)/3));
			final int k=i;
			b.setOnAction(e->{
				codeLabel.setText(codeLabel.getText()+k);
			});
			content.getChildren().add(b);
		}
		Button enter= new Button("Enter");
		enter.prefWidthProperty().bind(content.widthProperty());
		enter.prefHeightProperty().bind(content.heightProperty().divide(5));
		enter.layoutXProperty().set(0);
		enter.layoutYProperty().bind(content.heightProperty().divide(5).multiply(4));
		content.getChildren().add(enter);
		enter.setOnAction(e->{
			if(input!=null)input.accept(code.equalsIgnoreCase(codeLabel.getText()));
			codeLabel.setText("");
		});
		
	}
	public void setOnEnter(Consumer<Boolean> function){
		this.input=function;
	}
}
