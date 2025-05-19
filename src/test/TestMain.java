package test;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.collection.Dragon;

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
                Group group = (Group) stackPanes[j].getChildren().get(0);
                ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(5), group);
                scaleTransition.toXProperty().set(0.25);
                scaleTransition.toYProperty().set(0.25);
                scaleTransition.play();
                String css = CssFormatter.generateCss((i*8 + j)*4 - 200);
                stackPanes[j].getStylesheets().add("data:text/css," + css);
            }
            HBox hBox = new HBox(stackPanes);
            vBox.getChildren().add(hBox);
        }
        
        vBox.setStyle("-fx-background-color:  #37373E;");
        Scene scene = new Scene(vBox);
        
        primaryStage.setScene(scene);
        primaryStage.show();

        DragonMap dragonMap = new DragonMap(0.0625);
        for (int i = 20; i < 850; i+= 50) {
            for (int j = 20; j < 850; j+=50) {
                Dragon dragon = new Dragon.Builder().withId(i * j).build();
                dragonMap.createDragonAt(i, j, i * j, dragon);
            }
        }

        //dragonMap.createDragonAt(256, 256, 0);
        dragonMap.show();
        //System.out.println(dragonMap);
        Dragon dragon = new Dragon.Builder().withId(57400).build(); //test that eq id work
        System.out.println(dragonMap.getDragons().get(dragon));

        
        

        //String stylesheet = ModernInputHandlerDialog.class.getResource("resources/styles.css").toExternalForm(); -- example of stylesheet set
        //but can be set in fxml
    }
    
}
