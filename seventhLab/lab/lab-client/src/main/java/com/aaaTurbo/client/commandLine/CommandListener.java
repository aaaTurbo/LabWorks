package com.aaaTurbo.client.commandLine;

import com.aaaTurbo.common.commands.Command;
import com.aaaTurbo.common.commands.SavedAnswer;
import com.aaaTurbo.common.commands.SavedCommand;
import com.aaaTurbo.common.util.commandLine.CommandSelecter;
import com.aaaTurbo.common.util.commandLine.ScriptListener;
import com.aaaTurbo.common.util.myExceptions.NoArgsNeededException;
import com.aaaTurbo.common.util.myExceptions.WrongInputException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Класс реализующий чтение команд в реалтном времени
 */
public class CommandListener {
    private final int length = 12;
    private final int serverPort = 6666;
    private final int timeOut = 10000;
    private String[] history = new String[length];
    private DatagramSocket client;
    private boolean inWork = true;
    private int historyCounter = 0;
    private final int historyLength = 12;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private CommandSelecter commandSelecter = new CommandSelecter(new Command.UtilCommandForSetUp());
    private String[] historyAttendant = new String[historyLength];
    private String user;
    private String password;

    public CommandListener(DatagramSocket socket, String user, String password) throws UnknownHostException {
        for (int i = 0; i < historyAttendant.length; i++) {
            historyAttendant[i] = "---";
        }
        client = socket;
        this.user = user;
        this.password = password;
    }

    private void stopListen() {
        inWork = false;
        System.out.println("--- Программа завершена ---");
    }

    /**
     * Реализация сохранения истории комманд
     *
     * @param str
     * @return String[]
     */
    private String[] historyAttendant(String str) {
        final int count = 12;
        final int countS = 11;
        if (historyCounter == count) {
            historyAttendant[0] = null;
            for (int i = 0; i < historyAttendant.length - 1; i++) {
                historyAttendant[i] = historyAttendant[i + 1];
            }
            historyAttendant[countS] = str;
        }
        if (historyCounter < count) {
            historyAttendant[historyCounter] = str;
            historyCounter++;
        }
        return historyAttendant;
    }

    /**
     * Отделение имени от всего ввода
     *
     * @param str
     * @return String
     */
    private String splitName(String str) {
        String[] s = str.split(" ");
        String splitedName = s[0];
        return splitedName;
    }

    /**
     * Отделение аргументов от всего ввода
     *
     * @param str
     * @return String[]
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
     * Реализация чтения комманд в реальном времени
     *
     * @throws Exception
     */
    public void startListen() throws Exception {
        while (inWork) {
            try {
                System.out.print("Введите команду: ");
                String read = reader.readLine();
                if (read == null) {
                    throw new NoSuchElementException("Некорректный ввод! Остановка программы...");
                }
                if (Objects.equals(splitName(read), "history")) {
                    System.out.println("(нижняя команда - последняя введенная)");
                    for (String s : history) {
                        System.out.println(s);
                    }
                }
                if (Objects.equals(splitName(read), "save")) {
                    System.out.println("Комманды не поддерживается клиентом");
                }
                if (Objects.equals(splitName(read), "execute_script")) {
                    Command.ExecuteScript executeScript = new Command.ExecuteScript();
                    executeScript.setClient(client);
                    validateAndExecForScript(executeScript, read);
                }
                if (Objects.equals(splitName(read), "exit")) {
                    stopListen();
                } else {
                    executeAvaluableCommands(read);
                }
            } catch (SocketTimeoutException te) {
                sTEWorkOut(te);
            } catch (NullPointerException nPE) {
                System.out.println("---Такого файла не существует!---");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        client.close();
    }

    private void validateAndExecForScript(Command.ExecuteScript executeScript, String read) throws Exception {
        if (executeScript.validate(splitArgs(read))) {
            ScriptListener.addFirstScript(splitArgs(read)[0]);
            executeScript.execute(splitArgs(read), client.getLocalPort(), user);
        }
    }

    private void executeAvaluableCommands(String read) throws WrongInputException, NoArgsNeededException, IOException, ClassNotFoundException {
        String name = splitName(read);
        String[] args = splitArgs(read);
        Command command = commandSelecter.selectCommand(name);
        if (command == null) {
            throw new WrongInputException();
        }
        if (!"history".equals(read) && !"\n".equals(read)) {
            history = historyAttendant(read);
        }
        if (command.validate(args) & !Objects.equals(command.getName(), "execute_script")) {
            sendAndGetAnswer(command, args);
        }
    }

    private void sTEWorkOut(SocketTimeoutException te) throws IOException {
        System.out.println(te.getMessage());
        System.out.println("Повторить подключение? Введите нет/Нет, чтобы выйти или нажмите Eneter для повторной попытки");
        String read = reader.readLine();
        if (Objects.equals(read, "Нет") || Objects.equals(read, "нет")) {
            inWork = false;
        }
    }

    private void sendAndGetAnswer(Command command, String[] args) throws IOException, ClassNotFoundException {
        System.out.println("---Ожидание ответа сервера---");
        SavedCommand savedCommand = new SavedCommand(client.getLocalPort(), command, args, user, password);
        byte[] serializedCommandBytes = savedCommand.toBA(savedCommand);
        DatagramPacket commandPac = new DatagramPacket(serializedCommandBytes, serializedCommandBytes.length, InetAddress.getLocalHost(), serverPort);
        client.send(commandPac);
        client.setSoTimeout(timeOut);
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
        CommandListener that = (CommandListener) o;
        return inWork == that.inWork && historyCounter == that.historyCounter && historyLength == that.historyLength && reader.equals(that.reader) && commandSelecter.equals(that.commandSelecter) && Arrays.equals(historyAttendant, that.historyAttendant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historyCounter, historyLength);
    }
}
