package com.aaaTurbo.client.classes;

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
    public int hashCode() {
        return (int) (x + y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinates l = (Coordinates) o;
        return x == l.x && y == l.y;
    }

    @Override
    public String toString() {
        return "Координаты " + x + " ; " + y;
    }
}
