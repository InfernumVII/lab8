package shared.collection;
import java.io.Serializable;

/**
 * Класс, представляющий сущность дракона.
 * Реализует интерфейс {@link Comparable<Dragon>} для сортировки по ID.
 */
public class Dragon implements Comparable<Dragon>, Serializable {
    private static final long serialVersionUID = 1L; // уникальный идентификатор версии сериализованного класса. 
    
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0, Поле не может быть null
    private Color color; //Поле не может быть null
    private DragonType type; //Поле не может быть null
    private DragonCharacter character; //Поле не может быть null
    private DragonHead head;
    private int ownerId;

    public Dragon(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.coordinates = builder.coordinates;
        this.creationDate = builder.creationDate;
        this.age = builder.age;
        this.color = builder.color;
        this.type = builder.type;
        this.character = builder.character;
        this.head = builder.head;
        this.ownerId = builder.ownerId;
    }

    /**
     * Внутренний класс для построения объекта Dragon.
     * Использует шаблон проектирования Builder.
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = 1L; // уникальный идентификатор версии сериализованного класса. 
        private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        private String name; //Поле не может быть null, Строка не может быть пустой
        private Coordinates coordinates; //Поле не может быть null
        private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        private Long age; //Значение поля должно быть больше 0, Поле не может быть null
        private Color color; //Поле не может быть null
        private DragonType type; //Поле не может быть null
        private DragonCharacter character; //Поле не может быть null
        private DragonHead head;
        private int ownerId;


        public Builder withOwnerId(int ownerId){
            this.ownerId = ownerId;
            return this;
        }
        public Builder withId(Integer id){
            this.id = id;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }
        
        public Builder withCoordinates(Coordinates coordinates){
            this.coordinates = coordinates;
            return this;
        }

        public Builder withDate(java.time.LocalDate creationDate){
            this.creationDate = creationDate;
            return this;
        }

        public Builder withAge(Long age){
            this.age = age;
            return this;
        }

        public Builder withColor(Color color){
            this.color = color;
            return this;
        }

        public Builder withType(DragonType type){
            this.type = type;
            return this;
        }

        public Builder withCharacter(DragonCharacter character){
            this.character = character;
            return this;
        }

        public Builder withHead(DragonHead head){
            this.head = head;
            return this;
        }

        public Dragon build(){
            return new Dragon(this);
        }
    }

    @Override
    public int compareTo(Dragon other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dragon dragon = (Dragon) o;

        return id.equals(dragon.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Dragon{id=%d, name='%s', coordinates=%s, creationDate=%s, age=%d, color=%s, type=%s, character=%s, head=%s, ownerId=%d}",
                id, name, coordinates, creationDate, age, color, type, character, head, ownerId);
    }

    /**
     * Возвращает ID дракона.
     *
     * @return ID дракона.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Возвращает имя дракона.
     *
     * @return имя дракона.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает координаты дракона.
     *
     * @return координаты дракона.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает дату создания дракона.
     *
     * @return дата создания дракона.
     */
    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает возраст дракона.
     *
     * @return возраст дракона.
     */
    public Long getAge() {
        return age;
    }

    /**
     * Возвращает цвет дракона.
     *
     * @return цвет дракона.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Возвращает тип дракона.
     *
     * @return тип дракона.
     */
    public DragonType getType() {
        return type;
    }

    /**
     * Возвращает характер дракона.
     *
     * @return характер дракона.
     */
    public DragonCharacter getCharacter() {
        return character;
    }

    /**
     * Возвращает голову дракона.
     *
     * @return голова дракона.
     */
    public DragonHead getHead() {
        return head;
    }

    /**
     * Возвращает ID владельца дракона.
     *
     * @return ID владельца дракона.
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    // Установки значений для всех полей (сеттеры):

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(java.time.LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setType(DragonType type) {
        this.type = type;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public void setHead(DragonHead head) {
        this.head = head;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
