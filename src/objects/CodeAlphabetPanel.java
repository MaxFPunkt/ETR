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

public class CodeAlphabetPanel extends CloseUp {

	protected Consumer<Boolean> input;
	
	public CodeAlphabetPanel(String code) {
		super("Code Panel");
		Label codeLabel=new Label();
		codeLabel.prefWidthProperty().bind(content.widthProperty());
		codeLabel.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, .5), CornerRadii.EMPTY, Insets.EMPTY)));
		FontController fontController=new FontController("Arial",codeLabel.textProperty() , codeLabel.widthProperty(), codeLabel.heightProperty());
		codeLabel.fontProperty().bind(fontController.getFontProperty());
		codeLabel.prefHeightProperty().bind(content.heightProperty().divide(7));
		codeLabel.minHeightProperty().bind(content.heightProperty().divide(7));
		content.getChildren().add(codeLabel);
		for(int i=4*5;i>0;--i){
			Button b= new Button((char)(Integer.valueOf('A')+i-1)+"");
			b.prefWidthProperty().bind(content.widthProperty().divide(4));
			b.prefHeightProperty().bind(content.heightProperty().divide(2+5));
			b.layoutXProperty().bind(content.widthProperty().divide(4).multiply((i-1)%4));
			b.layoutYProperty().bind(content.heightProperty().divide(2+5).multiply((i-1)/4+1));
			final int k=i;
			b.setOnAction(e->{
				codeLabel.setText(codeLabel.getText()+(char)(Integer.valueOf('A')+k-1));
			});
			content.getChildren().add(b);
		}
		Button enter= new Button("Enter");
		enter.prefWidthProperty().bind(content.widthProperty());
		enter.prefHeightProperty().bind(content.heightProperty().divide(7));
		enter.layoutXProperty().set(0);
		enter.layoutYProperty().bind(content.heightProperty().divide(7).multiply(6));
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
