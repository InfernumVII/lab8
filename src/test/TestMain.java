package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TestMain extends Application {
    
    private static final int maxDragonInCols;
    private static final int maxDragonsInRows;
    private static final double smallDragonWidth = 225;
    private static final double smallDragonHeight = 143;
    
    
    static {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        maxDragonInCols = (int) Math.round(primaryScreenBounds.getWidth() / smallDragonWidth) - 1;
        maxDragonsInRows = (int) Math.round(primaryScreenBounds.getHeight() / smallDragonHeight) - 1;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        int cols = maxDragonInCols;
        int rows = maxDragonsInRows;
        VBox vBox = new VBox();
        for (int i = 0; i < rows; i++) {
            StackPane[] stackPanes = new StackPane[cols];
            for (int j = 0; j < cols; j++) {
                stackPanes[j] = FXMLLoader.load(TestMain.class.getResource("CoolDragon0.5x.fxml"));
            }
            HBox hBox = new HBox(stackPanes);
            vBox.getChildren().add(hBox);
        }
        vBox.setStyle("-fx-background-color:  #37373E;");
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();

        //String stylesheet = ModernInputHandlerDialog.class.getResource("resources/styles.css").toExternalForm(); -- example of stylesheet set
        //but can be set in fxml
        //TODO add styles to make possible to change dragon colors
    }
    
}
