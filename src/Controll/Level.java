package Controll;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import objects.interfaces.Drawable;
import objects.interfaces.Timer;
import objects.Object;

public class Level implements Drawable,Timer{
	private List<Object>  objects= new ArrayList<>();
	
	@Override
	public void draw() {
		objects.stream()
			.sorted(Comparator.comparing(Object::getZ))
			.forEachOrdered(o->o.draw());
		
	}
	@Override
	public void update(double pastTime) {
		objects.stream()
			.forEach(o->o.call(pastTime));
	}
}
