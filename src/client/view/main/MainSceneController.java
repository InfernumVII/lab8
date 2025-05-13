package client.view.main;

import shared.collection.Color;
import shared.collection.Dragon;
import shared.collection.DragonCharacter;
import shared.collection.DragonType;
import client.view.auth.AuthController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MainSceneController {

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

    @FXML
    private void initialize() {
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
    
}
