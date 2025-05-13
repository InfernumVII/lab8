package client.view.main;

import shared.collection.Color;
import shared.collection.Coordinates;
import shared.collection.Dragon;
import shared.collection.DragonCharacter;
import shared.collection.DragonHead;
import shared.collection.DragonType;
import shared.network.exceptions.TimeOutException;
import shared.network.models.Answer;
import shared.network.models.NetCommandAuth;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import client.ClientMain;
import client.view.auth.AuthController;
import client.view.login.LoginSceneController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import server.psql.auth.User;

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
    
        // Инициализация контекстного меню
        setupContextMenu();

        tableView.setRowFactory(tv -> {
            TableRow<Dragon> row = new TableRow<>();
            row.setOnMouseClicked(event -> handleRowRightClick(event, row));
            return row;
        });
        
        

        // Это просто пример добавления в таблицу, забей, можно вырезать

        // Coordinates coordinates = new Coordinates(100, 200);

        // DragonHead head = new DragonHead(0.1f);

        // // Создание дракона с помощью Builder
        // Dragon dragon = new Dragon.Builder()
        //         .withId(1)
        //         .withName("Smaug")
        //         .withCoordinates(coordinates)
        //         .withDate(LocalDate.now())
        //         .withAge(150L)
        //         .withColor(Color.BROWN)
        //         .withType(DragonType.FIRE)
        //         .withCharacter(DragonCharacter.CHAOTIC_EVIL)
        //         .withHead(head)
        //         .withOwnerId(42)
        //         .build();
        // //Типа пример задания элементов
        // tableView.setItems(FXCollections.observableArrayList(
        //     dragon
        // ));
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


    @FXML
    private void logoutClicked() {
        AuthController.setCheckUser(null);
        //switchToLoginScene();
        addDragon();
    }

    private void addDragon(){
        User user = AuthController.getCheckUser();

        Dragon.Builder builder = new Dragon.Builder()
                    .withName("Абоба")
                    .withCoordinates(new Coordinates(1, 1))
                    .withAge(1L)
                    .withColor(Color.values()[0])
                    .withType(DragonType.values()[1])
                    .withCharacter(DragonCharacter.values()[0])
                    .withHead(new DragonHead(1f));
        NetCommandAuth netCommandAuth = new NetCommandAuth("add", builder, user);
        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
        } catch (ClassNotFoundException | IOException | TimeOutException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
    }
    
}
