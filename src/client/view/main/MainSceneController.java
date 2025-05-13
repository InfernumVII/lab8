package client.view.main;

import shared.collection.Color;
import shared.collection.Dragon;
import shared.collection.DragonCharacter;
import shared.collection.DragonType;
import shared.network.exceptions.TimeOutException;
import shared.network.models.Answer;
import shared.network.models.NetCommandAuth;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;

import client.ClientMain;
import client.view.auth.AuthController;
import client.view.login.LoginSceneController;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import server.psql.auth.RegistrationEnums;

import java.util.TimerTask;
import java.util.Timer;


public class MainSceneController implements Initializable{

    @FXML private VBox rootVBox;
    @FXML private TableView<Dragon> tableView;
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
    @FXML private Canvas canvas;
    @FXML private MenuItem logoutMenuItem;
    @FXML private Menu currentUserName;

    private Timer timer = new Timer();


    private final ContextMenu contextMenu = new ContextMenu();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Привязка колонок к данным
        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        coordXColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCoordinates().getX()));
        coordYColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCoordinates().getY()));
        creationDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCreationDate()));
        ageColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAge()));
        colorColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getColor()));
        typeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getType()));
        characterColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCharacter()));
        numOfEyesColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getHead().getEyesCount()));

        currentUserName.setText(AuthController.getCheckUser().getLogin());
    
        setupContextMenu();


        Updater updater = new Updater(this);
        timer.scheduleAtFixedRate(updater, 0, 1000);


        tableView.setRowFactory(tv -> {
            TableRow<Dragon> row = new TableRow<>();
            row.setOnMouseClicked(event -> handleRowRightClick(event, row));
            return row;
        });

    }


    private void setupContextMenu() {
        MenuItem editItem = new MenuItem("Изменить");
        MenuItem deleteItem = new MenuItem("Удалить");
    
        editItem.setOnAction(event -> {
            Dragon selectedDragon = tableView.getSelectionModel().getSelectedItem();
            if (selectedDragon != null) {
                // TODO: Реализовать окно редактирования
                System.out.println("Изменить: " + selectedDragon);
            }
        });

        deleteItem.setOnAction(event -> {
            Dragon selectedDragon = tableView.getSelectionModel().getSelectedItem();
            if (selectedDragon != null) {
                // TODO: Реализовать подтверждение и удаление
                
                tableView.getItems().remove(selectedDragon);
                System.out.println("Удалить: " + selectedDragon);
            }
        });
    
        contextMenu.getItems().addAll(editItem, deleteItem);
    }
    
    private void handleRowRightClick(MouseEvent event, TableRow<Dragon> row) {
        if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
            tableView.getSelectionModel().select(row.getItem());
            contextMenu.show(row, event.getScreenX(), event.getScreenY());
        } else {
            contextMenu.hide();
        }
    }



    public void switchToLoginScene() {
        try {
            Scene currentScene = tableView.getScene();

            StackPane parent = (StackPane) currentScene.getRoot();
            Parent mainScene = FXMLLoader.load(LoginSceneController.class.getResource("resources/LoginScene.fxml"));

            mainScene.translateXProperty().set(0);

            parent.getChildren().add(mainScene);
            parent.getChildren().remove(rootVBox);

            currentScene.getWindow().sizeToScene();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTable () {
        // TODO
        System.out.println("Update");

        NetCommandAuth netCommandAuth = new NetCommandAuth("show", null, AuthController.getCheckUser());

        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
            List<Dragon> answerList = (List<Dragon>) answer.answer();
            Platform.runLater(() -> tableView.setItems(FXCollections.observableList(answerList)));

        } catch (IOException | ClassNotFoundException | TimeOutException e){
            e.printStackTrace();
            System.exit(1);
        }
    }


    @FXML
    private void logoutClicked() {
        timer.cancel();
        AuthController.setCheckUser(null);
        switchToLoginScene();
    }
    
}


class Updater extends TimerTask {
    private MainSceneController controller;

    public Updater(MainSceneController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.updateTable();
    }
}