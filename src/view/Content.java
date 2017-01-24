package view;

import java.util.ArrayList;
import java.util.List;

import Controll.Level;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Interface;
import objects.interfaces.Timer;

public class Content extends Canvas implements Timer{
	private List<Level> levels;
	private double xOffset;
	private Interface intface;
	int i=0;
	public Content(double width, double height){
		super(width,height);
		levels = new ArrayList<Level>();
		levels.add(new Level(1000));
		intface=new Interface();
	}

	public void draw() {
		GraphicsContext gc = getGraphicsContext2D();
		
		// Bild reseten
		gc.clearRect(0, 0, getWidth(), getHeight());
		
		if(i%30==0){
			gc.setFill(new Color(Math.random(),Math.random(), Math.random(), 1));
			i=0;
		}
		
		for(Level l:levels) l.draw(gc,getWidth(),getHeight(),xOffset);
		i++;
		intface.draw(gc,  getWidth(),  getHeight(), 0);		
	}
	public void mouseClick(double x, double y) {
		for(Level l:levels)l.mouseClicked(x,y);
		intface.mouseClicked(x,y);
	}
}