package client.view.main;


import java.io.IOException;

import client.ClientMain;
import client.view.auth.AuthController;
import client.view.customDialog.EnumPrompt;
import client.view.customDialog.FloatPrompt;
import client.view.customDialog.LongPrompt;
import client.view.customDialog.ModernInputHandlerDialog;
import client.view.customDialog.StringPrompt;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import shared.collection.Color;
import shared.collection.Coordinates;
import shared.collection.Dragon;
import shared.collection.DragonCharacter;
import shared.collection.DragonHead;
import shared.collection.DragonType;
import shared.network.exceptions.TimeOutException;
import shared.network.models.Answer;
import shared.network.models.NetCommandAuth;
import shared.network.models.User;

public class AddButton {
    @FXML protected Text addButton;

    
    @FXML
    protected void onAddMouseEntered(){
        addButton.setStrokeWidth(0.2);
    }   

    /*    return new Dragon.Builder()
                    .withName(consoleInputHandler.promptForString("Введите имя дракона:", false))
                    .withCoordinates(new Coordinates(consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE),
                                                        consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699)))
                    .withAge(consoleInputHandler.promptForLong("Введите возраст дракона:", false, 0, Long.MAX_VALUE))
                    .withColor(consoleInputHandler.promptForEnum("Введите цвет дракона: %s", Color.values(), false))
                    .withType(consoleInputHandler.promptForEnum("Введите тип дракона: %s", DragonType.values(), false))
                    .withCharacter(consoleInputHandler.promptForEnum("Введите характер дракона: %s", DragonCharacter.values(), false))
                    .withHead(new DragonHead(consoleInputHandler.promptForFloat("Введите кол-во глаз у дракона:", true, -Float.MAX_VALUE, Float.MAX_VALUE))); */
    @FXML
    protected void onAddMouseClicked(){
        ModernInputHandlerDialog modernInputHandlerDialog = new ModernInputHandlerDialog();
        modernInputHandlerDialog.setLabelText("Creating new Dragon");
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

            addDragon(createdDragon);
        }
    }

    private boolean addDragon(Dragon dragon){
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
