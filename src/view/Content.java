package view;

import java.util.ArrayList;
import java.util.List;

import Controll.Level;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import objects.Interface;
import objects.interfaces.Timer;

public class Content extends Canvas implements Timer{
	private List<Level> levels;
	private double xOffset;
	private Interface intface;
	public Content(Interface intface){
		super(100,100);
		this.intface=intface;
		levels = new ArrayList<Level>();
		levels.add(new Level(this,widthProperty(),heightProperty(),1000));
	}

	public void draw() {
		getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
		for(Level l:levels) 
			l.draw(getGraphicsContext2D(),getWidth(),getHeight(),xOffset);
	}
	public void mouseClick(double x, double y) {
		for(Level l:levels)l.mouseClicked(x,y);
	}

	public void keyEvent(KeyEvent e) {
		for(Level l:levels)l.keyEvent(e);
		if(e.getCode()==KeyCode.LEFT)
			xOffset+=50;
		if(e.getCode()==KeyCode.RIGHT)
			xOffset-=50;
	}
	@Override
	public void update(double pastetime){
		for(Level l:levels)l.call(pastetime);
	}

	public Interface getIntface() {return intface;}
}