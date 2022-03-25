package com.aaaTurbo.client.classes;

import java.util.Objects;

/*
Класс координат, который используются для сосздания route
*/
public class LocationOne {
    private double x;
    private Float y; //Поле не может быть null
    private String name; //Длина строки не должна быть больше 383, Поле может быть null

    public LocationOne(double x, Float y, String name) throws IllegalArgumentException {
        if (y == 0 || name == null) {
            throw new NullPointerException();
        }
        final int maxLength = 383;
        if (name.length() > maxLength) {
            throw new IllegalArgumentException("Превышен лимит символов для поля name");
        }
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Локация\' " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationOne that = (LocationOne) o;
        return Double.compare(that.x, x) == 0 && y.equals(that.y) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
