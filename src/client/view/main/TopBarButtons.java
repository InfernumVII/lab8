package client.view.main;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.function.Consumer;

import javax.naming.Context;

import client.ClientMain;
import client.commands.RemoveGreaterCommand;
import client.internationalization.LocaleController;
import client.view.auth.AuthController;
import client.view.customDialog.EnumPrompt;
import client.view.customDialog.FloatPrompt;
import client.view.customDialog.LongPrompt;
import client.view.customDialog.ModernInputHandlerDialog;
import client.view.customDialog.StringPrompt;
import client.view.message.Message;
import client.view.message.MessageColor;
import client.view.tableWindow.TableWindow;
import client.view.visualizationScope.VisualizationScopeController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import shared.collection.Color;
import shared.collection.Coordinates;
import shared.collection.Dragon;
import shared.collection.DragonCharacter;
import shared.collection.DragonHead;
import shared.collection.DragonType;
import shared.network.exceptions.TimeOutException;
import shared.network.models.Answer;
import shared.network.models.Info;
import shared.network.models.NetCommandAuth;
import shared.network.models.RemoveGreaterCommandArgs;
import shared.network.models.User;

public class TopBarButtons{
    @FXML protected Text addButton;
    @FXML protected Text showButton;
    @FXML protected Text addIfMinButton;
    @FXML protected Text info;
    @FXML protected Menu infoMenu;
    @FXML protected Menu commandsMenu;
    @FXML protected MenuItem clearCommand;
    @FXML protected Menu countByTypeCommand;
    @FXML protected Menu filterByCharacterCommand;
    @FXML protected MenuItem filterLessThanHeadCommand;
    @FXML protected MenuItem removeGreaterCommand;
    @FXML protected TableView<Dragon> tableView;


    protected VisualizationScopeController visualScope;

    private String preFormattedInfo;
    public static ResourceBundle cResourceBundle = LocaleController.getResourceBundle("main/main");

    public TopBarButtons() {
        initInfo();
    }

    protected void initInfo(){
        Platform.runLater(() -> {
            User user = AuthController.getCheckUser();
            String infoText = cResourceBundle.getString("error");
            NetCommandAuth netCommandAuth = new NetCommandAuth("info", null, user);
            Info answerInfo = null;
            try {
                Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
                if (answer.answer().getClass() != String.class){
                    answerInfo = (Info) answer.answer();
                    StringJoiner stringJoiner = new StringJoiner("\n");
                    stringJoiner.add(cResourceBundle.getString("info_c_1") + answerInfo.collectionType());
                    stringJoiner.add(cResourceBundle.getString("info_c_2") + answerInfo.initTime());
                    stringJoiner.add(cResourceBundle.getString("info_c_3")  + "%d");
                    infoText = stringJoiner.toString();
                }
                
            } catch (ClassNotFoundException | IOException | TimeOutException e) {
                e.printStackTrace();
                System.exit(1);
            }
            preFormattedInfo = infoText;
            info.setText(String.format(preFormattedInfo, answerInfo.size()));
        });
        
        
    }

    @FXML
    private void onInfoClicked(Event event) {
        info.setText(String.format(preFormattedInfo, tableView.getItems().size()));
    }

    @FXML
    protected void countByType(Event event){
        MenuItem menuItem = (MenuItem) event.getSource();
        //DragonType dragonType = DragonType.valueOf(menuItem.getText());

        User user = AuthController.getCheckUser();
        NetCommandAuth netCommandAuth = new NetCommandAuth("count_by_type", menuItem.getText(), user);
        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
            Long count = (Long)answer.answer();
            if (count == -1) {
                new Message(cResourceBundle.getString("command_error"), MessageColor.ERROR).show();
            } else {
                new Message(cResourceBundle.getString("count_by_type_success") + menuItem.getText() + ": " + count).show();
            }
        } catch (ClassNotFoundException | IOException | TimeOutException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @FXML
    protected void filterByCharacter(ActionEvent event){
        MenuItem menuItem = (MenuItem) event.getSource();
        DragonCharacter dragonCharacter = DragonCharacter.valueOf(menuItem.getText());

        User user = AuthController.getCheckUser();
        NetCommandAuth netCommandAuth = new NetCommandAuth("filter_by_character", dragonCharacter.toString(), user);
        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
            if(answer.answer() == null) {
                new Message(cResourceBundle.getString("command_error"), MessageColor.ERROR).show();
                return;
            }
            List<Dragon> result = (List<Dragon>)answer.answer();
            new TableWindow(result).show();

        } catch (ClassNotFoundException | IOException | TimeOutException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @FXML
    protected void clear(ActionEvent event){
        User user = AuthController.getCheckUser();

        NetCommandAuth netCommandAuth = new NetCommandAuth("clear", null, user);
        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
            String result = (String)answer.answer();
            if (result.equals("Драконы были очищены!")) {
                new Message(cResourceBundle.getString("clear_success")).show();
            } else if (result.equals("Нет драконов для очистки")) {
                new Message(cResourceBundle.getString("clear_error"), MessageColor.ERROR).show();
            } else {
                new Message(cResourceBundle.getString("command_error"), MessageColor.ERROR).show();
            }
        } catch (ClassNotFoundException | IOException | TimeOutException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @FXML
    protected void removeGreater(ActionEvent event){
        ModernInputHandlerDialog modernInputHandlerDialog = new ModernInputHandlerDialog(450, 270);
        modernInputHandlerDialog.setLabelText(cResourceBundle.getString("remove_greater_title"));
        LongPrompt xPrompt = new LongPrompt(cResourceBundle.getString("x_prompt"), false, -1000, 1000);
        LongPrompt yPrompt = new LongPrompt(cResourceBundle.getString("y_prompt"), false, -1000, 1000);
        modernInputHandlerDialog.addAll(xPrompt, yPrompt);
        modernInputHandlerDialog.showAndWait();
        if (modernInputHandlerDialog.wasSubmitted()){
            long x = xPrompt.getContent();
            long y = xPrompt.getContent();
            User user = AuthController.getCheckUser();
            NetCommandAuth netCommandAuth = new NetCommandAuth("remove_greater", new RemoveGreaterCommandArgs(x,y), user);
            try {
                Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
                String result = (String)answer.answer();
                if (result.equals("Нет драконов для удаления")) {
                    new Message(cResourceBundle.getString("remove_greater_error_1"), MessageColor.ERROR).show();
                } else if (result.equals("Ошибка удаления") || result.equals("Ошибка авторизации")) {
                    new Message(cResourceBundle.getString("command_error"), MessageColor.ERROR).show();
                } else {
                    new Message(String.format(cResourceBundle.getString("remove_greater_success_part1") + "%d" + cResourceBundle.getString("remove_greater_success_part2"), result.split("\n").length)).show();
                }
            } catch (ClassNotFoundException | IOException | TimeOutException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    @FXML
    protected void filterLessThanHead(ActionEvent event){
        ModernInputHandlerDialog modernInputHandlerDialog = new ModernInputHandlerDialog(450, 150); //Q: оно кстати не делается меньше некого размера, хзхзхзх A: Из-за отступов возможно не делается
        modernInputHandlerDialog.setLabelText(cResourceBundle.getString("filter_less_than_head_title"));
        FloatPrompt eyesPrompt = new FloatPrompt(cResourceBundle.getString("eyes_prompt"), false, 1, Float.MAX_VALUE);
        modernInputHandlerDialog.add(eyesPrompt);
        modernInputHandlerDialog.showAndWait();
        if (modernInputHandlerDialog.wasSubmitted()){
            Float eyesCount = eyesPrompt.getContent();

            User user = AuthController.getCheckUser();
            NetCommandAuth netCommandAuth = new NetCommandAuth("filter_less_than_head", eyesCount.toString(), user);
            try {
                Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
                if(answer.answer() == null) {
                    new Message(cResourceBundle.getString("command_error"), MessageColor.ERROR).show();
                    return;
                }
                List<Dragon> result = (List<Dragon>)answer.answer();
                new TableWindow(result).show();    
            } catch (ClassNotFoundException | IOException | TimeOutException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }


    
    @FXML
    protected void onAddMouseEntered(){
        addButton.setStrokeWidth(0.2);
    }   
    
    public void addDragon(String label, Consumer<Dragon> func, Dragon defaultDragon){
        ModernInputHandlerDialog modernInputHandlerDialog = new ModernInputHandlerDialog();
        modernInputHandlerDialog.setLabelText(label);
        StringPrompt dragonNamePrompt = new StringPrompt(cResourceBundle.getString("dragon_name_prompt"), false);
        LongPrompt xPrompt = new LongPrompt(cResourceBundle.getString("x_prompt"), false, -1000, 1000);
        LongPrompt yPrompt = new LongPrompt(cResourceBundle.getString("y_prompt"), false, -1000, 1000);
        LongPrompt agePrompt = new LongPrompt(cResourceBundle.getString("age_prompt"), false, 0, Long.MAX_VALUE);
        EnumPrompt<Color> colorPrompt = new EnumPrompt<>(cResourceBundle.getString("color_prompt"), Color.class, false);
        EnumPrompt<DragonType> typePrompt = new EnumPrompt<>(cResourceBundle.getString("type_prompt"), DragonType.class, false);
        EnumPrompt<DragonCharacter> characterPrompt = new EnumPrompt<>(cResourceBundle.getString("character_prompt"), DragonCharacter.class, false);
        FloatPrompt eyesCountPrompt = new FloatPrompt(cResourceBundle.getString("eyes_prompt"), true, -Float.MAX_VALUE, Float.MAX_VALUE);
        
        modernInputHandlerDialog.addAll(dragonNamePrompt, xPrompt, yPrompt, agePrompt, colorPrompt, typePrompt, characterPrompt, eyesCountPrompt);
        
        if (defaultDragon != null) {
            dragonNamePrompt.setDefaultValue(defaultDragon.getName());
            xPrompt.setDefaultValue(defaultDragon.getCoordinates().getX());
            yPrompt.setDefaultValue(defaultDragon.getCoordinates().getY());
            agePrompt.setDefaultValue(defaultDragon.getAge());
            colorPrompt.setDefaultValue(defaultDragon.getColor());
            typePrompt.setDefaultValue(defaultDragon.getType());
            characterPrompt.setDefaultValue(defaultDragon.getCharacter());
            eyesCountPrompt.setDefaultValue(defaultDragon.getHead().getEyesCount());
        }
        
        modernInputHandlerDialog.showAndWait();
        if (modernInputHandlerDialog.wasSubmitted()){
            
            Dragon createdDragon = new Dragon.Builder()
                .withName(dragonNamePrompt.getContent())
                .withCoordinates(new Coordinates(xPrompt.getContent(), yPrompt.getContent()))
                .withAge(agePrompt.getContent())
                .withColor(colorPrompt.getContent())
                .withType(typePrompt.getContent())
                .withCharacter(characterPrompt.getContent())
                .withHead(new DragonHead(eyesCountPrompt.getContent()))
                .build();

            if(defaultDragon != null) {
                createdDragon.setId(defaultDragon.getId());
                createdDragon.setCreationDate(defaultDragon.getCreationDate());
                createdDragon.setOwnerId(defaultDragon.getOwnerId());
            }

            func.accept(createdDragon);
            //sendAddDragonToServer(createdDragon);
        }
    }

    @FXML
    protected void onAddIfMinMouseClicked(){
        addDragon(cResourceBundle.getString("add_if_min_title"), this::sendAddIfMinDragonToServer, null);
    }

    @FXML
    protected void onAddIfMinMouseEntered(){
        addIfMinButton.setStrokeWidth(0.2);
    }

    @FXML
    protected void onAddIfMinMouseExited(){
        addIfMinButton.setStrokeWidth(0);
    }


    @FXML
    protected void onShowMouseEntered(){
        showButton.setStrokeWidth(0.2);
    }

    @FXML
    protected void onShowMouseExited(){
        showButton.setStrokeWidth(0);
    }


    @FXML
    protected void onAddMouseClicked(){
        addDragon(cResourceBundle.getString("add_title"), this::sendAddDragonToServer, null);
    }

    @FXML
    protected void onShowMouseClicked(){
        visualScope.show();
    }


    protected boolean sendUpdateDragonToServer(Dragon dragon){
        User user = AuthController.getCheckUser();

        NetCommandAuth netCommandAuth = new NetCommandAuth("update", dragon, user);
        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
            if (!"Дракон с ID успешно обновлён!".equals((String)answer.answer())) {
                return false;
            } else {
                return true;
            }
        } catch (ClassNotFoundException | IOException | TimeOutException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }


    private boolean sendAddIfMinDragonToServer(Dragon dragon){
        User user = AuthController.getCheckUser();

        NetCommandAuth netCommandAuth = new NetCommandAuth("add_if_min", dragon, user);
        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
            if ("Новый дракон успешно добавлен.".equals((String)answer.answer())) {
                return true;
            } else if ("Ваш дракон имеет большее значение, чем у минимального элемента коллекции.".equals((String)answer.answer())){
                new Message(cResourceBundle.getString("add_if_min_error")).show();
                return false;
            } else {
                return false;
            }
        } catch (ClassNotFoundException | IOException | TimeOutException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public boolean sendAddDragonToServer(Dragon dragon){
        User user = AuthController.getCheckUser();

        NetCommandAuth netCommandAuth = new NetCommandAuth("add", dragon, user);
        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
            if (!"Добавление нового дракона.\nНовый дракон успешно добавлен.".equals((String)answer.answer())) {
                return false;
            } else {
                return true;
            }
        } catch (ClassNotFoundException | IOException | TimeOutException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    @FXML
    protected void onAddMouseExited(){
        addButton.setStrokeWidth(0);
    }
}
