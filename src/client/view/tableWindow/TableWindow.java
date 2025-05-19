package client.view.tableWindow;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.collection.Color;
import shared.collection.Dragon;
import shared.collection.DragonCharacter;
import shared.collection.DragonType;

public class TableWindow {

    @FXML private TableView<Dragon> tableView;
    @FXML private VBox rootVBox;
    @FXML private TableColumn<Dragon, Integer> idColumn;
    @FXML private TableColumn<Dragon, String> nameColumn;
    @FXML private TableColumn<Dragon, Long> coordXColumn;
    @FXML private TableColumn<Dragon, Long> coordYColumn;
    @FXML private TableColumn<Dragon, java.time.LocalDate> creationDateColumn;
    @FXML private TableColumn<Dragon, Long> ageColumn;
    @FXML private TableColumn<Dragon, Color> colorColumn;
    @FXML private TableColumn<Dragon, DragonType> typeColumn;
    @FXML private TableColumn<Dragon, DragonCharacter> characterColumn;
    @FXML private TableColumn<Dragon, Float> numOfEyesColumn;

    private Stage stage = new Stage();

    private void setupColumns(){
        //Привязка колонок к данным
        //А также отключение их смены
        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        idColumn.reorderableProperty().set(false);
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        nameColumn.reorderableProperty().set(false);
        coordXColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCoordinates().getX()));
        coordXColumn.reorderableProperty().set(false);
        coordYColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCoordinates().getY()));
        coordYColumn.reorderableProperty().set(false);
        creationDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCreationDate()));
        creationDateColumn.reorderableProperty().set(false);
        ageColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAge()));
        ageColumn.reorderableProperty().set(false);
        colorColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getColor()));
        colorColumn.reorderableProperty().set(false);
        typeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getType()));
        typeColumn.reorderableProperty().set(false);
        characterColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCharacter()));
        characterColumn.reorderableProperty().set(false);
        numOfEyesColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getHead().getEyesCount()));
        numOfEyesColumn.reorderableProperty().set(false);
    }

    public TableWindow(List<Dragon> data){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/tableWindow.fxml"));
        loader.setController(this);
        try {
            Scene scene = new Scene(loader.load());
            
            stage.setScene(scene);
            stage.setTitle("Result table");
        
            stage.initModality(Modality.APPLICATION_MODAL);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        setupColumns();
        tableView.getItems().setAll(data);
    }
    
    public void show(){
        stage.show();
        stage.setHeight(479);
        stage.setWidth(1112);
    }

    @FXML
    private void onAction(ActionEvent actionEvent){
        stage.close();
    }
}
