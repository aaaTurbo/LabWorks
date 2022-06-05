package com.aaaTurbo.common.commands;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class SavedCommand implements Serializable {
    private String[] args;
    private String name;

    public SavedCommand(Command command, String[] args) {
        name = command.getName();
        this.args = args;
    }

    public byte[] toBA(SavedCommand sCom) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        ObjectOutputStream ois = new ObjectOutputStream(boas);
        ois.writeObject(sCom);
        ois.flush();
        boas.close();
        ois.close();
        return boas.toByteArray();
    }

    private static String splitName(String str) {
        String[] s = str.split(" ");
        String splitedName = s[0];
        return splitedName;
    }

    private static String[] splitArgs(String str) {
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

    public static SavedCommand toSC(byte[] packet) throws IOException, ClassNotFoundException {
        ByteArrayInputStream is = new ByteArrayInputStream(packet);
        ObjectInputStream ois = new ObjectInputStream(is);
        SavedCommand savedCommand = (SavedCommand) ois.readObject();
        is.close();
        ois.close();
        return savedCommand;
    }

    public String[] getArgs() {
        return args;
    }

    public String getName() {
        return name;
    }
}
