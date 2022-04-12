package com.aaaTurbo.client;

import com.aaaTurbo.client.classes.RouteCollection;
import com.aaaTurbo.client.commandLine.CommandListener;
import com.aaaTurbo.client.commands.Command;

import java.io.*;
import java.util.Objects;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    /**
     * Метод, реализующий настройку клиента для работы программы
     */
    private static void clientSetUP() throws Exception {
        RouteCollection mainCollection = new RouteCollection();
        CommandListener commandListener = new CommandListener(mainCollection);
        System.out.println("--- Клиент успешно настроен ---\n--- Запуск ---");
        if (new File(System.getProperty("user.dir") + "/lab-client/src/main/java/com/aaaTurbo/client/commands/lastSavedCollectionBuffer").length() != 0) {
            System.out.println("---Хотите ли вы восстановить последнюю сохрвненную коллекцию?---\n---Введите: да/нет---");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String read = reader.readLine();
            if (Objects.equals("да", read) || Objects.equals("Да", read)) {
                Command.LoadFromFile loader = new Command.LoadFromFile(mainCollection);
                BufferedInputStream readHistoryCollections = new BufferedInputStream(new FileInputStream(new File(System.getProperty("user.dir")) + "/lab-client/src/main/java/com/aaaTurbo/client/commands/lastSavedCollectionBuffer"));
                BufferedReader historyReader = new BufferedReader(new InputStreamReader(readHistoryCollections));
                String lastCollection = historyReader.readLine();
                System.out.println(lastCollection);
                String[] arg = new String[1];
                arg[0] = lastCollection;
                loader.execute(arg);
                commandListener.startListen();
            }
        } else {
            commandListener.startListen();
        }
    }

    public static void main(String[] args) throws Exception {
        clientSetUP();
    }
}
