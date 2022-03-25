package com.aaaTurbo.client.myExceptions;

public class WrongInputException extends Exception {

    public WrongInputException() {
        super("Неправильный ввод. Повторите попытку. Введите help, чтобы ознакомиться с командами");
    }
}
