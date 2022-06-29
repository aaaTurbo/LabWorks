package com.aaaTurbo.common.util.myExceptions;

public class NoArgsNeededException extends Exception {

    public NoArgsNeededException() {
        super("Команда не требует аргументов, повторите попытку...");
    }
}
