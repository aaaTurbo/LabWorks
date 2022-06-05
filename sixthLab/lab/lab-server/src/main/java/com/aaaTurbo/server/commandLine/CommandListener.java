package com.aaaTurbo.server.commandLine;

import com.aaaTurbo.common.commands.Command;
import com.aaaTurbo.common.util.myExceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandListener implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);
    private static boolean inwork;
    private Thread cL = new Thread(this);
    private RequestListener rL;

    public CommandListener(boolean inwork, RequestListener requestListener) {
        this.inwork = inwork;
        rL = requestListener;
    }

    private String splitName(String str) {
        String[] s = str.split(" ");
        String splitedName = s[0];
        return splitedName;
    }

    public Thread getcL() {
        return cL;
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

    @Override
    public void run() {
        while (inwork) {
            try {
                LOGGER.info("===You can type the command at any moment you want to!===");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String read = reader.readLine();
                String name = splitName(read);
                String[] args = splitArgs(read);
                if (Objects.equals(name, "exit")) {
                    rL.stopWork();
                    inwork = false;
                    cL.stop();
                }
                if (Objects.equals(name, "save")) {
                    if (args.length == 0) {
                        throw new WrongInputException();
                    }
                    Command.Save save = new Command.Save();
                    save.execute(args);
                }
                if (Objects.equals(name, "help")) {
                    System.out.println("Доступные комманды:\nhelp - показать доступные комманды\nsave {относительный пкть} - сохранить коллекцию\nexit - завершить программу");
                } else if (!Objects.equals(name, "exit") && !Objects.equals(name, "help") && !Objects.equals(name, "save")) {
                    throw new WrongInputException();
                }
                LOGGER.info("===Server command executed {" + name + "}===");
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }
}
