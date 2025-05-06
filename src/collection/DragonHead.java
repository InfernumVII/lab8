package collection;

import java.io.Serializable;

/**
 * Класс, представляющий голову дракона.
 */
public class DragonHead implements Serializable {
    private static final long serialVersionUID = 1L; // уникальный идентификатор версии сериализованного класса. 

    private Float eyesCount; //Поле может быть null и положительным

    /**
     * Конструктор для создания объекта головы дракона.
     *
     * @param eyesCount количество глаз у дракона (может быть null).
     */
    public DragonHead(float eyesCount) {
        this.eyesCount = eyesCount;
    }

    /**
     * Возвращает строковое представление головы дракона.
     *
     * @return строковое представление головы дракона.
     */
    @Override
    public String toString() {
        return String.format("DragonHead{eyesCount=%.1f}", eyesCount);
    }

    /**
     * Возвращает количество глаз у дракона.
     *
     * @return количество глаз у дракона.
     */
    public Float getEyesCount() {
        return eyesCount;
    }

    /**
     * Устанавливает количество глаз у дракона.
     *
     * @param eyesCount новое количество глаз у дракона.
     */
    public void setEyesCount(Float eyesCount) {
        this.eyesCount = eyesCount;
    }
    
}