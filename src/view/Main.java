package view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application{
	
	private static final double WIDTH = 1600, HEIGHT = 900;
	
	private Scene mainScene;
	private Pane mainPane;
	private Content content;
	private AnimationTimer mainLoop;
	
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
		content = new Content(WIDTH, HEIGHT);
		mainPane.getChildren().add(content);
		mainScene = new Scene(mainPane);
	}
	
	@Override
	public void start(Stage stage){
		content.widthProperty().bind(stage.widthProperty());
		
		stage.setScene(mainScene);
		stage.show();
		
		stage.addEventHandler(MouseEvent.MOUSE_CLICKED, ( e) -> {
			System.out.println("ScreenX: "+e.getSceneX());			
			content.mouseClick(e.getSceneX(), e.getSceneY());
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
