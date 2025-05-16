package client.view.main;

import java.util.HashSet;
import java.util.Set;

import client.view.customDialog.EnumPrompt;
import client.view.customDialog.FloatPrompt;
import client.view.customDialog.LongPrompt;
import client.view.customDialog.ModernInputHandlerDialog;
import client.view.customDialog.StringPrompt;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import shared.collection.Color;
import shared.collection.DragonCharacter;
import shared.collection.DragonType;

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
            
            //dragonNamePrompt.getContent() -- пример получения данных
            //TODO добавить отправку команды на сервер
        }
    }   

    @FXML
    protected void onAddMouseExited(){
        addButton.setStrokeWidth(0);
    }
}
