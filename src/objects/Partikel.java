package objects;

import javafx.scene.paint.Color;
import objects.interfaces.Timer;

public class Partikel implements Timer{
	public double x,y;
	public Color color;
	public int faktorX,faktorY;
	public double livetime=100000000000000.*(Math.random()*2);
	public Partikel(double x,double y,Color color) {
		this.x=x;
		this.y=y;
		this.color=color;
		if(((int)(Math.random()*2))==1)faktorX=1;else faktorX=-1;		
		if(((int)(Math.random()*2))==1)faktorY=1;else faktorY=-1;
	}

	@Override
	public void update(double pastTime){
		//x+=pastTime/1000000000000.*Math.random()*50*faktorX;
		//y-=pastTime/1000000000000.*Math.random()*20*faktorY;
		livetime-=pastTime;
	}
}
