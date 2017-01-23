package view;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
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
				update();
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
		
		mainLoop.start();
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
