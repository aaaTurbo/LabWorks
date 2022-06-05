package com.aaaTurbo.common.commands;


import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class SavedAnswer implements Serializable {
    private String answer;

    public SavedAnswer(String ans) {
        answer = ans;
    }

    public byte[] toBA(SavedAnswer sAns) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(boas);
        oos.writeObject(sAns);
        oos.flush();
        boas.close();
        oos.close();
        return boas.toByteArray();
    }

    public static SavedAnswer toSA(byte[] packet) throws IOException, ClassNotFoundException {
        ByteArrayInputStream is = new ByteArrayInputStream(packet);
        ObjectInputStream ois = new ObjectInputStream(is);
        SavedAnswer savedAnswer = (SavedAnswer) ois.readObject();
        is.close();
        ois.close();
        return savedAnswer;
    }

    public String getAns() {
        return answer;
    }
}
