package client.view.customDialog;
import java.util.ArrayList;
import java.util.List;


import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private final Integer minWidth;
    private final Integer minHeight;


    public ModernInputHandlerDialog() {
        this(450, 560);
    }

    public ModernInputHandlerDialog(Integer minWidth, Integer  minHeight){
        this.minWidth = minWidth;
        this.minHeight = minHeight;

        submitButtonSetup();
        rootSetup();
    }

    private void submitButtonSetup(){
        submitButton.setDisable(true);
        submitButton.setOnAction(this::onButtonSubmit);
    }

    private void onButtonSubmit(ActionEvent event){
        submitted = true;
        stage.close();
    }

    public boolean wasSubmitted(){
        return submitted;
    }

    private void labelSetup(){
        label = new Text();
        label.getStyleClass().add("label-text");
        label.setEffect(new Glow(0.8));
    }
    
    public void setLabelText(String text){
        label.setText(text);
    }

    private void vBoxSetup(){
        labelSetup();
        vBox = new VBox();
        StackPane.setAlignment(vBox, Pos.TOP_LEFT);
        StackPane.setMargin(vBox, new Insets(20, 20, 20, 20));
        vBox.getChildren().add(label);
    }

    private void rootSetup(){
        vBoxSetup();
        root = new StackPane(vBox);
        String stylesheet = ModernInputHandlerDialog.class.getResource("resources/styles.css").toExternalForm();
        root.getStylesheets().add(stylesheet);
    }

    public void add(Handler<?> handler){
        handler.validateStateProperty().addListener((observable, oldValue, newValue) -> {
            checkAllHandlersReady();
        });
        vBox.getChildren().addAll(handler.getNodes());
        handlers.add(handler);
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

    public void showAndWait(){
        StackPane root = getCompletedRoot();
        
        Scene scene = new Scene(root);
        
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
        
        stage.setWidth(minWidth);
        stage.setHeight(minHeight);
        

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    } 
    
    
    
}
