package view;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	
	private Scene mainScene;
	private Content content;
	private AnimationTimer mainLoop;
	
	public static void main(String[] args) { launch(); }

	@Override
	public void init() throws Exception {
		super.init();
		mainLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
				draw();
			}
		};
	}
	
	@Override
	public void start(Stage stage){
		
		mainLoop.start();
		stage.setScene(mainScene);
		stage.show();
	}

	private void draw() {
		content.draw();
	}
	
	private void update() {

	}

	@Override
	public void stop() throws Exception {
		super.stop();
		
	}
}