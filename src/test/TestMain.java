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

        //String stylesheet = ModernInputHandlerDialog.class.getResource("resources/styles.css").toExternalForm(); -- example of stylesheet set
        //but can be set in fxml
        //TODO add styles to make possible to change dragon colors
    }
    
}

class CssFormatter {
    private static final String CSS_TEMPLATE = """
        .color1 {    -fx-fill: hsb(%d, 87.86%%25, 96.85%%25);}
        .color2 {    -fx-fill: hsb(%d, 88.31%%25, 96.88%%25);}
        .color3 {    -fx-fill: hsb(%d, 53.48%%25, 51.32%%25);}
        .color4 {    -fx-fill: hsb(%d, 80.17%%25, 39.56%%25);}
        .color5 {    -fx-fill: hsb(%d, 83.15%%25, 95.68%%25);}
        .color6 {    -fx-fill: hsb(%d, 85.38%%25, 93.35%%25);}
        .color7 {    -fx-fill: hsb(%d, 84.33%%25, 74.87%%25);}
        .color8 {    -fx-fill: hsb(%d, 80.67%%25, 60.84%%25);}
        .color9 {    -fx-fill: hsb(%d, 77.34%%25, 93.75%%25);}
        .color10 {    -fx-fill: hsb(%d, 74.52%%25, 98.82%%25);}
        """;

    public static String generateCss(int hueShift) {
        return String.format(CSS_TEMPLATE,
            (20 + hueShift) % 360,
            (33 + hueShift) % 360,
            (33 + hueShift) % 360,
            (353 + hueShift) % 360,
            (14 + hueShift) % 360,
            (352 + hueShift) % 360,
            (354 + hueShift) % 360,
            (352 + hueShift) % 360,
            (4 + hueShift) % 360,
            (36 + hueShift) % 360
        );
    }
}