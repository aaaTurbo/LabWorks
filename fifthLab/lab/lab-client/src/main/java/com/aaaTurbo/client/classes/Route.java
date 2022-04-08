package com.aaaTurbo.client.classes;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 *Класс объекы которого, хранятся в коллекции
 */
public class Route implements Comparable<Route> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private LocationOne to; //Поле не может быть null
    private Long distance; //Поле не может быть null, Значение поля должно быть больше 1

    public Route(int id, String name, Coordinates coordinates, ZonedDateTime creationDate, Location from, LocationOne to, Long distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route(String name) {
        this.name = name;
    }

    public Route(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Route(String name, Coordinates coordinates, Location from, LocationOne to, Long distance) throws Exception {
        if (name == null || coordinates == null || from == null || to == null || distance < 1) {
            throw new Exception("Введенные данные не удовлетворяют условию");
        }
        final int rand = 2147483647;
        id = (int) (Math.random() * rand);
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route(int id, String name, Coordinates coordinates, Location from, LocationOne to, Long distance) throws Exception {
        if (id == 0 || name == null || coordinates == null || from == null || to == null || distance < 1) {
            throw new Exception("Введенные данные не удовлетворяют условию");
        }
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    /**
     *Метод, который генерирует данные для сохранения элементов коллекции в файл
     *@return String[]
     */
    public String[] generateToSaveInCSV() {
        String[] csv = (id + "," + name + "," + coordinates.getX() + "," + coordinates.getY() + "," + creationDate.toString() + "," + from.getX() + "," + from.getY() + "," + from.getName() + "," + to.getX() + "," + to.getY() + "," + to.getName() + "," + distance).split(",");
        return csv;
    }

    public Long getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Дорога " + id;
    }

    @Override
    public int compareTo(Route o) {
        return name.compareTo(o.name) + id - o.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route route = (Route) o;
        return id == route.id && name.equals(route.name) && coordinates.equals(route.coordinates) && creationDate.equals(route.creationDate) && from.equals(route.from) && to.equals(route.to) && distance.equals(route.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
