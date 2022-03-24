package com.aaaTurbo.client.classes;

public class Location {
    private long x;
    private int y;
    private String name; //Поле может быть null

    public Location(long x, int y, String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException();
        }
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public long getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location l = (Location) o;
        return x == l.x && y == l.y && name == l.name;
    }

    @Override
    public String toString() {
        return "Локация " + name;
    }
}
