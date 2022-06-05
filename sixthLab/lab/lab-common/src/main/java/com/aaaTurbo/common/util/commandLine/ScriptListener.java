package com.aaaTurbo.common.util.commandLine;

import au.com.bytecode.opencsv.CSVReader;
import com.aaaTurbo.common.commands.Command;
import com.aaaTurbo.common.commands.SavedAnswer;
import com.aaaTurbo.common.commands.SavedCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Vector;

/**
 * Класс, реализающий чтение и выполнение скриптов
 */
public class ScriptListener {
    private static Vector<String> executedScripts = new Vector<>();
    private final int port = 6666;
    private final int timeOut = 3000;
    private DatagramSocket client;
    private boolean recurs = false;
    private File file;
    private Command.UtilCommandForSetUp utilCommandForSetUp;
    private CommandSelecter commandSelecter;
    private DatagramPacket alivePac = new DatagramPacket(new byte[0], 0, InetAddress.getLocalHost(), port);


    public ScriptListener(DatagramSocket cl) throws UnknownHostException {
        utilCommandForSetUp = new Command.UtilCommandForSetUp();
        commandSelecter = new CommandSelecter(utilCommandForSetUp);
        client = cl;
    }

    /**
     * Метод, устанавливающий файл
     *
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Метод, выделяющий имя из параметров командной строки
     *
     * @param str
     * @return String
     */
    private String splitName(String str) {
        String[] s = str.split(" ");
        String splitedName = s[0];
        return splitedName;
    }

    public static void addFirstScript(String args) {
        executedScripts.add(args);
    }

    /**
     * Метод, выделяющий аргументы из параметров командной строки
     *
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
     * Метод, реализующий парсинг и выполнение скрипт
     */
    public void listenScript() throws Exception {
        client.setSoTimeout(timeOut);
        BufferedInputStream read = new BufferedInputStream(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(new InputStreamReader(read));
        CSVReader csvReader = new CSVReader(reader, ',', '"', 0);
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            try {
                String name = splitName(nextLine[0]);
                System.out.println("---" + name + "---");
                String[] args = splitArgs(nextLine[0]);
                if ("execute_script".equals(name)) {
                    for (String executedScript : executedScripts) {
                        if (args[0].equals(executedScript)) {
                            System.out.println("---В скрипте содержится рекурсивыный цикл! Остановка выполнения!---");
                            recurs = true;
                            break;
                        }
                    }
                    if (!recurs) {
                        executeOtherScriptWirhoutRecurs(args);
                    }
                } else {
                    Command command = commandSelecter.selectCommand(name);
                    if (command != null && command.validate(args)) {
                       sendAndrecieve(command, args);
                    } else {
                        System.out.println("---Выполенение остановленно!---");
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stopScriptListening();
    }

    private void executeOtherScriptWirhoutRecurs(String[] args) throws Exception {
        executedScripts.add(file.getName());
        Command executeScript = new Command.ExecuteScript();
        if (executeScript.validate(args)) {
            executeScript.execute(args);
        }
    }

    private void stopScriptListening() {
        executedScripts.remove(executedScripts.lastElement());
        System.out.println("---Скрипт Выполнен!---");
    }

    private void sendAndrecieve(Command command, String[] args) throws IOException, ClassNotFoundException {
        SavedCommand savedCommand = new SavedCommand(command, args);
        byte[] savedCommandBytes = savedCommand.toBA(savedCommand);
        DatagramPacket datagramPacket = new DatagramPacket(savedCommandBytes, savedCommandBytes.length, InetAddress.getLocalHost(), port);
        client.send(datagramPacket);
        DatagramPacket answerPack = new DatagramPacket(new byte[client.getReceiveBufferSize()], client.getReceiveBufferSize());
        client.receive(answerPack);
        byte[] bytes = answerPack.getData();
        SavedAnswer answer = SavedAnswer.toSA(bytes);
        System.out.println(answer.getAns());
        System.out.println("--Операция завершена---");
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
