package objects;

import javafx.scene.canvas.GraphicsContext;
import objects.interfaces.Drawable;
import objects.interfaces.Timer;

public class Interface implements Drawable,Timer{

	@Override
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow) {
		// TODO Auto-generated method stub		
	}
	public void mouseClicked(double x, double y){}
}
