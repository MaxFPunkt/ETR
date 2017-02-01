package objects;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import objects.interfaces.Drawable;
import objects.interfaces.Timer;

public class PartikelEffekt implements Drawable,Timer {

	List<Partikel> partikels=new ArrayList<>();
	public PartikelEffekt(Object object, double lastWindowHeight, Double lastXOffsetWindow){
		if(object.getImage()!=null){
	        PixelReader pR = object.getImage().getPixelReader();
			for(int x=0;x<object.getDrawWidth(lastWindowHeight);++x){
				for(int y=0;y<object.getDrawHeight(lastWindowHeight);++y){					
					partikels.add(new Partikel(x+object.getDrawX(lastWindowHeight, lastXOffsetWindow),
							y+object.getDrawY(lastWindowHeight),
							pR.getColor((int)(x/object.getDrawWidth(lastWindowHeight)*object.getImage().getWidth()),
									(int)(y/object.getDrawHeight(lastWindowHeight)*object.getImage().getHeight()))));
				}
			}
		}
	}
	@Override
	public void draw(GraphicsContext gc, double windowWidth, double windowHeight, double xOffsetWindow) {
		for(Partikel p:partikels){
			gc.setFill(p.color);
			gc.fillOval(p.x, p.y, 1, 1);
		}
	}
	@Override
	public void update(double pastTime){
		partikels.forEach(p->{
			p.call(pastTime);
		});
		partikels.removeIf(p->p.livetime<0);
	}
}