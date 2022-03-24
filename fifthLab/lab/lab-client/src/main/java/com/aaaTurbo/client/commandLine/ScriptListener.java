package com.aaaTurbo.client.commandLine;

import au.com.bytecode.opencsv.CSVReader;
import com.aaaTurbo.client.classes.RouteCollection;
import com.aaaTurbo.client.commands.Command;
import com.aaaTurbo.client.myExceptions.WrongInputException;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ScriptListener {
    private File file;
    private Command.UtilCommandForSetUp utilCommandForSetUp;
    private CommandSelecter commandSelecter;

    public ScriptListener(RouteCollection routeCollection) {
        utilCommandForSetUp = new Command.UtilCommandForSetUp(routeCollection);
        commandSelecter = new CommandSelecter(utilCommandForSetUp);
    }

    public void setFile(File file) {
        this.file = file;
    }

    private String splitName(String str) {
        String[] s = str.split(" ");
        String splitedName = s[0];
        System.out.println("===" + splitedName + "===");
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

    public void listenScript() throws Exception {
        BufferedInputStream read = new BufferedInputStream(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(new InputStreamReader(read));
        CSVReader csvReader = new CSVReader(reader, ',', '"', 0);
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            try {
                String name = splitName(nextLine[0]);
                String[] args = splitArgs(nextLine[0]);
                Command command = commandSelecter.selectCommand(name);
                if (command == null) {
                    throw new WrongInputException();
                }
                command.execute(args);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("---Скрипт выполнен!---");
    }
}
