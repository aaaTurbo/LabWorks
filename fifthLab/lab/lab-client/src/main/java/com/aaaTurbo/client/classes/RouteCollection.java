package com.aaaTurbo.client.classes;

import com.aaaTurbo.client.myExceptions.NoRouteInCollectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Vector;

/**
 * Класс, реализующий коллекцию дорог
 */
public class RouteCollection {
    private HashSet<Route> roadCollection = new HashSet<>();
    private java.time.ZonedDateTime creationDate;
    private final int rand = 2147483647;
    private int ident = (int) (Math.random() * rand);

    public RouteCollection() {
        this.creationDate = ZonedDateTime.now();
    }

    public HashSet<Route> getRoadCollection() {
        return roadCollection;
    }

    /**
     * Метод, выолняющий поиск в коллекци по полю id объекта
     * @return Route
     */
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

    /**
     * Метод, выполняющий поиск по distance
     * @return Vector<Route>
     */
    public Vector<Route> distanceFilter(long dist) {
        Vector<Route> foundRoutes = new Vector<>();
        for (Route r : roadCollection) {
            if (r.getDistance() == dist) {
                foundRoutes.add(r);
            }
        }
        return foundRoutes;
    }

    /**
     * Метод, выполняющий поиск по distance меньших элементов
     * @return Vector<Route>
     */
    public Vector<Route> distanceLessFilter(long dist) {
        Vector<Route> foundRoutes = new Vector<>();
        for (Route r : roadCollection) {
            if (r.getDistance() < dist) {
                foundRoutes.add(r);
            }
        }
        return foundRoutes;
    }

    /**
     * Метод, выполняющий поиск по минимального элемента по значению
     * @return Route
     */
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

    /**
     * Метод, выполняющий поиск по значеню имени
     * @return Route
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RouteCollection that = (RouteCollection) o;
        return rand == that.rand && ident == that.ident && roadCollection.equals(that.roadCollection) && creationDate.equals(that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ident);
    }
}
