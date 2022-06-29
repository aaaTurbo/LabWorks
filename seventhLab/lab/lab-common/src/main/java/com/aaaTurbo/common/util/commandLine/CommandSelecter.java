package com.aaaTurbo.common.util.commandLine;

import com.aaaTurbo.common.commands.Command;

import java.util.Objects;
import java.util.Vector;

/**
 * Класс, реализующий выборку команды
 */
public class CommandSelecter {
    private Command.UtilCommandForSetUp utilCommandForSetUp;
    private Vector<Command> commandList;

    public CommandSelecter(Command.UtilCommandForSetUp utilCommandForSetUp) {
        this.utilCommandForSetUp = utilCommandForSetUp;
        commandList = utilCommandForSetUp.formCommandList();
    }

    /**
     * Метод, выбирающий команду
     * @param name
     * @return Command
     */
    public Command selectCommand(String name) {
        for (Command c : commandList) {
            if (Objects.equals(c.getName(), name)) {
                return c;
            }
        }
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
        CommandSelecter that = (CommandSelecter) o;
        return utilCommandForSetUp.equals(that.utilCommandForSetUp) && commandList.equals(that.commandList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilCommandForSetUp, commandList);
    }
}
