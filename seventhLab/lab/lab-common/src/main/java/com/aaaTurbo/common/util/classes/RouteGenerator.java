package com.aaaTurbo.common.util.classes;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс реализующий генерацию
 */
public class RouteGenerator {
    private String generatorName = "Генератор дорог";

    public RouteGenerator() {
    }

    private Coordinates generateCoordinates(String x, String y) {
        long xT = Long.parseLong(x);
        int yT = Integer.parseInt(y);
        return new Coordinates(xT, yT);
    }

    private Location generateLocation(String x, String y, String name) {
        long xT = Long.parseLong(x);
        int yT = Integer.parseInt(y);
        return new Location(xT, yT, name);
    }

    private LocationOne generateLocationOne(String x, String y, String name) {
        double xT = Double.parseDouble(x);
        Float yT = Float.valueOf(y);
        return new LocationOne(xT, yT, name);
    }

    public Route generateRoute(String[] args) throws Exception {
        int index = 0;
        String name = args[index];
        Coordinates cords = generateCoordinates(args[++index], args[++index]);
        Location loc = generateLocation(args[++index], args[++index], args[++index]);
        LocationOne locO = generateLocationOne(args[++index], args[++index], args[++index]);
        Long dist = Long.valueOf(args[++index]);
        return new Route(name, cords, loc, locO, dist);
    }

    public Route generateRouteWithId(String[] args) throws Exception {
        int index = 0;
        int id = Integer.parseInt(args[index]);
        String name = args[++index];
        Coordinates cords = generateCoordinates(args[++index], args[++index]);
        Location loc = generateLocation(args[++index], args[++index], args[++index]);
        LocationOne locO = generateLocationOne(args[++index], args[++index], args[++index]);
        Long dist = Long.valueOf(args[++index]);
        return new Route(id, name, cords, loc, locO, dist);
    }

    public Route generateRouteWithIdAndTime(String[] args) throws Exception {
        int index = 0;
        long id = Long.parseLong(args[index]);
        String name = args[++index];
        Coordinates cords = generateCoordinates(args[++index], args[++index]);
        LocalDate creationDate = LocalDate.parse(args[++index]);
        Location loc = generateLocation(args[++index], args[++index], args[++index]);
        LocationOne locO = generateLocationOne(args[++index], args[++index], args[++index]);
        Long dist = Long.valueOf(args[++index]);
        int ownerID = Integer.parseInt(args[++index]);
        Route ret = new Route(name, cords, creationDate, loc, locO, dist, ownerID);
        ret.setId(id);
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RouteGenerator that = (RouteGenerator) o;
        return Objects.equals(generatorName, that.generatorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generatorName);
    }
}
