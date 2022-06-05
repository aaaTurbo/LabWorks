package com.aaaTurbo.server.commandLine;

import com.aaaTurbo.common.commands.Command;
import com.aaaTurbo.common.commands.SavedAnswer;
import com.aaaTurbo.common.commands.SavedCommand;
import com.aaaTurbo.common.util.classes.RouteCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.Vector;


public class RequestListener implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestListener.class);
    private static DatagramChannel serverChannel;
    private static boolean inWork;
    private final int clientPort = 7777;
    private SavedCommand savedCommand = null;
    private RouteCollection collection;
    private Command.UtilCommandForSetUp utilCommand;
    private Thread rL = new Thread(this);
    private boolean noCommand = true;

    public RequestListener(DatagramChannel server, boolean work, RouteCollection routeCollection, Command.UtilCommandForSetUp utilCommandForSetUp) throws SocketException {
        serverChannel = server;
        inWork = work;
        collection = routeCollection;
        utilCommand = utilCommandForSetUp;
    }

    public void stopWork() throws InterruptedException {
        inWork = false;
        noCommand = false;
        try {
            rL.interrupt();
        } catch (Exception ignored) {
            //ignored
        }
    }

    public Thread getrL() {
        return rL;
    }

    private void waitCommand() {
        savedCommand = null;
        while (noCommand) {
            try {
                ByteBuffer pack = ByteBuffer.allocate(serverChannel.socket().getReceiveBufferSize());
                serverChannel.receive(pack);
                ((Buffer) pack).flip();
                byte[] bytes = new byte[pack.remaining()];
                pack.get(bytes);
                savedCommand = SavedCommand.toSC(bytes);
                noCommand = false;
            } catch (Exception ignored) {
                //ignored
            }
        }
    }

    private void sendExceptionAnswer(Exception e) {
        LOGGER.warn(e.getMessage() + "\n===Answer is ready and sent!===");
        SavedAnswer savedAnswer = new SavedAnswer(e.getMessage());
        try {
            byte[] serializedAnsver = savedAnswer.toBA(savedAnswer);
            DatagramPacket sendExc = new DatagramPacket(serializedAnsver, serializedAnsver.length, InetAddress.getLocalHost(), clientPort);
            ByteBuffer sendBuf = ByteBuffer.wrap(sendExc.getData());
            serverChannel.send(sendBuf, sendExc.getSocketAddress());
        } catch (IOException ignored) {
            //ignored
        }
    }

    @Override
    public void run() {
        LOGGER.info("===Server started!===");
        Vector<Command> commands = utilCommand.formCommandList();
        while (inWork) {
            noCommand = true;
            try {
                LOGGER.info("===Waiting for connection from client and command!===");
                waitCommand();
                LOGGER.info("===Client connected and command accepted!===");
                if (savedCommand != null && !Objects.equals(savedCommand.getName(), "execute_script")) {
                    String[] args = savedCommand.getArgs();
                    for (Command c : commands) {
                        if (Objects.equals(savedCommand.getName(), c.getName())) {
                            c.execute(args);
                            LOGGER.info("===Command executed {" + c.getName() + "} and answer sent!===");
                        }
                    }
                }
            } catch (Exception e) {
                sendExceptionAnswer(e);
            }
        }
        Command.Save save = new Command.Save();
        String[] toSaveCommand = new String[1];
        toSaveCommand[0] = "/lab-client/src/main/java/com/aaaTurbo/client/files/test.csv";
        try {
            save.toExecuteInTheEnd(toSaveCommand);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        LOGGER.info("===Server closed!===");
    }
}
