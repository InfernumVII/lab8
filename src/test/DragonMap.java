package test;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DragonMap {
    private final double scaleFactor;
    private final double screenX = 1000;
    private final double screenY = 1000;
    private final double dragonXShift = 447;
    private final double dragonYShift = 282;
    private final Pane rootPane = new Pane();
    private final Stage stage = new Stage();

    public DragonMap(double scaleFactor){
        this.scaleFactor = scaleFactor;
        setupScene();
    }

    private StackPane createDragon(int colorShift){
        try {
            StackPane dragon = FXMLLoader.load(TestMain.class.getResource("CoolDragon.fxml"));
            Group group = (Group) dragon.getChildren().get(0);
            group.setScaleX(scaleFactor);
            group.setScaleY(scaleFactor);
            dragon.getStylesheets().add("data:text/css," + CssFormatter.generateCss(colorShift));
            return dragon;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
        
    }

    private void setupScene(){
        rootPane.setStyle("-fx-background-color:  #37373E;");
        Scene scene = new Scene(rootPane, screenX, screenY);
        stage.setScene(scene);
    }

    public void createDragonAt(double x, double y, int colorShift){
        StackPane dragon = createDragon(colorShift);
        dragon.setLayoutX(x - dragonXShift / 2);
        dragon.setLayoutY(y - dragonYShift / 2);
        rootPane.getChildren().add(dragon);
        
    }

    public void show(){
        stage.show();
    }

}
