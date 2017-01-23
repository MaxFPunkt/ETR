package objects.interfaces;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow);
}