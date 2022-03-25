package com.aaaTurbo.client.classes;

import java.util.Objects;

/*
Класс координат, который используются для сосздания route
*/
public class Coordinates {
    private Long x; //Поле не может быть null
    private int y;

    public Coordinates(long x, int y) throws NullPointerException {
        if (x == 0) {
            throw new NullPointerException();
        }
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Координаты " + x + " ; " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinates that = (Coordinates) o;
        return y == that.y && x.equals(that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(y);
    }
}
