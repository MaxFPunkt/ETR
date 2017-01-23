package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Content extends Canvas{
//	private List<Level> levels;
	
	public Content(double width, double height){
		super(width,height);
//		levels = new ArrayList<Object>();
	}

	public void draw() {
		GraphicsContext gc = getGraphicsContext2D();
		
		// Bild reseten
		gc.clearRect(0, 0, getWidth(), getHeight());
	}
}