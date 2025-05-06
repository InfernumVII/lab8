package server.managers.utility;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;

/**
 * Класс для преобразования данных о драконах в CSV-формат и обратно.
 * Реализует парсинг строк CSV в объекты {@link Dragon} и обратное преобразование.
 */
public class DragonCSVParser {

    /**
     * Преобразует строку CSV в объект {@link Dragon}.
     *
     * @param row массив строк, представляющих данные о драконе в CSV-формате.
     * @return объект {@link Dragon}, созданный из строки, или {@code null}, если произошла ошибка.
     */
    public static Dragon parseDragonFromRow(String[] row) {
        try {
            Dragon dragon = new Dragon.Builder()
                        .withId(Integer.parseInt(row[0]))
                        .withName(row[1])
                        .withCoordinates(new Coordinates(Long.parseLong(row[2].split(";")[0]), Long.parseLong(row[2].split(";")[1])))
                        .withDate(LocalDate.parse(row[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .withAge(Long.parseLong(row[4]))
                        .withColor(Color.valueOf(row[5]))
                        .withType(DragonType.valueOf(row[6]))
                        .withCharacter(DragonCharacter.valueOf(row[7]))
                        .withHead(new DragonHead(Float.parseFloat(row[8])))
                        .build();

            return dragon;
        } catch (Exception e) {
            System.err.println("Ошибка парсинга: " + String.join(",", row));
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Преобразует объект {@link Dragon} в строку CSV.
     *
     * @param dragon объект {@link Dragon}, который нужно преобразовать.
     * @return массив строк, представляющих данные о драконе в CSV-формате, или {@code null}, если произошла ошибка.
     */
    public static String[] parseRowFromDragon(Dragon dragon) {
        try {
            String[] row = new String[9];
            row[0] = String.valueOf(dragon.getId());
            row[1] = dragon.getName();
            Coordinates coordinates = dragon.getCoordinates();
            row[2] = coordinates.getX() + ";" + coordinates.getY();
            row[3] = dragon.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            row[4] = String.valueOf(dragon.getAge());
            row[5] = dragon.getColor().name();
            row[6] = dragon.getType().name();
            row[7] = dragon.getCharacter().name();
            row[8] = String.valueOf(dragon.getHead().getEyesCount());

            return row;
        } catch (Exception e) {
            System.err.println("Ошибка обратного парсинга для дракона: " + dragon);
            e.printStackTrace();
            return null;
        }
    }
}
