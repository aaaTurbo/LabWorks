package com.aaaTurbo.client;

import com.aaaTurbo.common.commands.SavedAnswer;
import com.aaaTurbo.common.commands.SavedCommand;
import com.aaaTurbo.client.commandLine.CommandListener;
import com.aaaTurbo.common.commands.Command;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.net.InetAddress;
import java.util.Objects;

public final class Client {
    static final int SERVERPORT = 6666;
    static final int TIMEOUT = 3000;
    static final int CLIENTPORT = 7777;

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    private static void restoreCollection(DatagramSocket client) throws IOException {
        Command.LoadFromFile loader = new Command.LoadFromFile();
        BufferedInputStream readHistoryCollections = new BufferedInputStream(new FileInputStream(new File(System.getProperty("user.dir")) + "/lab-client/src/main/java/com/aaaTurbo/client/files/lastSavedCollectionBuffer"));
        BufferedReader historyReader = new BufferedReader(new InputStreamReader(readHistoryCollections));
        String lastCollection = historyReader.readLine();
        String[] arg = new String[1];
        arg[0] = lastCollection;
        SavedCommand lFF = new SavedCommand(loader, arg);
        byte[] lFFBA = lFF.toBA(lFF);
        DatagramPacket lFFP = new DatagramPacket(lFFBA, lFFBA.length, InetAddress.getLocalHost(), SERVERPORT);
        client.send(lFFP);
        System.out.println("---Ожидание ответа сервера---");
    }

    private static void resiveResponceAfterResoring(DatagramSocket client) throws IOException, ClassNotFoundException {
        client.setSoTimeout(TIMEOUT);
        DatagramPacket answerPack = new DatagramPacket(new byte[client.getReceiveBufferSize()], client.getReceiveBufferSize());
        client.receive(answerPack);
        byte[] bytes = answerPack.getData();
        SavedAnswer answer = SavedAnswer.toSA(bytes);
        System.out.println(answer.getAns());
        System.out.println("--Операция завершена---");
    }

    /**
     * Метод, реализующий настройку клиента для работы программы
     */
    private static void clientSetUP(DatagramSocket client) throws Exception {
        Command.UtilCommandForSetUp.setClientForRequests(client);
        CommandListener commandListener = new CommandListener(client);
        System.out.println("--- Клиент успешно настроен ---\n--- Запуск ---");
        if (new File(System.getProperty("user.dir") + "/lab-client/src/main/java/com/aaaTurbo/client/files/lastSavedCollectionBuffer").length() != 0) {
            System.out.println("---Хотите ли вы восстановить последнюю сохрвненную коллекцию?---\n---Введите: да/нет---");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String read = reader.readLine();
            if (Objects.equals("да", read) || Objects.equals("Да", read)) {
                restoreCollection(client);
                boolean lostConnection = true;
                while (lostConnection) {
                    try {
                        resiveResponceAfterResoring(client);
                        lostConnection = false;
                        commandListener.startListen();
                    } catch (SocketTimeoutException sTE) {
                        System.out.println("---Сервер не отвечает! Хотите повторить подключение?---\n---Введите да/Да или нажмите Enter, чтобы выйти.---");
                        if (!Objects.equals(reader.readLine(), "да") && !Objects.equals(reader.readLine(), "Да") || Objects.equals(reader.readLine(), "\n")) {
                         lostConnection = false;
                        }
                    }
                }
            }  else {
                commandListener.startListen();
            }
        } else {
            commandListener.startListen();
        }
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket(CLIENTPORT, InetAddress.getLocalHost());
        clientSetUP(client);
    }
}
