package client.view.customDialog;
import java.util.ArrayList;
import java.util.List;


import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModernInputHandlerDialog {
    private List<Handler<?>> handlers = new ArrayList<>();
    private VBox vBox;
    private StackPane root;
    private Button submitButton = new Button("Submit");
    private Stage stage = new Stage();
    private boolean submitted = false;
    private Text label;

    public ModernInputHandlerDialog(){
        submitButton.setDisable(true);
        submitButton.setOnAction(this::onButtonSubmit);
        vBox = new VBox();
        label = new Text();
        label.getStyleClass().add("label-text");
        label.setEffect(new Glow(0.8));
        StackPane.setAlignment(vBox, Pos.TOP_LEFT);
        StackPane.setMargin(vBox, new Insets(20, 20, 20, 20));
        //StackPane.setAlignment(label, Pos.TOP_LEFT);
        //StackPane.setMargin(label, new Insets(10, 20, 100, 20));
        //VBox.setMargin(label, new Insets(0, 0, 5, 0));
        vBox.getChildren().add(label);
        root = new StackPane(vBox);
        root.setMinWidth(700);
        String stylesheet = ModernInputHandlerDialog.class.getResource("resources/styles.css").toExternalForm();
        root.getStylesheets().add(stylesheet);
        //root.setStyle("-fx-background-color: #28272F;");
    }

    public void add(Handler<?> handler){
        handler.validateStateProperty().addListener((observable, oldValue, newValue) -> {
            checkAllHandlersReady();
        });
        vBox.getChildren().addAll(handler.getNodes());
        handlers.add(handler);
    }

    public void setLabelText(String text){
        label.setText(text);
    }
    
    public void addAll(Handler<?>... handlers){
        for (Handler<?> handler : handlers) {
            handler.validateStateProperty().addListener((observable, oldValue, newValue) -> {
                checkAllHandlersReady();
            });
            vBox.getChildren().addAll(handler.getNodes());
            this.handlers.add(handler);
        }
    }

    private void checkAllHandlersReady(){
        if (handlers.stream().allMatch(Handler::getValidateStateProperty)){
            submitButton.setDisable(false);
        } else {
            submitButton.setDisable(true);
        }
    }

    private StackPane getCompletedRoot(){
        HBox buttonContainer = new HBox(submitButton);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        VBox.setMargin(buttonContainer, new Insets(20, 0, 0, 0));
        vBox.getChildren().add(buttonContainer);
        return root;
    }

    private void onButtonSubmit(ActionEvent event){
        submitted = true;
        stage.close();
    }

    public boolean wasSubmitted(){
        return submitted;
    }

    public void showAndWait(){
        stage.setScene(new Scene(getCompletedRoot()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    } 

    // private void addChilden(Handler children){
    //     childrens.add(children);
    //     vBox.getChildren().addAll(children.getNodes());
    // }
    
    
    
}
