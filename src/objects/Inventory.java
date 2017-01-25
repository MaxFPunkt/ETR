package objects;

import java.util.ArrayList;
import java.util.List;


import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class Inventory extends Pane{
	private List<Object> objects=new ArrayList<>();
	private Button switchButton = new Button("<->");
	private ListView listView =new ListView<>();
	public void add(Object object){
		objects.add(object);
	}
	Inventory(){
		
	}
}
