package com.aaaTurbo.common.util.myExceptions;

public class NoRouteInCollectionException extends Exception {

    public NoRouteInCollectionException() {
        super("В коллекции нет ни одного элемента!");
    }
}
