package com.aaaTurbo.common.util.classes;

import com.aaaTurbo.common.commands.SavedAnswer;
import com.aaaTurbo.common.util.myExceptions.NoRouteInCollectionException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;

/**
 * Класс, реализующий коллекцию дорог
 */
public class RouteCollection {
    private HashSet<Route> roadCollection = new HashSet<>();
    private ZonedDateTime creationDate;
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
        Route found = roadCollection.stream().filter(x -> x.getId() == id).findFirst().get();
        return found;
    }

    /**
     * Метод, выполняющий поиск по distance
     * @return Object[]
     */
    public Object[] distanceFilter(long dist) {
        Object[] foundRoutes = roadCollection.stream().filter(x -> x.getDistance() == dist).toArray();
        return foundRoutes;
    }

    /**
     * Метод, выполняющий поиск по distance меньших элементов
     * @return Vector<Route>
     */
    public Object[] distanceLessFilter(long dist) {
        Object[] foundRoutes = roadCollection.stream().filter(x -> x.getDistance() < dist).toArray();
        return foundRoutes;
    }

    /**
     * Метод, выполняющий поиск по минимального элемента по значению
     * @return Route
     */
    public Route findMin() {
        final int id = 2147483647;
        final Route[] min = {new Route("Я", id)};
        min[0] = roadCollection.stream().filter(x -> x.compareTo(min[0]) < 0).findFirst().get();
        return min[0];
    }

    /**
     * Метод, выполняющий поиск по значеню имени
     * @return Route
     */
    public Route nameValueFilter(DatagramChannel serverChannel) throws NoRouteInCollectionException {
        if (roadCollection.size() == 0) {
            SavedAnswer savedAnswer = new SavedAnswer(new NoRouteInCollectionException().getMessage());
            try {
                byte[] serializedAnsver = savedAnswer.toBA(savedAnswer);
                final int port = 7777;
                DatagramPacket sendExc = new DatagramPacket(serializedAnsver, serializedAnsver.length, InetAddress.getLocalHost(), port);
                ByteBuffer sendBuf = ByteBuffer.wrap(sendExc.getData());
                serverChannel.send(sendBuf, sendExc.getSocketAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        final Route[] filteredRoute = {new Route("0")};
        filteredRoute[0] = roadCollection.stream().filter(x -> filteredRoute[0].getName().compareTo(x.getName()) < 0).findFirst().get();
        return filteredRoute[0];
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
