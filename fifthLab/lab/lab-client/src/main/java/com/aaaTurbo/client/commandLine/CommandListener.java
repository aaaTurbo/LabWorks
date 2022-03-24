package com.aaaTurbo.client.commandLine;

import com.aaaTurbo.client.classes.RouteCollection;
import com.aaaTurbo.client.commands.Command;
import com.aaaTurbo.client.myExceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class CommandListener {
    private boolean inWork = true;
    private int historyCounter = 0;
    private final int historyLength = 12;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Command.UtilCommandForSetUp utilCommandForSetUp;
    private CommandSelecter commandSelecter;
    private String[] historyAttendant = new String[historyLength];

    public CommandListener(RouteCollection routeCollection) {
        utilCommandForSetUp = new Command.UtilCommandForSetUp(routeCollection);
        commandSelecter = new CommandSelecter(utilCommandForSetUp);
        for (int i = 0; i < historyAttendant.length; i++) {
            historyAttendant[i] = "---";
        }
    }

    private void stopListen() {
        inWork = false;
        System.out.println("--- Программа завершена ---");
    }

    private String[] historyAttendant(String str) {
        final int count = 12;
        final int countS = 11;
        if (historyCounter == count) {
            historyAttendant[0] = null;
            for (int i = 0; i < historyAttendant.length - 1; i++) {
                historyAttendant[i] = historyAttendant[i + 1];
            }
            historyAttendant[countS] = str;
        }
        if (historyCounter < count) {
            historyAttendant[historyCounter] = str;
            historyCounter++;
        }
        return historyAttendant;
    }

    private String splitName(String str) {
        String[] s = str.split(" ");
        String splitedName = s[0];
        return splitedName;
    }

    private String[] splitArgs(String str) {
        String[] splited = str.split(" ");
        String[] args = new String[splited.length - 1];
        if (splited.length > 1) {
            int j = 0;
            for (int i = 1; i < splited.length; i++) {
                args[j] = splited[i];
                j++;
            }
        }
        return args;
    }

    public void startListen() throws Exception {
        final int length = 12;
        String[] history = new String[length];
        try {
            while (inWork) {
                System.out.print("Введите команду: ");
                String read = reader.readLine();
                if (Objects.equals(read, "history")) {
                    System.out.println("(нижняя команда - последняя введенная)");
                    for (String s : history) {
                        System.out.println(s);
                    }
                }
                if (Objects.equals(read, "exit")) {
                    stopListen();
                } else {
                    try {
                        String name = splitName(read);
                        String[] args = splitArgs(read);
                        Command command = commandSelecter.selectCommand(name);
                        if (command == null) {
                            throw new WrongInputException();
                        }
                        if (!"history".equals(read) && !"\n".equals(read)) {
                            history = historyAttendant(read);
                        }
                        command.execute(args);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
