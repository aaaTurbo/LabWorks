package com.aaaTurbo.client.myExceptions;

public class NoRouteInCollectionException extends Exception {

    public NoRouteInCollectionException() {
        super("В коллекции нет ни одного элемента!");
    }
}
