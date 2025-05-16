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
import java.util.List;
import java.time.LocalDate;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import server.psql.auth.RegistrationEnums;

import java.util.TimerTask;
import java.util.Timer;

import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import server.psql.auth.User;

public class MainSceneController extends AddButton implements Initializable{

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

    private StackPane parent;
    private Circle gradientCircle;

    private final ContextMenu contextMenu = new ContextMenu();

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

    private void updateParent(){
        parent = (StackPane) tableView.getScene().getRoot();
    }

    private void addGradientCircle(){
        gradientCircle = new Circle(100);
        javafx.scene.paint.Color paint = new javafx.scene.paint.Color(1.0, 0.3176, 0.1843, 1.0);
        DropShadow dropShadow = new DropShadow(BlurType.THREE_PASS_BOX, paint, 50, 0.5, 0, 0);
        dropShadow.setWidth(100);
        dropShadow.setHeight(100);
        gradientCircle.setEffect(dropShadow);
        gradientCircle.setStyle("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #ff512f 0.0%, #f09819 100.0%); ");

        gradientCircle.setBlendMode(BlendMode.SCREEN);
        parent.getChildren().add(gradientCircle);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupColumns();
        Platform.runLater(() -> {
            updateParent();
            addGradientCircle(); //Maybe not needed LOOKS UGLY :}
            //TODO add some object to the background to make table looks better
        });
        
        
        currentUserName.setText(AuthController.getCheckUser().getLogin());
    
        setupContextMenu();


        Updater updater = new Updater(this);
        timer.scheduleAtFixedRate(updater, 0, 1000);


        tableView.setRowFactory(tv -> {
            TableRow<Dragon> row = new TableRow<>();
            row.setOnMouseClicked(event -> handleRowRightClick(event, row));
            return row;
        });
        

        // Это просто пример добавления в таблицу, забей, можно вырезать

        Coordinates coordinates = new Coordinates(100, 200);

        DragonHead head = new DragonHead(0.1f);

        // Создание дракона с помощью Builder
        Dragon[] dragons = new Dragon[10];
        for (int index = 0; index < dragons.length; index++) {
            dragons[index] = new Dragon.Builder()
                .withId(index)
                .withName(String.format("GoodBoy %s", index))
                .withCoordinates(coordinates)
                .withDate(LocalDate.now())
                .withAge(150L)
                .withColor(Color.BROWN)
                .withType(DragonType.FIRE)
                .withCharacter(DragonCharacter.CHAOTIC_EVIL)
                .withHead(head)
                .withOwnerId(42)
                .build();
        }
        
        //Типа пример задания элементов
        tableView.setItems(FXCollections.observableArrayList(
            dragons
        ));
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
            Parent mainScene = FXMLLoader.load(LoginSceneController.class.getResource("resources/LoginScene.fxml"));

            mainScene.translateXProperty().set(0);

            parent.getChildren().add(mainScene);
            parent.getChildren().remove(rootVBox);
            parent.getChildren().remove(gradientCircle);

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
    private void logoutClicked(ActionEvent event) {
        AuthController.setCheckUser(null);
        close();
        switchToLoginScene();
        //addDragon();
    }

    private void close(){
        timer.cancel(); // Все?
        //TODO close Thread of Timer
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