package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import shared.collection.Dragon;
import shared.network.models.Pair;

public class DragonMap {
    private final double scaleFactor;
    private final double screenX = 1000;
    private final double screenY = 1000;
    private final double dragonXShift = 447;
    private final double dragonYShift = 282;
    private final Pane rootPane = new Pane();
    private final StackPane centerPane = new StackPane();
    private final StackPane mainPane = new StackPane(centerPane, rootPane);
    private final Stage stage = new Stage();
    private final Map<Dragon, StackPane> dragons = new HashMap<>();

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

    private Pair<Double, Double> convertFromDragonToScreen(Pair<Double, Double> dragonCoord) {
        Double x = dragonCoord.getValue1();
        Double y = dragonCoord.getValue2();
        Double xScreen = x - dragonXShift / 2; // Сюда можно будет вынести всю сложную логику,
        Double yScreen = y - dragonYShift / 2; // сдвиги, масштаб. Т.о. все драконы будут отрисовываться по единой логике

        return new Pair<Double,Double>(xScreen, yScreen);
    }

    private void setupScene(){
        rootPane.setStyle("-fx-background-color:  #37373E;");
        Scene scene = new Scene(mainPane, screenX, screenY);
        stage.setScene(scene);
    }

    public void addDragon(Dragon dragonI, int colorShift){ // Нужн ли этот метод для нашего сценария использования?
        StackPane dragon = createDragon(colorShift);
        Pair<Double,Double> screenCoord = convertFromDragonToScreen(dragonI.getCoordinates().getDoublePair());
        
        dragon.setLayoutX(screenCoord.getValue1());
        dragon.setLayoutY(screenCoord.getValue2());

        dragons.put(dragonI, dragon);
        rootPane.getChildren().add(dragon);
    }

    public void updateDragon(Dragon dragon) {
        StackPane mapDragon = dragons.get(dragon);
        Pair<Double,Double> screenCoord = convertFromDragonToScreen(dragon.getCoordinates().getDoublePair());

        mapDragon.setLayoutX(screenCoord.getValue1());
        mapDragon.setLayoutY(screenCoord.getValue2());
    }

    public void updateMap() {
        for (Dragon dragon : dragons.keySet()) {
            updateDragon(dragon);
        }
    }


    public void syncWithList(List<Dragon> currentDragons) {
        Set<Dragon> existingDragons = new HashSet<>(dragons.keySet());

        for (Dragon dragon : currentDragons) {
            if (!dragons.containsKey(dragon)) {
                addDragon(dragon, ColorGenerator.generateColorShift(dragon.getOwnerId()));
            } else {
                updateDragon(dragon);
            }
            existingDragons.remove(dragon);
        }

        for (Dragon removedDragon : existingDragons) {
            removeDragon(removedDragon);
        }
    }

    private void removeDragon(Dragon dragon) {
        StackPane removedNode = dragons.remove(dragon);
        if (removedNode != null) {
            rootPane.getChildren().remove(removedNode);
        }
    }

    public Map<Dragon, StackPane> getDragons(){
        return dragons;
    } 

    public void show(){
        stage.show();
    }

}
