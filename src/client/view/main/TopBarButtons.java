package client.view.main;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.function.Consumer;

import client.ClientMain;
import client.view.auth.AuthController;
import client.view.customDialog.EnumPrompt;
import client.view.customDialog.FloatPrompt;
import client.view.customDialog.LongPrompt;
import client.view.customDialog.ModernInputHandlerDialog;
import client.view.customDialog.StringPrompt;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
import shared.network.models.User;

public class TopBarButtons{
    @FXML protected Text addButton;
    @FXML protected Text addIfMinButton;
    @FXML protected Text info;

    //COMMANDS MENU
    @FXML protected MenuItem clearCommand;
    @FXML protected MenuItem countByTypeCommand;
    @FXML protected MenuItem filterByCharacterCommand;
    @FXML protected MenuItem filterLessThanHeadCommand;
    @FXML protected MenuItem removeGreaterCommand;

    public TopBarButtons() {
        initInfo();
    }

    private void initInfo(){
        Platform.runLater(() -> {
            User user = AuthController.getCheckUser();
            String infoText = "Error";
            NetCommandAuth netCommandAuth = new NetCommandAuth("info", null, user);
            try {
                Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
                if (answer.answer().getClass() != String.class){
                    Info answerInfo = (Info) answer.answer();
                    StringJoiner stringJoiner = new StringJoiner("\n");
                    stringJoiner.add("Type of collection: " + answerInfo.collectionType());
                    stringJoiner.add("Init time: " + answerInfo.initTime());
                    stringJoiner.add("Num of elements:  " + answerInfo.size());
                    infoText = stringJoiner.toString();
                }
                
            } catch (ClassNotFoundException | IOException | TimeOutException e) {
                e.printStackTrace();
                System.exit(1);
            }
            info.setText(infoText);
        });
        
        
    }  
    
    @FXML
    protected void onAddMouseEntered(){
        addButton.setStrokeWidth(0.2);
    }   
    
    private void addDragon(String label, Consumer<Dragon> func){
        ModernInputHandlerDialog modernInputHandlerDialog = new ModernInputHandlerDialog();
        modernInputHandlerDialog.setLabelText(label);
        StringPrompt dragonNamePrompt = new StringPrompt("Enter the dragon's name", false);
        LongPrompt xPrompt = new LongPrompt("Enter the x coordinate", false, -420, Long.MAX_VALUE);
        LongPrompt yPrompt = new LongPrompt("Enter the y coordinate", false, Long.MIN_VALUE, 699);
        LongPrompt agePrompt = new LongPrompt("Enter the age of the dragon", false, 0, Long.MAX_VALUE);
        EnumPrompt<Color> colorPrompt = new EnumPrompt<>("Enter the color of the dragon", Color.class, false);
        EnumPrompt<DragonType> typePrompt = new EnumPrompt<>("Enter the type of the dragon", DragonType.class, false);
        EnumPrompt<DragonCharacter> characterPrompt = new EnumPrompt<>("Enter the character of the dragon", DragonCharacter.class, false);
        FloatPrompt eyesCountPrompt = new FloatPrompt("Enter the number of eyes of the dragon", true, -Float.MAX_VALUE, Float.MAX_VALUE);
        modernInputHandlerDialog.addAll(dragonNamePrompt, xPrompt, yPrompt, agePrompt, colorPrompt, typePrompt, characterPrompt, eyesCountPrompt);
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

            func.accept(createdDragon);
            //sendAddDragonToServer(createdDragon);
        }
    }

    @FXML
    protected void onAddIfMinMouseClicked(){
        addDragon("AddIfMin command", this::sendAddIfMinDragonToServer);
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
    protected void onAddMouseClicked(){
        addDragon("Creating new Dragon", this::sendAddDragonToServer);
    }

    private boolean sendAddIfMinDragonToServer(Dragon dragon){
        //TODO complete this func
        return false;
    }

    private boolean sendAddDragonToServer(Dragon dragon){
        User user = AuthController.getCheckUser();

        NetCommandAuth netCommandAuth = new NetCommandAuth("add", dragon, user);
        try {
            Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
            System.out.println((String)answer.answer());
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
