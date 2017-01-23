package Controll;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import objects.interfaces.Drawable;
import objects.Object;

public class Level implements Drawable{
	private List<Object>  objects= new ArrayList<>();
	
	@Override
	public void draw() {
		objects.stream()
			.sorted(Comparator.comparing(Object::getZ))
			.forEachOrdered(o->o.draw());
		
	}
}
