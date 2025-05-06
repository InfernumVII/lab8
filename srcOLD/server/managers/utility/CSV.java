package server.managers.utility;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс для работы с CSV-файлами.
 * Предоставляет методы для чтения и записи данных в CSV-формате.
 */
public class CSV {

    /**
     * Читает данные из CSV-файла и возвращает их в виде списка строк.
     *
     * @param name имя файла.
     * @param skipFirstRow пропускать ли первую строку (заголовок).
     * @return список строк, где каждая строка представлена массивом строк.
     */
    public static List<String[]> read(String name, boolean skipFirstRow){
        List<String[]> output = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(name);
             InputStreamReader input = new InputStreamReader(file, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(input)) {
            
            String line = reader.readLine();
            while (line != null) {
                if (!skipFirstRow){
                    output.add(line.split(","));
                } else {
                    skipFirstRow = false;
                }
                line = reader.readLine();
                
            }
            

        } catch (IOException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            return null;
        }
        
        return output;

    }

    /**
     * Читает данные из CSV-файла, пропуская первую строку (заголовок).
     *
     * @param name имя файла.
     * @return список строк, где каждая строка представлена массивом строк.
     */
    public static List<String[]> read(String name) {
        return read(name, true);
    }

    /**
     * Записывает данные в CSV-файл.
     *
     * @param name имя файла.
     * @param data список строк, где каждая строка представлена массивом строк.
     * @param append добавлять ли данные в конец файла.
     */
    public static void write(String name, List<String[]> data, boolean append) {
        try (FileOutputStream file = new FileOutputStream(name, append);
             BufferedOutputStream bufferedOutput = new BufferedOutputStream(file)) {

            for (String[] row : data) {
                String line = String.join(",", row) + "\n";
                bufferedOutput.write(line.getBytes(StandardCharsets.UTF_8));
            }

        } catch (IOException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    /**
     * Записывает данные в CSV-файл, перезаписывая его.
     *
     * @param name имя файла.
     * @param data список строк, где каждая строка представлена массивом строк.
     */
    public static void write(String name, List<String[]> data) {
        write(name, data, false);
    }

    /**
     * Записывает одну строку данных в CSV-файл.
     *
     * @param name имя файла.
     * @param data массив строк, представляющих одну строку данных.
     * @param append добавлять ли данные в конец файла.
     */
    public static void writeOneLine(String name, String[] data, boolean append) {
        try (FileOutputStream file = new FileOutputStream(name, append);
             BufferedOutputStream bufferedOutput = new BufferedOutputStream(file)) {

            String line = String.join(",", data) + "\n";
            bufferedOutput.write(line.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    /**
     * Записывает одну строку данных в CSV-файл, перезаписывая его.
     *
     * @param name имя файла.
     * @param data массив строк, представляющих одну строку данных.
     */
    public static void writeOneLine(String name, String[] data) {
        writeOneLine(name, data, false);
    }
}
