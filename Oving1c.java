
package application;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import javafx.geometry.Pos;



public class Oving1c extends Application {
	
	public static void main(String[] args) {
		launch(Oving1c.class, args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException{
		GridPane grid = addGridPane();
		Circle c = new Circle(250,170,45);
		c.setStroke(Color.BLACK);
		grid.add(c, 0, 0);
		Scene scene = new Scene(grid, 500, 500);
		//scene.getStylesheets().add("application/Oving1c.css");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	private GridPane addGridPane() {
		
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		StackPane pane = addStackPane();
		grid.add(pane, 0, 0);
		/*StackPane orangePane = addStackPane();
		StackPane greenPane = addStackPane();
		StackPane yellowPane = addStackPane();
		StackPane purplePane = addStackPane();
		StackPane bluePane = addStackPane();*/
		/*ImageView orange = new ImageView(
				new Image(Oving1c.class.getResourceAsStream("application.graphics/orange.png")));
		orangeSquare.getChildren().add(orange);
		grid.add(orangePane, 0, 0, 1, 1); */
		
        
		grid.setGridLinesVisible(true);
        return grid;
    }
	
	private StackPane addStackPane() {
		StackPane pane = new StackPane();
		pane.getStyleClass().addAll("pane");
		pane.setAlignment(Pos.CENTER); 
		return pane;
	}
	
}
