package client.commands.utility;
import java.util.Arrays;
import java.util.Scanner;

import server.managers.ServerCommandManager;


/**
 * Класс для обработки ввода данных с консоли.
 * Предоставляет методы для запроса и валидации различных типов данных.
 */
public class ConsoleInputHandler {
    private Scanner scanner;

    /**
     * Конструктор для инициализации объекта.
     *
     * @param commandManager объект {@link ServerCommandManager}, используемый для управления командами и их составными.
     */
    public ConsoleInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }
    

    

    /**
     * Запрашивает у пользователя строковое значение.
     *
     * @param prompt сообщение, которое выводится пользователю.
     * @param allowNull разрешает ли метод возвращать пустую строку.
     * @return введённое пользователем строковое значение.
     */
    public String promptForString(String prompt, boolean allowNull){
        while (true){
            System.out.println(prompt);
            String in = scanner.nextLine();
            if (!allowNull && in.isEmpty()) {
                System.err.println("Значение поля не может быть пустым.");
                continue;
            }
            return in;
        }
        
        
    }

    /**
     * Запрашивает у пользователя целочисленное значение.
     *
     * @param prompt сообщение, которое выводится пользователю.
     * @param allowNull разрешает ли метод возвращать значение по умолчанию (0), если ввод пустой.
     * @param min минимальное допустимое значение (исключительно).
     * @param max максимальное допустимое значение (включительно).
     * @return введённое пользователем целочисленное значение.
     */
    public long promptForLong(String prompt, boolean allowNull, long min, long max){ 
        while (true) {
            System.out.println(String.format(prompt, min, max));
            String inString = scanner.nextLine();
            if (inString.isEmpty()) {
                if (allowNull) {
                    return 0; 
                } else {
                    System.err.println("Значение поля не может быть пустым."); }
                    continue;
            }
            try {
                if (!inString.matches("\\d+")){
                    System.err.println("Поле должно быть целочисленным числом");
                    continue;
                }
                long in = Long.parseLong(inString);

                if (in <= min || in > max) {
                    System.err.printf("Число должно быть между %s и %s.\n", min, max);
                } else {
                    return in; 
                }
            } catch (NumberFormatException e) {
                System.err.printf("Число должно быть между %s и %s.\n", min, max);
            }
        }
    }

    /**
     * Запрашивает у пользователя значение с плавающей точкой.
     *
     * @param prompt сообщение, которое выводится пользователю.
     * @param allowNull разрешает ли метод возвращать значение по умолчанию (0f), если ввод пустой.
     * @param min минимальное допустимое значение (исключительно).
     * @param max максимальное допустимое значение (включительно).
     * @return введённое пользователем значение с плавающей точкой.
     */
    public Float promptForFloat(String prompt, boolean allowNull, Float min, Float max){
        while (true) {
            System.out.println(prompt);
            String inString = scanner.nextLine();
            if (inString.isEmpty()) {
                if (allowNull) {
                    return 0f; 
                } else {
                    System.err.println("Значение поля не может быть пустым.");
                    continue;
                }
            }
            try {
                Float in = Float.parseFloat(inString);

                if (in <= min || in > max) {
                    System.err.printf("Число должно быть между %s и %s.\n", min, max);
                } else {
                    return in; 
                }
            } catch (NumberFormatException e) {
                System.err.println("Поле должно быть числом.");
            }
        }
    }

    /**
     * Запрашивает у пользователя значение из перечисления (enum).
     * Аргумент может быть как строковым представлением значения перечисления (без учета регистра),
     * так и числовым представлением его порядкового номера (начиная с 1).
     *
     * @param <E> тип перечисления.
     * @param prompt сообщение, которое выводится пользователю.
     * @param enums массив значений перечисления.
     * @param allowNull разрешает ли метод возвращать значение по умолчанию (первый элемент перечисления), если ввод пустой.
     * @return введённое пользователем значение из перечисления.
     */
    public <E extends Enum<E>> E promptForEnum(String prompt, E[] enums, boolean allowNull){
        String joinedEnums = Arrays.toString(enums);
        while (true) {
            System.out.println(String.format(prompt, joinedEnums));
            String in = scanner.nextLine();
            if (in.isEmpty()){
                if (allowNull){
                    return enums[0];
                } else {
                    System.err.println("Значение поля не может быть пустым."); 
                    continue;
                }
            }
            boolean isInEnums = false;
            for (E enu : enums) {
                if (in.equalsIgnoreCase(enu.name()) || in.equals(Integer.toString(enu.ordinal() + 1))) {
                    return enu;
                }
            }
            if (isInEnums == false){
                System.err.println(String.format("Поле должно быть одним из вариантов: (%s)", joinedEnums)); 
                continue;
            }
            


        }

    }

}
