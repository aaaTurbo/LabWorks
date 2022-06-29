package com.aaaTurbo.client;

import com.aaaTurbo.client.commandLine.CommandListener;
import com.aaaTurbo.common.commands.Command;
import com.aaaTurbo.common.commands.SavedAnswer;
import com.aaaTurbo.common.commands.SavedCommand;
import com.aaaTurbo.common.util.myExceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Objects;

public final class Client {
    static final int SERVERPORT = 6666;
    static final int TIMEOUT = 5000;

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        clientSetUP(client);
    }

    private static String resieveResponceAfterRequest(DatagramSocket client) throws IOException, ClassNotFoundException {
        client.setSoTimeout(TIMEOUT);
        DatagramPacket answerPack = new DatagramPacket(new byte[client.getReceiveBufferSize()], client.getReceiveBufferSize());
        client.receive(answerPack);
        byte[] bytes = answerPack.getData();
        SavedAnswer answer = SavedAnswer.toSA(bytes);
        return answer.getAns();
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

    public static DatagramPacket create(Command command, String[] args, int port, String user, String password) throws IOException {
        SavedCommand savedCommand = new SavedCommand(port, command, args, user, password);
        byte[] ssc = savedCommand.toBA(savedCommand);
        return new DatagramPacket(ssc, ssc.length, InetAddress.getLocalHost(), SERVERPORT);
    }

    private static String splitUserName(String read) {
        String[] splitedUsername = read.split(" ");
        return splitedUsername[1];
    }

    private static String splitPassword(String read) {
        String[] splitedPass = read.split(" ");
        return splitedPass[2];
    }

    /**
     * Метод, реализующий настройку клиента для работы программы
     */
    private static void clientSetUP(DatagramSocket client) throws Exception {
        CommandListener commandListener = null;
        System.out.println("--- Клиент успешно настроен ---\n--- Запуск ---\n---Войдите или авторизуйтесь! Для аворизации sign_in,"
                + "для регистрации sign_up <user> <passwd>---");
        boolean notAuthorised = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (notAuthorised) {
            try {
                System.out.print("=>");
                String read = reader.readLine();
                if (Objects.equals(splitName(read), "sign_in")) {
                    Command.Autorisation autorisation = new Command.Autorisation();
                    if (autorisation.validate(splitArgs(read))) {
                        client.send(create(autorisation, splitArgs(read), client.getLocalPort(), splitUserName(read), splitPassword(read)));
                        String ans = resieveResponceAfterRequest(client);
                        System.out.println(ans);
                        if (!Objects.equals(ans, "---Ошибка: Неверные данные для авторизации!---")) {
                            notAuthorised = false;
                            commandListener = new CommandListener(client, splitUserName(read), splitPassword(read));
                        }
                    } else {
                        throw new WrongInputException();
                    }
                } else if (Objects.equals(splitName(read), "sign_up")) {
                    Command.Registration registration = new Command.Registration();
                    if (registration.validate(splitArgs(read))) {
                        client.send(create(registration, splitArgs(read), client.getLocalPort(), splitUserName(read), splitPassword(read)));
                        System.out.println(resieveResponceAfterRequest(client));
                    } else {
                        throw new WrongInputException();
                    }
                } else {
                    throw new WrongInputException();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        commandListener.startListen();
    }
}
