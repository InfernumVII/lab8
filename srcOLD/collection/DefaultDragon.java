package collection;



import client.commands.utility.ConsoleInputHandler;
import collection.Dragon.Builder;

public class DefaultDragon {
    private ConsoleInputHandler consoleInputHandler;
    public DefaultDragon(ConsoleInputHandler consoleInputHandler){
        this.consoleInputHandler = consoleInputHandler;
    }
    public Builder get(){
        return new Dragon.Builder()
                    .withName(consoleInputHandler.promptForString("Введите имя дракона:", false))
                    .withCoordinates(new Coordinates(consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE),
                                                        consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699)))
                    .withAge(consoleInputHandler.promptForLong("Введите возраст дракона:", false, 0, Long.MAX_VALUE))
                    .withColor(consoleInputHandler.promptForEnum("Введите цвет дракона: %s", Color.values(), false))
                    .withType(consoleInputHandler.promptForEnum("Введите тип дракона: %s", DragonType.values(), false))
                    .withCharacter(consoleInputHandler.promptForEnum("Введите характер дракона: %s", DragonCharacter.values(), false))
                    .withHead(new DragonHead(consoleInputHandler.promptForFloat("Введите кол-во глаз у дракона:", true, -Float.MAX_VALUE, Float.MAX_VALUE)));
    }
}
