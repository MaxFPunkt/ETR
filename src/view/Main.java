package view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.Menu;


public class Main extends Application{

	
	private Scene mainScene;
	private Pane mainPane;
	private Content content;
	private AnimationTimer mainLoop;
	private Menu menu=new Menu();
	public static void main(String[] args) { launch(); }

	@Override
	public void init() throws Exception {
		super.init();
		mainLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				call(now);
				draw();
			}
		};
		mainPane = new Pane();
		content = new Content();
		mainPane.getChildren().add(content);
		mainPane.getChildren().add(menu);
		mainScene = new Scene(mainPane);
		content.widthProperty().bind(mainScene.widthProperty());
		content.heightProperty().bind(mainScene.heightProperty());
		menu.prefWidthProperty().bind(mainScene.widthProperty());
		menu.prefHeightProperty().bind(mainScene.heightProperty());
	}
	
	@Override
	public void start(Stage stage){
		content.widthProperty().bind(stage.widthProperty());
		stage.initStyle(StageStyle.UTILITY);
		stage.setFullScreen(true);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setScene(mainScene);
		stage.show();
		
		stage.addEventHandler(MouseEvent.MOUSE_CLICKED, ( e) -> {	
			content.mouseClick(e.getSceneX(), e.getSceneY());
		});
		stage.addEventHandler(KeyEvent.ANY, e->{
			content.keyEvent(e);
			if(e.getEventType()==KeyEvent.KEY_RELEASED){
				if(e.getCode()==KeyCode.ESCAPE){				
					if(!menu.isOpen()){						
						menu.open();
					}
					else{
						menu.close();
					}
				}
			}
		});
		
		mainLoop.start();
	}

	private void draw() {
		content.draw();
	}
	
	private void call(double pastTime) {
		content.call(pastTime);
	}

	@Override
	public void stop() throws Exception {
		super.stop();		
	}
}
