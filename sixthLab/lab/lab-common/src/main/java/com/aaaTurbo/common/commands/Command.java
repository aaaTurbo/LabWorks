package com.aaaTurbo.common.commands;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.aaaTurbo.common.util.commandLine.ScriptListener;
import com.aaaTurbo.common.util.classes.Route;
import com.aaaTurbo.common.util.classes.RouteCollection;
import com.aaaTurbo.common.util.classes.RouteGenerator;
import com.aaaTurbo.common.util.myExceptions.NoArgsNeededException;
import com.aaaTurbo.common.util.myExceptions.NoRouteInCollectionException;
import com.aaaTurbo.common.util.myExceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.file.NoSuchFileException;
import java.util.Objects;
import java.util.Vector;

/**
 * Класс, описывающий и реализующий все команды в программе
 */
public abstract class Command {
    private static RouteCollection routeCollection;
    private static DatagramChannel serverChannel;
    private static DatagramSocket client;
    private int lengthOfArgs;

    public Command() {
    }

    /**
     * Геттер описания команды
     */
    public abstract String getDescription();

    /**
     * Метод, реализующий выполнение команды
     * @param args
     */
    public abstract void execute(String[] args) throws Exception;

    /**
     * Геттер названия команды
     */
    public abstract String getName();

    public int getLengthOfArgs() {
        return lengthOfArgs;
    }

    public abstract boolean validate(String[] args) throws WrongInputException, NoArgsNeededException;

    private static void sendResponse(String answer) throws IOException {
        final int port = 7777;
        SavedAnswer savedAnswer = new SavedAnswer(answer);
        byte[] serializedAnswer = savedAnswer.toBA(savedAnswer);
        DatagramPacket answerPack = new DatagramPacket(serializedAnswer, serializedAnswer.length, InetAddress.getLocalHost(), port);
        ByteBuffer sendBuf = ByteBuffer.wrap(answerPack.getData());
        serverChannel.send(sendBuf, answerPack.getSocketAddress());
    }

    /**
     * Служебная комманда реализующая первичную настройку всех команд для работы с коллекцией
     */
    public static class UtilCommandForSetUp extends Command {
        private String name;

        public UtilCommandForSetUp() {
            super();
            name = "set_up_utility";
        }

        public static void setServerForResponds(DatagramChannel serv) {
            serverChannel = serv;
        }

        public static void setClientForRequests(DatagramSocket cl) {
            client = cl;
        }

        public void setCollection(RouteCollection roadCollection) {
            routeCollection = roadCollection;
        }

        /**
         * Метод настраивает команды под работу с коллекцией
         * @return Vector<Command>
         */
        public static Vector<Command> formCommandList() {
            Help help = new Help();
            Info info = new Info();
            Show show = new Show();
            Add add = new Add();
            Update update = new Update();
            RemoveById removeById = new RemoveById();
            Clear clear = new Clear();
            MaxByName maxByName = new MaxByName();
            FilterByDistance filterByDistance = new FilterByDistance();
            FilterLessThenDistance filterLessThenDistance = new FilterLessThenDistance();
            RemoveGreater removeGreater = new RemoveGreater();
            AddIfMin addIfMin = new AddIfMin();
            ExecuteScript executeScript = new ExecuteScript();
            Save save = new Save();
            LoadFromFile loadFromFile = new LoadFromFile();
            Vector<Command> commandList = new Vector<>();
            commandList.add(help);
            commandList.add(info);
            commandList.add(show);
            commandList.add(add);
            commandList.add(update);
            commandList.add(removeById);
            commandList.add(clear);
            commandList.add(maxByName);
            commandList.add(filterByDistance);
            commandList.add(filterLessThenDistance);
            commandList.add(addIfMin);
            commandList.add(removeGreater);
            commandList.add(executeScript);
            commandList.add(loadFromFile);
            return commandList;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            return false;
        }

        @Override
        public String getDescription() {
            return null;
        }

        /**
         * За выполнение этой комманды отвечает
         */
        @Override
        public void execute(String[] args) throws Exception {
            System.out.println("За выполнение отвечают другие методы... :/");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UtilCommandForSetUp that = (UtilCommandForSetUp) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда выводит список всех остальных команд с описанием
     */
    public static class Help extends Command {
        private String name = "help";
        private String description = "вывести справку по доступным командам";
        private final int lengthOfArgs = 0;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public void execute(String[] args) throws IOException {
            Vector<Command> commandList = Command.UtilCommandForSetUp.formCommandList();
            String commandStringList = "\n";
            for (Command c : commandList) {
               commandStringList += (c.getName() + " : " + c.getDescription() + "\n");
            }
            Command.sendResponse("Список команд:\nexit : завершить программу (без сохранения в файл)\n" + "history : вывести последние 12 команд (без их аргументов)" + commandStringList);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Help help = (Help) o;
            return name.equals(help.name) && description.equals(help.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда выводит информацию о коллекции
     */
    public static class Info extends Command {
        private String name = "info";
        private String description = "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
        private final int lengthOfArgs = 0;

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        public String getDescription() {
            return description;
        }

        @Override
        public void execute(String[] args) throws IOException {
            Command.sendResponse(routeCollection.toString());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Info info = (Info) o;
            return name.equals(info.name) && description.equals(info.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда выводит все элементы коллекции
     */
    public static class Show extends Command {
        private String name = "show";
        private String description = "в стандартный поток вывода элементы коллекции в строковом представлении";
        private final int lengthOfArgs = 0;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public void execute(String[] args) throws NoRouteInCollectionException, IOException {
            try {
                if (routeCollection.getRoadCollection().size() > 0) {
                    Command.sendResponse(routeCollection.getRoadCollection().toString());
                } else {
                    throw new NoRouteInCollectionException();
                }
            } catch (Exception e) {
                Command.sendResponse(e.getMessage());
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Show show = (Show) o;
            return name.equals(show.name) && description.equals(show.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда добавляет элемень в коллекцию
     */
    public static class Add extends Command {
        private String name = "add";
        private String description = "{name, x(Coordinates), y(Coordinates), x(LocationFrom), y(LocationFrom), name(LocationFrom), x(LocationTo), y(LocationTo), name(LocationTo), distance} : добавить новый элемент в коллекцию";
        private final int lengthOfArgs = 10;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public void execute(String[] args) throws Exception {
            try {
                RouteGenerator r = new RouteGenerator();
                routeCollection.getRoadCollection().add(r.generateRoute(args));
                Command.sendResponse("---Объект добавлен!---");
            } catch (Exception e) {
                Command.sendResponse(e.getMessage());
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Add command = (Add) o;
            return name.equals(command.name) && description.equals(command.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда обновляет объект в коллекции по его id
     */
    public static class Update extends Command {
        private String name = "update";
        private String description = "id {name, x(Coordinates), y(Coordinates), x(LocationFrom), y(LocationFrom), name(LocationFrom), x(LocationTo), y(LocationTo), name(LocationTo), distance} : обновить значение элемента коллекции, id которого равен заданному";
        private final int lengthOfArgs = 10;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public void execute(String[] args) throws Exception {
                int id = Integer.parseInt(args[0]);
                RouteGenerator r = new RouteGenerator();
                Route ch = r.generateRouteWithId(args);
                routeCollection.getRoadCollection().remove(routeCollection.idSearch(id));
                routeCollection.getRoadCollection().add(ch);
                Command.sendResponse("---Объект обновлен---");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Update update = (Update) o;
            return name.equals(update.name) && description.equals(update.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда удаляет объект из коллекции по его id
     */
    public static class RemoveById extends Command {
        private String name = "remove_by_id";
        private String description = "удалить элемент из коллекции по его id";
        private final int lengthOfArgs = 1;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public void execute(String[] args) throws Exception {
                int id = Integer.parseInt(args[0]);
                System.out.println(routeCollection.idSearch(id).toString());
                routeCollection.getRoadCollection().remove(routeCollection.idSearch(id));
                Command.sendResponse("---Объект удален---");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RemoveById that = (RemoveById) o;
            return name.equals(that.name) && description.equals(that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда полностью очищает коллекцию
     */
    public static class Clear extends Command {
        private String name = "clear";
        private String description = "очистить коллекцию";
        private final int lengthOfArgs = 0;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public void execute(String[] args) throws Exception {
            routeCollection.getRoadCollection().clear();
            Command.sendResponse("---Коллекция очищина---");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Clear clear = (Clear) o;
            return name.equals(clear.name) && description.equals(clear.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Служебная команда для восстановления коллекции из сохраненного файла, если такой имеется
     */
    public static class LoadFromFile extends Command {
        private String name = "load";
        private String description = "{file name} : загрузить коллекцию из файла";
        private RouteGenerator r = new RouteGenerator();
        private final int lengthOfArgs = 1;

        public LoadFromFile() {
        }

        public LoadFromFile(RouteCollection r) {
            routeCollection = r;
        }

        @Override
        public String getDescription() {
            return description;
        }

        /**
         * Метод реализует добавление восстановленных элементов в коллекцию
         * @param foundFileToLoad
         */
        private void add(File foundFileToLoad) throws Exception {
            BufferedInputStream read = new BufferedInputStream(new FileInputStream(foundFileToLoad));
            BufferedReader reader = new BufferedReader(new InputStreamReader(read));
            CSVReader csvReader = new CSVReader(reader, ',', '"', 0);
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                try {
                    Route toAdd = r.generateRouteWithIdAndTime(nextLine);
                    routeCollection.getRoadCollection().add(toAdd);
                } catch (Exception e) {
                    Command.sendResponse("===Файл поврежден и записан не полностью или вовсе не записан!===\n" + e.getMessage());
                }
            }
            Command.sendResponse("---Коллекция восстановлена---");
        }

        @Override
        public void execute(String[] args) throws Exception {
            Vector<File> foundFiles = new Vector<>();
            File foundFileToLoad;
            File dir = new File(new File(System.getProperty("user.dir") + args[0]).getParent());
            try {
                String fileName = (args[0].split("/"))[(args[0].split("/")).length - 1];
                for (File f : dir.listFiles()) {
                    if (Objects.equals(fileName, f.getName())) {
                        foundFiles.add(f);
                        break;
                    }
                }
                if (foundFiles.size() == 1) {
                    foundFileToLoad = foundFiles.get(0);
                    add(foundFileToLoad);
                } else {
                    Command.sendResponse(new Exception("---Файла не существует---").getMessage());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            foundFiles.clear();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            LoadFromFile that = (LoadFromFile) o;
            return routeCollection.equals(name.equals(that.name));
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда сохраняет коллекцию в файл
     */
    public static class Save extends Command {
        private String name = "save";
        private String description = "сохранить коллекцию в файл";
        private final int lengthOfArgs = 1;

        public String getDescription() {
            return description;
        }

        @Override
        public void execute(String[] args) throws Exception {
            Route toSave = null;
            String csv = args[0];
            File f = new File(System.getProperty("user.dir") + csv);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(f));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            CSVWriter csvWriter = new CSVWriter(writer);
            for (Route r : routeCollection.getRoadCollection()) {
                if (r != null) {
                    toSave = r;
                    csvWriter.writeNext(toSave.generateToSaveInCSV());
                }
            }
            csvWriter.close();
            File saveToBufferDellOld = new File(System.getProperty("user.dir") + "/lab-client/src/main/java/com/aaaTurbo/client/files/lastSavedCollectionBuffer");
            saveToBufferDellOld.delete();
            File saveToBuffer = new File(System.getProperty("user.dir") + "/lab-client/src/main/java/com/aaaTurbo/client/files/lastSavedCollectionBuffer");
            FileOutputStream bufferOutput = new FileOutputStream(saveToBuffer);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(bufferOutput));
            bufferedWriter.write(csv);
            bufferedWriter.close();
            Command.sendResponse("---Ваша коллекция успешно сохранена в файл!---");
        }

        public void toExecuteInTheEnd(String[] args) throws IOException {
            Route toSave = null;
            String csv = args[0];
            File f = new File(System.getProperty("user.dir") + csv);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(f));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            CSVWriter csvWriter = new CSVWriter(writer);
            for (Route r : routeCollection.getRoadCollection()) {
                if (r != null) {
                    toSave = r;
                    csvWriter.writeNext(toSave.generateToSaveInCSV());
                }
            }
            csvWriter.close();
            File saveToBufferDellOld = new File(System.getProperty("user.dir") + "/lab-common/src/main/java/com/aaaTurbo/common/commands/lastSavedCollectionBuffer");
            saveToBufferDellOld.delete();
            File saveToBuffer = new File(System.getProperty("user.dir") + "/lab-common/src/main/java/com/aaaTurbo/common/commands/lastSavedCollectionBuffer");
            FileOutputStream bufferOutput = new FileOutputStream(saveToBuffer);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(bufferOutput));
            bufferedWriter.write(csv);
            bufferedWriter.close();
            System.out.println("--Коллекция мохранена! Сервер закончил работу---");
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Save save = (Save) o;
            return name.equals(save.name) && description.equals(save.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда выполняет скрипт из указанного файла
     */
    public static class ExecuteScript extends Command {
        private String name = "execute_script";
        private String description = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
        private Vector<File> foundFiles = new Vector<>();
        private File foundFile;
        private final int lengthOfArgs = 1;

        public String getDescription() {
            return description;
        }

        @Override
        public void execute(String[] args) throws Exception {
            File dir = new File(new File(System.getProperty("user.dir") + args[0]).getParent());
            String fileName = (args[0].split("/"))[args[0].split("/").length - 1];
            for (File f : Objects.requireNonNull(dir.listFiles())) {
                if (Objects.equals(fileName, f.getName())) {
                    foundFiles.add(f);
                    break;
                }
            }
            if (foundFiles.size() == 0) {
                throw new NoSuchFileException("---Такого файла не существует!---");
            } else {
                foundFile = foundFiles.get(0);
            }
            ScriptListener scriptListener = new ScriptListener(client);
            scriptListener.setFile(foundFile);
            scriptListener.listenScript();
            foundFiles.clear();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ExecuteScript that = (ExecuteScript) o;
            return name.equals(that.name) && description.equals(that.description) && foundFiles.equals(that.foundFiles) && foundFile.equals(that.foundFile);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда добавляет элемент в коллекцию, если он минимальный по значению
     */
    public static class AddIfMin extends Command {
        private String name = "add_if_min";
        private String description = "{name, x(Coordinates), y(Coordinates), x(LocationFrom), y(LocationFrom), name(LocationFrom), x(LocationTo), y(LocationTo), name(LocationTo), distance} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
        private final int lengthOfArgs = 10;

        public String getDescription() {
            return description;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public void execute(String[] args) throws Exception {
            try {
                RouteGenerator r = new RouteGenerator();
                if (routeCollection.findMin().compareTo(r.generateRoute(args)) > 0) {
                    routeCollection.getRoadCollection().add(r.generateRoute(args));
                    Command.sendResponse("---Элемент добавлен!---");
                }
            } catch (Exception e) {
                Command.sendResponse(e.getMessage());
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AddIfMin addIfMin = (AddIfMin) o;
            return name.equals(addIfMin.name) && description.equals(addIfMin.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда удаляет все большие по значению элементы коллекции
     */
    public static class RemoveGreater extends Command {
        private String name = "remove_greater";
        private String description = "{name, x(Coordinates), y(Coordinates), x(LocationFrom), y(LocationFrom), name(LocationFrom), x(LocationTo), y(LocationTo), name(LocationTo), distance} : удалить из коллекции все элементы, превышающие заданный";
        private final int lengthOfArgs = 10;

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RemoveGreater that = (RemoveGreater) o;
            return name.equals(that.name) && description.equals(that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public void execute(String[] args) throws Exception {
            try {
                RouteGenerator r = new RouteGenerator();
                Route generatedRoute = r.generateRoute(args);
                for (Route rt : routeCollection.getRoadCollection()) {
                    if (rt.compareTo(generatedRoute) > 0) {
                        routeCollection.getRoadCollection().remove(rt);
                        Command.sendResponse("---Объект удален---");
                    }
                }
            } catch (Exception e) {
                Command.sendResponse(e.getMessage());
            }

        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }
    }

    /**
     * Комманда фильтр, выводит элемент с максимальным значением по имени
     */
    public static class MaxByName extends Command {
        private String name = "max_by_name";
        private String description = "вывести любой объект из коллекции, значение поля name которого является максимальным";
        private final int lengthOfArgs = 0;

        public String getDescription() {
            return description;
        }

        @Override
        public void execute(String[] args) throws Exception {
            Command.sendResponse(routeCollection.nameValueFilter(serverChannel).toString());
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MaxByName maxByName = (MaxByName) o;
            return name.equals(maxByName.name) && description.equals(maxByName.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда фильтр, выводит все элементы, дистанция которых равна введенной
     */
    public static class FilterByDistance extends Command {
        private String name = "filter_by_distance";
        private String description = "вывести элементы, значение поля distance которых равно заданному";
        private final int lengthOfArgs = 1;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public void execute(String[] args) throws Exception {
            if (routeCollection.distanceFilter(Long.parseLong(args[0])).length == 0) {
                Command.sendResponse(new NoRouteInCollectionException().getMessage());
            }
            Command.sendResponse(routeCollection.distanceFilter(Long.parseLong(args[0]))[0].toString());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FilterByDistance that = (FilterByDistance) o;
            return name.equals(that.name) && description.equals(that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Комманда выводит все элементы, дистанция которых меньше введенной
     */
    public static class FilterLessThenDistance extends Command {
        private String name = "filter_less_then_distance";
        private String description = "вывести элементы, значение поля distance которых меньше заданного";
        private final int lengthOfArgs = 1;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean validate(String[] args) throws WrongInputException, NoArgsNeededException {
            if (args.length == lengthOfArgs) {
                return true;
            }
            if (lengthOfArgs == 0) {
                throw new NoArgsNeededException();
            } else {
                throw new WrongInputException();
            }
        }

        @Override
        public void execute(String[] args) throws Exception {
            if (routeCollection.distanceFilter(Long.parseLong(args[0])).length == 0) {
                Command.sendResponse(new NoRouteInCollectionException().getMessage());
            }
            Command.sendResponse(routeCollection.distanceLessFilter(Long.parseLong(args[0]))[0].toString());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FilterLessThenDistance that = (FilterLessThenDistance) o;
            return name.equals(that.name) && description.equals(that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
