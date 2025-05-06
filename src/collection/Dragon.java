package collection;
import java.io.Serializable;
import java.time.LocalDate;

import client.commands.utility.ConsoleInputHandler;

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
    public static class  Builder implements Serializable {
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


        /**
         * Устанавливает ID дракона.
         *
         * @param id ID дракона.
         * @return текущий объект Builder.
         */
        public Builder withOwnerId(int ownerId){
            this.ownerId = ownerId;
            return this;
        }
        public Builder withId(Integer id){
            this.id = id;
            return this;
        }

        /**
         * Устанавливает имя дракона.
         *
         * @param name имя дракона.
         * @return текущий объект Builder.
         */
        public Builder withName(String name){
            this.name = name;
            return this;
        }
        
        /**
         * Устанавливает координаты дракона.
         *
         * @param coordinates координаты дракона.
         * @return текущий объект Builder.
         */
        public Builder withCoordinates(Coordinates coordinates){
            this.coordinates = coordinates;
            return this;
        }

        /**
         * Устанавливает дату создания дракона.
         *
         * @param creationDate дата создания дракона.
         * @return текущий объект Builder.
         */
        public Builder withDate(java.time.LocalDate creationDate){
            this.creationDate = creationDate;
            return this;
        }

        /**
         * Устанавливает возраст дракона.
         *
         * @param age возраст дракона.
         * @return текущий объект Builder.
         */
        public Builder withAge(Long age){
            this.age = age;
            return this;
        }

        /**
         * Устанавливает цвет дракона.
         *
         * @param color цвет дракона.
         * @return текущий объект Builder.
         */
        public Builder withColor(Color color){
            this.color = color;
            return this;
        }

        /**
         * Устанавливает тип дракона.
         *
         * @param type тип дракона.
         * @return текущий объект Builder.
         */
        public Builder withType(DragonType type){
            this.type = type;
            return this;
        }

        /**
         * Устанавливает характер дракона.
         *
         * @param character характер дракона.
         * @return текущий объект Builder.
         */
        public Builder withCharacter(DragonCharacter character){
            this.character = character;
            return this;
        }

        /**
         * Устанавливает голову дракона.
         *
         * @param head голова дракона.
         * @return текущий объект Builder.
         */
        public Builder withHead(DragonHead head){
            this.head = head;
            return this;
        }

        /**
         * Создает объект Dragon с текущими параметрами.
         *
         * @return новый объект Dragon.
         */
        public Dragon build(){
            return new Dragon(this);
        }

    }

    /**
     * Сравнивает драконов по их ID.
     *
     * @param other другой дракон, с которым сравнивается текущий.
     * @return отрицательное число, если текущий дракон меньше, положительное, если больше, и 0, если равны.
     */
    @Override
    public int compareTo(Dragon other) {
        return Integer.compare(this.id, other.id);
    }

    /**
     * Проверяет, равен ли текущий дракон другому объекту.
     *
     * @param o объект для сравнения с текущим драконом.
     * @return {@code true}, если объекты равны, иначе {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dragon dragon = (Dragon) o;

        return id == dragon.id;
    }

    /**
     * Возвращает хэш-код дракона.
     *
     * @return хэш-код дракона.
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Возвращает строковое представление дракона.
     *
     * @return строковое представление дракона.
     */
    @Override
    public String toString() {
        return String.format("Dragon{id=%d, name='%s', coordinates=%s, creationDate=%s, age=%d, color=%s, type=%s, character=%s, head=%s}",
                id, name, coordinates, creationDate, age, color, type, character, head, ownerId);
    }
    
    /**
     * Возвращает ID дракона.
     *
     * @return ID дракона.
     */
    public int getId(){
        return id;
    }

    /**
     * Возвращает имя дракона.
     *
     * @return имя дракона.
     */
    public String getName(){
        return name;
    }

    /**
     * Устанавливает ID дракона.
     *
     * @param id новый ID дракона.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    

    /**
     * Устанавливает имя дракона.
     *
     * @param name новое имя дракона.
     */
    public void setName(String name) {
        this.name = name;
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
     * Устанавливает координаты дракона.
     *
     * @param coordinates новые координаты дракона.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
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
     * Устанавливает дату создания дракона.
     *
     * @param creationDate новая дата создания дракона.
     */
    public void setCreationDate(java.time.LocalDate creationDate) {
        this.creationDate = creationDate;
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
     * Устанавливает возраст дракона.
     *
     * @param age новый возраст дракона.
     */
    public void setAge(Long age) {
        this.age = age;
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
     * Устанавливает цвет дракона.
     *
     * @param color новый цвет дракона.
     */
    public void setColor(Color color) {
        this.color = color;
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
     * Устанавливает тип дракона.
     *
     * @param type новый тип дракона.
     */
    public void setType(DragonType type) {
        this.type = type;
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
     * Устанавливает характер дракона.
     *
     * @param character новый характер дракона.
     */
    public void setCharacter(DragonCharacter character) {
        this.character = character;
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
     * Устанавливает голову дракона.
     *
     * @param head новая голова дракона.
     */
    public void setHead(DragonHead head) {
        this.head = head;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOwnerId(){
        return ownerId;
    }
    

}