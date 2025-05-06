package collection;

import java.io.Serializable;

/**
 * Класс, представляющий координаты дракона.
 */
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 1L; // уникальный идентификатор версии сериализованного класса. 
    
    private long x; //Значение поля должно быть больше -420
    private long y; //Максимальное значение поля: 699

    /**
     * Конструктор для создания объекта координат.
     *
     * @param x координата x (должна быть больше -420).
     * @param y координата y (должна быть не больше 699).
     */
    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает строковое представление координат.
     *
     * @return строковое представление координат.
     */
    @Override
    public String toString() {
        return String.format("Coordinates{x=%d, y=%d}", x, y);
    }

    /**
     * Возвращает координату x.
     *
     * @return координата x.
     */
    public long getX() {
        return x;
    }

    /**
     * Устанавливает координату x.
     *
     * @param x новая координата x.
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Возвращает координату y.
     *
     * @return координата y.
     */
    public long getY() {
        return y;
    }

    /**
     * Устанавливает координату y.
     *
     * @param y новая координата y.
     */
    public void setY(long y) {
        this.y = y;
    }
}