package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.collection.Dragon;
import shared.network.models.Pair;

public class DragonMap {
    private final double scaleFactor;
    private final double screenX;
    private final double screenY;
    private final double dragonXShift = 447;
    private final double dragonYShift = 282;
    private final Pane rootPane = new Pane();
    private final StackPane centerPane = new StackPane();
    private final StackPane mainPane = new StackPane(centerPane, rootPane);
    private final Stage stage = new Stage();
    private final Map<Dragon, StackPane> dragons = new HashMap<>();
    private AtomicBoolean selected = new AtomicBoolean(false);
    private Dragon checkDragon = null;

    public DragonMap(double scaleFactor, Integer sceenSizeX, Integer screenSizeY){
        this.screenX = sceenSizeX;
        this.screenY = screenSizeY;
        this.scaleFactor = scaleFactor;
        setupScene();
    }

    public DragonMap(double scaleFactor){
        this(scaleFactor, 1000, 1000);
    }


    private StackPane createDragon(int colorShift){
        try {
            StackPane dragon = FXMLLoader.load(TestMain.class.getResource("CoolDragon.fxml"));
            // Group group = (Group) dragon.getChildren().get(0);
            // group.setScaleX(scaleFactor);
            // group.setScaleY(scaleFactor);
            dragon.setScaleX(scaleFactor);
            dragon.setScaleY(scaleFactor);
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
        Double xScreen = mapRanges(x, -1000d, 1000d, 0d, Math.min(screenX, screenY));
        Double yScreen = mapRanges(y, -1000d, 1000d, Math.min(screenX, screenY), 0d); // Y ось перевернута

        xScreen -= dragonXShift / 2;
        yScreen -= dragonYShift / 2;

        return new Pair<Double,Double>(xScreen, yScreen);
    }

    private Double mapRanges(Double x, Double inMin, Double inMax, Double outMin, Double outMax) {
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    private void setupScene(){
        mainPane.setStyle("-fx-background-color:  #37373E;");
        
        Scene scene = new Scene(mainPane, screenX, screenY);
        scene.setOnMouseClicked(event -> {
            selectDragonByClick(event.getX(), event.getY());
        });
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE && selected.get() == true){
                System.out.println("1234");
                runDeselectAnimation(checkDragon);
            }
        });

        stage.setScene(scene);
        stage.setResizable(false);
        centerPane.toFront();
        
    }

    private void selectDragonByClick(Double x, Double y) {
        System.out.println("123");

        for (Entry<Dragon, StackPane> entry : dragons.entrySet()) {
            StackPane mapDragon = entry.getValue();
            
            Bounds bounds = mapDragon.getBoundsInParent();
            if (bounds.contains(x, y)) {
                System.out.println("Вы выбрали дракона:");
                System.out.println(entry.getKey());

                //runDeselectAnimation(selectedDragon);
                //runSelectAnimation(entry.getKey());
                if (selected.get() == false){
                    checkDragon = entry.getKey();
                    runSelectAnimation(checkDragon);
                }
                
                
            }
        }
    }

    private void runSelectAnimation(Dragon dragon) {
        System.out.println("Select");
        StackPane mapDragon = dragons.get(dragon);
        mapDragon.toFront();
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(500),
                new KeyValue(mapDragon.layoutXProperty(), (screenX-dragonXShift) / 2),
                new KeyValue(mapDragon.layoutYProperty(), (screenY-dragonYShift) / 2),
                new KeyValue(mapDragon.scaleXProperty(), 1.0),
                new KeyValue(mapDragon.scaleYProperty(), 1.0)
            )
        );

        GaussianBlur gaussianBlur = new GaussianBlur();
        rootPane.setEffect(gaussianBlur);
        timeline.setOnFinished(e -> {
            rootPane.getChildren().remove(mapDragon);
            centerPane.getChildren().add(mapDragon);
        });
        selected.set(true);
        timeline.play();

    }


    private void runDeselectAnimation(Dragon dragon) {
        System.out.println("Deselect");
        System.out.println(dragon);
        StackPane mapDragon = dragons.get(dragon);
        Pair<Double, Double> pointGoal = convertFromDragonToScreen(dragon.getCoordinates().getDoublePair());
        
        centerPane.getChildren().remove(mapDragon);
        rootPane.getChildren().add(mapDragon);
        mapDragon.setLayoutX((screenX-dragonXShift) / 2);
        mapDragon.setLayoutY((screenY-dragonYShift) / 2);
        System.out.println(mapDragon.layoutXProperty());
        System.out.println(mapDragon.layoutYProperty());
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(500),
                new KeyValue(mapDragon.layoutXProperty(), pointGoal.getValue1()),
                new KeyValue(mapDragon.layoutYProperty(), pointGoal.getValue2()),
                new KeyValue(mapDragon.scaleXProperty(), scaleFactor),
                new KeyValue(mapDragon.scaleYProperty(), scaleFactor)
            )
        );
        timeline.setOnFinished(e -> {
            selected.set(false);
            rootPane.setEffect(null);
        });
        timeline.play();
        
    }

    public void addDragon(Dragon dragonI, int colorShift){
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
        stage.setHeight(screenY);
        stage.setWidth(screenX);
    }

}
