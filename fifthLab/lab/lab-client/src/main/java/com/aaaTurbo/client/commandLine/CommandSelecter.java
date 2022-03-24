package com.aaaTurbo.client.commandLine;

import com.aaaTurbo.client.commands.Command;

import java.util.Objects;
import java.util.Vector;

public class CommandSelecter {
    private Command.UtilCommandForSetUp utilCommandForSetUp;
    private Vector<Command> commandList;

    public CommandSelecter(Command.UtilCommandForSetUp utilCommandForSetUp) {
        this.utilCommandForSetUp = utilCommandForSetUp;
        commandList = utilCommandForSetUp.formCommandList();
    }

    public Command selectCommand(String name) {
        for (Command c : commandList) {
            if (Objects.equals(c.getName(), name)) {
                return c;
            }
        }
        return null;
    }
}
