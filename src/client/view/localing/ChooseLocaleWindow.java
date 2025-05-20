package client.view.localing;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import javax.swing.Action;

import client.internationalization.LocaleController;
import client.internationalization.Locales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChooseLocaleWindow {
    private VBox vBox = new VBox();
    private StackPane root = new StackPane(vBox);
    private Stage stage = new Stage();
    
    public ChooseLocaleWindow(){
        //root = FXMLLoader.load(ChooseLocaleWindow.class.getResource("resources/ChooseLocaleWindow.fxml"));
        setupScene();
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.setScene(new Scene(root));
        stage.setTitle("Choose your locale");
        stage.setResizable(false);
    }

    private void setupScene(){
        StackPane.setMargin(vBox, new Insets(20));
        MenuButton menuButton = new MenuButton("Choose your locale");
        Stream.of(Locales.values()).forEach(l -> {
            MenuItem menuItem = new MenuItem(l.getNameInLocale());
            menuItem.setOnAction(this::changeLocale);
            menuButton.getItems().add(menuItem);
        });
        HBox hBox = new HBox(menuButton);
        vBox.getChildren().add(hBox);
        String styleSheet = ChooseLocaleWindow.class.getResource("resources/styles.css").toExternalForm();
        root.getStylesheets().add(styleSheet);
    }

    private void changeLocale(ActionEvent event){
        MenuItem menuItem = (MenuItem) event.getSource();
        Optional<Locales> locale = Stream.of(Locales.values()).filter(l -> l.getNameInLocale().equals(menuItem.getText())).findFirst();
        LocaleController.setCurrentLocale(locale.get());
        stage.close();
    }

    public void show(){
        stage.show();
    }

    public void showAndWait(){
        stage.showAndWait();
    }
}
