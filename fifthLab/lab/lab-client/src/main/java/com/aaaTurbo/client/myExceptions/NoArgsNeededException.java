package com.aaaTurbo.client.myExceptions;

public class NoArgsNeededException extends Exception {

    public NoArgsNeededException() {
        super("Команда не требует аргументов, повторите попытку...");
    }
}
