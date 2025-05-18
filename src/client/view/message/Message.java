package client.view.message;


import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Message {
    private Stage stage = new Stage();

    public Message(String message){
        Button button = new Button("OK");
        button.setOnAction(this::onAction);
        Text text = new Text(message);
        text.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 20px;");
        HBox hBox = new HBox(text);
        HBox.setMargin(text, new Insets(20, 20, 10, 20));
        hBox.setAlignment(Pos.CENTER);
        HBox hBox2 = new HBox(button);
        HBox.setMargin(button, new Insets(0, 10, 10, 10));
        hBox2.setAlignment(Pos.BOTTOM_RIGHT);
        VBox vbox = new VBox(hBox, hBox2);
        StackPane stackPane = new StackPane(vbox, createFourPolyStars());
        stackPane.setStyle("-fx-background-color: #28272F;");
        stage.setScene(new Scene(stackPane));
        stage.setTitle("Message"); //TODO change name
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        // stage.setOnShown(this::onShow);
        
    }

    private StackPane createFourPolyStars(){
        final StackPane stackPane = new StackPane();
        Pos[] poses = {
            Pos.TOP_LEFT,
            Pos.TOP_RIGHT,
            Pos.BOTTOM_LEFT,
            //Pos.BOTTOM_RIGHT
        };
        for (Pos pos : poses) {
            PolyStar polyStar = new PolyStar(10, 5, 0.6, Color.WHITESMOKE);
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setCycleCount(Timeline.INDEFINITE);
            rotateTransition.setByAngle(1);
            rotateTransition.setNode(polyStar);
            rotateTransition.play();
            StackPane.setAlignment(polyStar, pos);
            stackPane.getChildren().add(polyStar);
        }
        stackPane.setBlendMode(BlendMode.SCREEN);
        stackPane.setMouseTransparent(true);
        stackPane.setOpacity(0.8);
        StackPane.setMargin(stackPane, new Insets(5));
        return stackPane;
    }

    // private void onShow(WindowEvent event){
    //     final StackPane stackPane = (StackPane) ((Stage) event.getSource()).getScene().getRoot();
    //     final double width = stackPane.getWidth();
    //     final double height = stackPane.getHeight();
    //     Bounds bounds = new PolyStar(10, 5, 0.6, Color.WHITESMOKE).getBoundsInLocal();
    //     int polyStarsPerRow = (int) (width / bounds.getWidth()) - 1;
    //     int polyStartPerCol = (int) (height / bounds.getHeight());

    //     VBox vBox = new VBox();
    //     for (int i = 0; i < polyStartPerCol; i++) {
    //         HBox hBox = new HBox();
    //         for (int j = 0; j < polyStarsPerRow; j++) {
    //             PolyStar polyStar = new PolyStar(10, 5, 0.6, Color.WHITESMOKE);
    //             polyStar.setOpacity(0.8);
    //             polyStar.setBlendMode(BlendMode.SCREEN);
    //             hBox.getChildren().add(polyStar);
    //         }
    //         vBox.getChildren().add(hBox);
    //     }
    //     vBox.setMouseTransparent(true);
        
        
    //     stackPane.getChildren().add(vBox);
    // }

    
    
    public void show(){
        stage.show();
    }

    private void onAction(ActionEvent actionEvent){
        stage.close();
    }
}
