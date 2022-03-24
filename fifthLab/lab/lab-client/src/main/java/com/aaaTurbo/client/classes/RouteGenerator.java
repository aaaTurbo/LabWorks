package com.aaaTurbo.client.classes;

import java.time.ZonedDateTime;

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
        int id = Integer.parseInt(args[index]);
        String name = args[++index];
        Coordinates cords = generateCoordinates(args[++index], args[++index]);
        ZonedDateTime creationDate = ZonedDateTime.parse(args[++index]);
        Location loc = generateLocation(args[++index], args[++index], args[++index]);
        LocationOne locO = generateLocationOne(args[++index], args[++index], args[++index]);
        Long dist = Long.valueOf(args[++index]);
        return new Route(id, name, cords, creationDate, loc, locO, dist);
    }
}
