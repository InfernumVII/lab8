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
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.IOException;

import client.ClientMain;
import client.internationalization.LocaleController;
import client.internationalization.Locales;
import client.internationalization.Localizable;
import client.view.auth.AuthController;
import client.view.login.LoginSceneController;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

import java.util.TimerTask;
import java.util.stream.Stream;
import java.util.Timer;

import javafx.scene.shape.Circle;

public class MainSceneController extends TopBarButtons implements Initializable, Localizable{

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
    @FXML private Canvas canvas;
    @FXML private MenuItem logoutMenuItem;
    @FXML private Menu currentUserName;
    @FXML private Menu languageMenu;

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
        gradientCircle.setMouseTransparent(true);

        parent.getChildren().add(gradientCircle);
    }

    private void initMenuLanguage(){
        Stream.of(Locales.values()).forEach(l -> {
            MenuItem menuItem = new MenuItem(l.getNameInLocale());
            menuItem.setOnAction(this::changeLocale);
            languageMenu.getItems().add(menuItem);
        });
    }

    private void changeLocale(ActionEvent event){
        MenuItem menuItem = (MenuItem) event.getSource();
        Optional<Locales> locale = Stream.of(Locales.values()).filter(l -> l.getNameInLocale().equals(menuItem.getText())).findFirst();
        LocaleController.setCurrentLocale(locale.get());
        updateSceneWithLocale();
    }

    @Override
    public void updateSceneWithLocale() {
        MainSceneController.cResourceBundle = LocaleController.getResourceBundle("main/main");
        addButton.setText(cResourceBundle.getString("add"));
        addIfMinButton.setText(cResourceBundle.getString("add_if_min"));
        infoMenu.setText(cResourceBundle.getString("info"));
        logoutMenuItem.setText(cResourceBundle.getString("log_out"));
        commandsMenu.setText(cResourceBundle.getString("commands"));
        clearCommand.setText(cResourceBundle.getString("clear"));
        countByTypeCommand.setText(cResourceBundle.getString("count_by_type"));
        filterByCharacterCommand.setText(cResourceBundle.getString("filter_by_character"));
        filterLessThanHeadCommand.setText(cResourceBundle.getString("filter_less_than_head"));
        removeGreaterCommand.setText(cResourceBundle.getString("remove_greater"));


        initInfo();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupColumns();
        initMenuLanguage();
        updateSceneWithLocale();

        Platform.runLater(() -> {
            updateParent();
            addGradientCircle();
            tableView.getScene().getWindow().setOnCloseRequest(e -> {
                close();
            });

        });
        
        
        currentUserName.setText(AuthController.getCheckUser().getLogin());

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    updateTable();
                });
            }
        }, 0, 1000);


        tableView.setRowFactory(tv -> {
            TableRow<Dragon> row = new TableRow<>();
            row.setOnMouseClicked(event -> handleRowRightClick(event, row));
            return row;
        });
        

        updateTable();
    }


    private void setupPermittedContextMenu() {
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
    
        editItem.setOnAction(event -> {
            Dragon selectedDragon = tableView.getSelectionModel().getSelectedItem();
            if (selectedDragon != null) {
                addDragon("Edit dragon", this::sendUpdateDragonToServer, selectedDragon);   
            }
        });

        deleteItem.setOnAction(event -> {
            System.out.println("event");
            Dragon selectedDragon = tableView.getSelectionModel().getSelectedItem();
            if (selectedDragon != null) {
                NetCommandAuth netCommandAuth = new NetCommandAuth("remove_by_id", selectedDragon.getId().toString(), AuthController.getCheckUser());
                try{
                    Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
                    String strAnswer = (String)answer.answer();
                    if (strAnswer.equals("Дракон удалён.")) {
                        tableView.getItems().remove(selectedDragon);
                    }
                } catch (IOException | ClassNotFoundException | TimeOutException e){
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        });
    
        contextMenu.getItems().setAll(editItem, deleteItem);
    }

    


    private void setupForbiddenContextMenu () {
        MenuItem forbiddenItem = new MenuItem(cResourceBundle.getString("edit_error"));
        contextMenu.getItems().setAll(forbiddenItem);
    }
    
    private void handleRowRightClick(MouseEvent event, TableRow<Dragon> row) {
        if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
            Dragon tableItem = row.getItem();

            tableView.getSelectionModel().select(tableItem);

            if (AuthController.getUserId() == tableItem.getOwnerId()) {
                setupPermittedContextMenu(); 
            } else {
                setupForbiddenContextMenu();
            }
            
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
        NetCommandAuth netCommandAuth = new NetCommandAuth("show", null, AuthController.getCheckUser());

        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
            List<Dragon> answerList = (List<Dragon>) answer.answer();

            Dragon selectedDragon = tableView.getSelectionModel().getSelectedItem();
            
            //TODO update only if update is needed
            tableView.getItems().setAll(answerList);
            tableView.sort();

            if (selectedDragon != null) {
                tableView.getSelectionModel().select(selectedDragon);
            }
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
    }

    private void close(){
        timer.cancel(); // Все? Yes, that's enough
    }
}
