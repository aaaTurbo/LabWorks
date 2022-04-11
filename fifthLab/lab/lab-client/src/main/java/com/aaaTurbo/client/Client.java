package com.aaaTurbo.client;

import com.aaaTurbo.client.classes.RouteCollection;
import com.aaaTurbo.client.commandLine.CommandListener;
import com.aaaTurbo.client.commands.Command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        System.out.println("---Хотите ли вы восстановить последнюю сохрвненную коллекцию?\nВведите: да/нет---");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String read = reader.readLine();
        if (Objects.equals("да", read) || Objects.equals("Да", read)){
            Command.LoadFromFile loader = new Command.LoadFromFile(mainCollection);
            String[] arg = new String[1];
            arg[0] = "colleсtion.csv";
            loader.execute(arg);
            commandListener.startListen();
        } else {
            commandListener.startListen();
        }
    }

    public static void main(String[] args) throws Exception {
        clientSetUP();
    }
}
