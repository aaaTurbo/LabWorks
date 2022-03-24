package com.aaaTurbo.client.classes;

import com.aaaTurbo.client.myExceptions.NoRouteInCollectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Vector;

public class RouteCollection {
    private HashSet<Route> roadCollection = new HashSet<>();
    private java.time.ZonedDateTime creationDate;

    public RouteCollection() {
        this.creationDate = ZonedDateTime.now();
    }

    public HashSet<Route> getRoadCollection() {
        return roadCollection;
    }

    public Route idSearch(int id) throws IOException {
        Vector<Route> routes = new Vector<>();
        for (Route r : roadCollection) {
            if (r.hashCode() == id) {
                routes.add(r);
            }
        }
        if (routes.size() == 1) {
            return routes.get(0);
        } else if (routes.size() > 1) {
            System.out.println(routes);
            System.out.println("Введите номер объекта, который хотите обновить(нумерация от 1): ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int read = Integer.valueOf(reader.readLine());
            return routes.get(read - 1);
        } else {
            return null;
        }
    }

    public Vector<Route> distanceFilter(long dist) {
        Vector<Route> foundRoutes = new Vector<>();
        for (Route r : roadCollection) {
            if (r.getDistance() == dist) {
                foundRoutes.add(r);
            }
        }
        return foundRoutes;
    }

    public Vector<Route> distanceLessFilter(long dist) {
        Vector<Route> foundRoutes = new Vector<>();
        for (Route r : roadCollection) {
            if (r.getDistance() < dist) {
                foundRoutes.add(r);
            }
        }
        return foundRoutes;
    }

    public Route findMin() {
        final int id = 2147483647;
        Route min = new Route("Я", id);
        for (Route r : roadCollection) {
            if (r.compareTo(min) < 0) {
                min = r;
            }
        }
        return min;
    }

    public Route nameValueFilter() throws NoRouteInCollectionException {
        if (roadCollection.size() == 0) {
            throw new NoRouteInCollectionException();
        }
        Route filteredRoute = new Route("0");
        for (Route r : roadCollection) {
            if (filteredRoute.getName().compareTo(r.getName()) < 0) {
                filteredRoute = r;
            }
        }
        return filteredRoute;
    }

    @Override
    public String toString() {
        return "Коллекция дорог типа HashSet.\nДата создания: " + creationDate.toString() + "\nКоличество элементов: " + roadCollection.size();
    }
}
