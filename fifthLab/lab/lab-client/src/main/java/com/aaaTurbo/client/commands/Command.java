package com.aaaTurbo.client.commands;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.aaaTurbo.client.classes.Route;
import com.aaaTurbo.client.classes.RouteCollection;
import com.aaaTurbo.client.classes.RouteGenerator;
import com.aaaTurbo.client.commandLine.ScriptListener;
import com.aaaTurbo.client.myExceptions.NoArgsNeededException;
import com.aaaTurbo.client.myExceptions.NoRouteInCollectionException;
import com.aaaTurbo.client.myExceptions.WrongInputException;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;
import java.util.Objects;
import java.util.Vector;

/*
Класс, описывающий и реализующий все команды в программе
 */
public abstract class Command {
    private static RouteCollection routeCollection;

    public Command() {
    }

    /*
    Геттер описания команды
    */
    public abstract String getDescription();

    /*
    Метод, реализующий выполнение команды
    @param String[] args
     */
    public abstract void execute(String[] args) throws Exception;

    /*
    Геттер названия команды
     */
    public abstract String getName();

    /*
    Служебная комманда реализующая первичную настройку всех команд для работы с коллекцией
     */
    public static class UtilCommandForSetUp extends Command {
        private String name;

        public UtilCommandForSetUp(RouteCollection routeCollection) {
            super();
            Command.routeCollection = routeCollection;
            name = "setuputility";
        }

        /*
        Метод настраивает команды под работу с коллекцией
        @return Vector<Command>
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
            commandList.add(save);
            return commandList;
        }

        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return null;
        }

        /*
        За выполнение этой комманды отвечает
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

    /*
    Комманда выводит список всех остальных команд с описанием
     */
    public static class Help extends Command {
        private String name = "help";
        private String description = "вывести справку по доступным командам";

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public void execute(String[] args) throws NoArgsNeededException {
            if (args.length > 0) {
                throw new NoArgsNeededException();
            }
            Vector<Command> commandList = Command.UtilCommandForSetUp.formCommandList();
            System.out.println("Список команд:\nexit : завершить программу (без сохранения в файл)\n" + "history : вывести последние 12 команд (без их аргументов)");
            for (Command c : commandList) {
                System.out.println(c.getName() + " : " + c.getDescription());
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
            Help help = (Help) o;
            return name.equals(help.name) && description.equals(help.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /*
    Комманда выводит информацию о коллекции
     */
    public static class Info extends Command {
        private String name = "info";
        private String description = "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public void execute(String[] args) throws NoArgsNeededException {
            if (args.length > 0) {
                throw new NoArgsNeededException();
            }
            System.out.println(routeCollection.toString());
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

    /*
    Комманда выводит все элементы коллекции
     */
    public static class Show extends Command {
        private String name = "show";
        private String description = "в стандартный поток вывода элементы коллекции в строковом представлении";

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public void execute(String[] args) throws NoRouteInCollectionException {
            if (routeCollection.getRoadCollection().size() == 0) {
                throw new NoRouteInCollectionException();
            }
            System.out.println(routeCollection.getRoadCollection());
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

    /*
    Комманда добавляет элемень в коллекцию
     */
    public static class Add extends Command {
        private String name = "add";
        private String description = "{name, x(Coordinates), y(Coordinates), x(LocationFrom), y(LocationFrom), name(LocationFrom), x(LocationTo), y(LocationTo), name(LocationTo), distance} : добавить новый элемент в коллекцию";

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public void execute(String[] args) throws Exception {
            final int lengthOfArgs = 10;
            try {
                if (args.length != lengthOfArgs) {
                    throw new WrongInputException();
                }
                RouteGenerator r = new RouteGenerator();
                routeCollection.getRoadCollection().add(r.generateRoute(args));
                System.out.println("Объект добавлен!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
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

    /*
    Комманда обновляет объект в коллекции по его id
     */
    public static class Update extends Command {
        private String name = "update";
        private String description = "id {name, x(Coordinates), y(Coordinates), x(LocationFrom), y(LocationFrom), name(LocationFrom), x(LocationTo), y(LocationTo), name(LocationTo), distance} : обновить значение элемента коллекции, id которого равен заданному";

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public void execute(String[] args) throws Exception {
            try {
                int id = Integer.parseInt(args[0]);
                RouteGenerator r = new RouteGenerator();
                Route ch = r.generateRouteWithId(args);
                routeCollection.getRoadCollection().remove(routeCollection.idSearch(id));
                routeCollection.getRoadCollection().add(ch);
                System.out.println("Объект обновлен!");
            } catch (Exception e) {
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
            Update update = (Update) o;
            return name.equals(update.name) && description.equals(update.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /*
    Комманда удаляет объект из коллекции по его id
     */
    public static class RemoveById extends Command {
        private String name = "remove_by_id";
        private String description = "удалить элемент из коллекции по его id";

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public void execute(String[] args) throws Exception {
            try {
                int id = Integer.parseInt(args[0]);
                routeCollection.getRoadCollection().remove(routeCollection.idSearch(id));
                System.out.println("Объект удален!");
            } catch (Exception e) {
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
            RemoveById that = (RemoveById) o;
            return name.equals(that.name) && description.equals(that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /*
    Комманда полностью очищает коллекцию
     */
    public static class Clear extends Command {
        private String name = "clear";
        private String description = "очистить коллекцию";

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public void execute(String[] args) throws Exception {
            routeCollection.getRoadCollection().clear();
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

    /*
    Служебная команда для восстановления коллекции из сохраненного файла, если такой имеется
     */
    public static class LoadFromFile extends Command {
        private RouteCollection routeCollection;
        private String name = "загркзка из файла";

        @Override

        public String getDescription() {
            return null;
        }

        /*
        метод устанавливает коллекцию для корректной работы команды
        @param RouteCollection
        */
        public void setRouteCollection(RouteCollection routeCollection) {
            this.routeCollection = routeCollection;
        }

        /*
        Метод реализует добавление восстановленных элементов в коллекцию
        @param File
        */
        private void add(File foundFileToLoad) throws Exception {
            BufferedInputStream read = new BufferedInputStream(new FileInputStream(foundFileToLoad));
            BufferedReader reader = new BufferedReader(new InputStreamReader(read));
            CSVReader csvReader = new CSVReader(reader, ',', '"', 0);
            String[] nextLine;
            RouteGenerator r = new RouteGenerator();
            while ((nextLine = csvReader.readNext()) != null) {
                try {
                    Route toAdd = r.generateRouteWithIdAndTime(nextLine);
                    routeCollection.getRoadCollection().add(toAdd);
                } catch (Exception e) {
                    System.out.println("===Файл поврежден и записан не полностью или вовсе не записан!===");
                }
            }
            System.out.println("---Коллекция восстановлена---");
        }

        @Override
        public void execute(String[] args) throws Exception {
            Vector<File> foundFiles = new Vector<>();
            File foundFileToLoad;
            File dir = new File("/Users/antonsemenov/IdeaProjects/lab/lab-client/src/main/java/com/aaaTurbo/client/files");
            for (File f : dir.listFiles()) {
                if (Objects.equals("colleсtion.csv", f.getName())) {
                    foundFiles.add(f);
                }
            }
            if (foundFiles.size() > 1) {
                System.out.println("Введите номер коллекции, которую хотите восстановить(нумерация от 1): ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                int read = Integer.parseInt(reader.readLine());
                foundFileToLoad = foundFiles.get(read - 1);
                add(foundFileToLoad);
            }
            if (foundFiles.size() == 1) {
                foundFileToLoad = foundFiles.get(0);
                add(foundFileToLoad);
            }
        }

        @Override
        public String getName() {
            return null;
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
            return routeCollection.equals(that.routeCollection) && name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /*
    Комманда сохраняет коллекцию в файл
     */
    public static class Save extends Command {
        private String name = "save";
        private String description = "сохранить коллекцию в файл";

        public String getDescription() {
            return description;
        }

        @Override
        public void execute(String[] args) throws Exception {
            if (args.length != 0) {
                throw new WrongInputException();
            }
            Route toSave = null;
            String csv = "colleсtion.csv";
            File f = new File("/Users/antonsemenov/IdeaProjects/lab/lab-client/src/main/java/com/aaaTurbo/client/files/" + csv);
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
            System.out.println("---Ваша коллекция успешно сохранена в файл!---");
        }

        @Override
        public String getName() {
            return name;
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

    /*
    Комманда выполняет скрипт из указанного файла
     */
    public static class ExecuteScript extends Command {
        private String name = "execute_script";
        private String description = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
        private Vector<File> foundFiles = new Vector<>();
        private File foundFile;

        public String getDescription() {
            return description;
        }

        @Override
        public void execute(String[] args) throws Exception {
            if (args.length != 1) {
                throw new WrongInputException();
            }
            File dir = new File("/Users/antonsemenov/IdeaProjects/lab/lab-client/src/main/java/com/aaaTurbo/client/files");
            for (File f : dir.listFiles()) {
                if (Objects.equals(args[0], f.getName())) {
                    foundFiles.add(f);
                }
            }
            if (foundFiles.size() == 0) {
                throw new NoSuchFileException("Такого файла не существует!");
            }
            if (foundFiles.size() > 1) {
                for (File f : foundFiles) {
                    System.out.println(f.getName());
                }
                System.out.println("Введите номер скрипта, который хотите выполнить(нумерация от 1): ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                int read = Integer.valueOf(reader.readLine());
                foundFile = foundFiles.get(read - 1);
            } else {
                foundFile = foundFiles.get(0);
            }
            ScriptListener scriptListener = new ScriptListener(routeCollection);
            scriptListener.setFile(foundFile);
            scriptListener.listenScript();
            foundFiles.remove(foundFiles.lastElement());
        }

        @Override
        public String getName() {
            return name;
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

    /*
    Комманда добавляет элемент в коллекцию, если он минимальный по значению
     */
    public static class AddIfMin extends Command {
        private String name = "add_if_min";
        private String description = "{name, x(Coordinates), y(Coordinates), x(LocationFrom), y(LocationFrom), name(LocationFrom), x(LocationTo), y(LocationTo), name(LocationTo), distance} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";

        public String getDescription() {
            return description;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void execute(String[] args) throws Exception {
            final int lengthOfArgs = 10;
            try {
                if (args.length != lengthOfArgs) {
                    throw new WrongInputException();
                }
                RouteGenerator r = new RouteGenerator();
                if (routeCollection.findMin().compareTo(r.generateRoute(args)) > 0) {
                    routeCollection.getRoadCollection().add(r.generateRoute(args));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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

    /*
    Комманда удаляет все большие по значению элементы коллекции
     */
    public static class RemoveGreater extends Command {
        private String name = "remove_greater";
        private String description = "{name, x(Coordinates), y(Coordinates), x(LocationFrom), y(LocationFrom), name(LocationFrom), x(LocationTo), y(LocationTo), name(LocationTo), distance} : удалить из коллекции все элементы, превышающие заданный";

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
            final int lengthOfArgs = 10;
            try {
                if (args.length != lengthOfArgs) {
                    throw new WrongInputException();
                }
                RouteGenerator r = new RouteGenerator();
                Route generatedRoute = r.generateRoute(args);
                for (Route rt : routeCollection.getRoadCollection()) {
                    if (rt.compareTo(generatedRoute) > 0) {
                        routeCollection.getRoadCollection().remove(rt);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        @Override
        public String getName() {
            return name;
        }
    }

    /*
    Комманда фильтр, выводит элемент с максимальным значением по имени
     */
    public static class MaxByName extends Command {
        private String name = "max_by_name";
        private String description = "вывести любой объект из коллекции, значение поля name которого является максимальным";

        public String getDescription() {
            return description;
        }

        @Override
        public void execute(String[] args) throws Exception {
            System.out.println(routeCollection.nameValueFilter());
        }

        @Override
        public String getName() {
            return name;
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

    /*
     Комманда фильтр, выводит все элементы, дистанция которых равна введенной
     */
    public static class FilterByDistance extends Command {
        private String name = "filter_by_distance";
        private String description = "вывести элементы, значение поля distance которых равно заданному";

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public void execute(String[] args) throws Exception {
            if (args.length != 1) {
                throw new WrongInputException();
            }
            if (routeCollection.distanceFilter(Long.parseLong(args[0])).size() == 0) {
                throw new NoRouteInCollectionException();
            }
            System.out.println(routeCollection.distanceFilter(Long.parseLong(args[0])));
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

    /*
    Комманда выводит все элементы, дистанция которых меньше введенной
     */
    public static class FilterLessThenDistance extends Command {
        private String name = "filter_less_then_distance";
        private String description = "вывести элементы, значение поля distance которых меньше заданного";

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        @Override
        public void execute(String[] args) throws Exception {
            if (args.length != 1) {
                throw new WrongInputException();
            }
            if (routeCollection.distanceFilter(Long.parseLong(args[0])).size() == 0) {
                throw new NoRouteInCollectionException();
            }
            System.out.println(routeCollection.distanceLessFilter(Long.parseLong(args[0])));
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
