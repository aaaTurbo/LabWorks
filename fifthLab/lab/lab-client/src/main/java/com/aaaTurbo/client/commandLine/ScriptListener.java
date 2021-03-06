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
import java.util.Objects;
import java.util.Vector;

/**
 * Класс, реализающий чтение и выполнение скриптов
 */
public class ScriptListener {
    private static Vector<String> executedScripts = new Vector<>();
    private File file;
    private Command.UtilCommandForSetUp utilCommandForSetUp;
    private CommandSelecter commandSelecter;


    public ScriptListener(RouteCollection routeCollection) {
        utilCommandForSetUp = new Command.UtilCommandForSetUp(routeCollection);
        commandSelecter = new CommandSelecter(utilCommandForSetUp);
    }

    /**
     * Метод, устанавливающий файл
     * @param file
    */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Метод, выделяющий имя из параметров командной строки
     * @param str
     * @return String
     */
    private String splitName(String str) {
        String[] s = str.split(" ");
        String splitedName = s[0];
        System.out.println("===" + splitedName + "===");
        return splitedName;
    }

    /**
     * Метод, выделяющий аргументы из параметров командной строки
     * @param str
     * @return String
    */
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

    /**
     * Метод, реализующий парсинг и выполнения скрипт
     */
    public void listenScript() throws Exception {
        BufferedInputStream read = new BufferedInputStream(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(new InputStreamReader(read));
        CSVReader csvReader = new CSVReader(reader, ',', '"', 0);
        String[] nextLine;
        executedScripts.add(file.getName());
        while ((nextLine = csvReader.readNext()) != null) {
            try {
                String name = splitName(nextLine[0]);
                String[] args = splitArgs(nextLine[0]);
                if ("execute_script".equals(name)) {
                    int size = executedScripts.size();
                    for (int i = 0; i < size; i++) {
                        if (executedScripts.get(i).equals(args[0])) {
                            throw new Exception("Такой скрипт уже выполнялся!");
                        } else {
                            executedScripts.add(args[0]);
                        }
                    }
                }
                Command command = commandSelecter.selectCommand(name);
                if (command == null) {
                    throw new WrongInputException();
                }
                command.execute(args);
                if ("execute_script".equals(name)) {
                    executedScripts.remove(executedScripts.lastElement());
                    executedScripts.remove(executedScripts.lastElement());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("---Скрипт выполнен!---");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScriptListener that = (ScriptListener) o;
        return file.equals(that.file) && utilCommandForSetUp.equals(that.utilCommandForSetUp) && commandSelecter.equals(that.commandSelecter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
