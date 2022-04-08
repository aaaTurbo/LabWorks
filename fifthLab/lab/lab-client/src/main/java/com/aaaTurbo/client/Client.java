package com.aaaTurbo.client;

import com.aaaTurbo.client.classes.RouteCollection;
import com.aaaTurbo.client.commandLine.CommandListener;
import com.aaaTurbo.client.commands.Command;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    /**
     * Метод, реализующий настройку клиента для работы программы
     */
    private static void clientSetUP() throws Exception {
        RouteCollection mainCollection = new RouteCollection();
        Command loader = new Command.LoadFromFile();
        ((Command.LoadFromFile) loader).setRouteCollection(mainCollection);
        loader.execute(null);
        CommandListener commandListener = new CommandListener(mainCollection);
        System.out.println("--- Клиент успешно настроен ---\n--- Запуск ---");
        commandListener.startListen();
    }

    public static void main(String[] args) throws Exception {
        clientSetUP();
    }
}
