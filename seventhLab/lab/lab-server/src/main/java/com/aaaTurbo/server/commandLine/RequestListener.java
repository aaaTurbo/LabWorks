package com.aaaTurbo.server.commandLine;

import com.aaaTurbo.common.commands.Command;
import com.aaaTurbo.common.commands.SavedCommand;
import com.aaaTurbo.common.dbManeger.DBManeger;
import com.aaaTurbo.common.util.classes.RouteCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;


public class RequestListener implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestListener.class);
    private static final int THREADNUM = 5;
    private static DatagramChannel serverChannel;
    private static boolean inWork;
    private RouteCollection collection;
    private Command.UtilCommandForSetUp utilCommand;
    private Thread rL = new Thread(this);
    private boolean noCommand = true;
    private DBManeger dbManeger = new DBManeger();
    private ExecutorService requestPool = Executors.newFixedThreadPool(THREADNUM);
    private ExecutorService commandExecutePool = new ForkJoinPool();
    private ExecutorService responseSendingPool = new ForkJoinPool();
    private Vector<Command> commands = utilCommand.formCommandList();

    public RequestListener(DatagramChannel server, boolean work, RouteCollection routeCollection, Command.UtilCommandForSetUp utilCommandForSetUp) throws SocketException, SQLException {
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
            requestPool.shutdown();
            commandExecutePool.shutdown();
            responseSendingPool.shutdown();
        } catch (Exception ignored) {
            //ignored
        }
    }

    public Thread getrL() {
        return rL;
    }

    private SavedCommand waitCommand() {
        SavedCommand savedCommand = null;
        while (noCommand) {
            try {
                ByteBuffer pack = ByteBuffer.allocate(serverChannel.socket().getReceiveBufferSize());
                serverChannel.receive(pack);
                ((Buffer) pack).flip();
                byte[] bytes = new byte[pack.remaining()];
                pack.get(bytes);
                savedCommand = SavedCommand.toSC(bytes);
                noCommand = false;
                return savedCommand;
            } catch (Exception ignored) {
                //ignored
            }
        }
        return savedCommand;
    }

    private void registerUser(SavedCommand savedCommand) {
        CompletableFuture
                .supplyAsync(savedCommand::getThisSC)
                .thenApplyAsync(savedCommand1 -> {
                    return executeCommand(savedCommand1);
                }, commandExecutePool)
                .thenAcceptAsync(command -> {
                    try {
                        command.sendResponse(command.getAns(), command.getClientPort());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info("===Command executed {" + command.getName() + "} and answer sent!===");
                }, responseSendingPool);
    }

    private Command executeCommand(SavedCommand savedCommand) {
        if (!Objects.equals(savedCommand.getName(), "execute_script")) {
            String[] args = savedCommand.getArgs();
            int clientPort = savedCommand.getClientPort();
            String user = savedCommand.getUser();
            for (Command c : commands) {
                if (Objects.equals(savedCommand.getName(), c.getName())) {
                    if (c.getClass() == Command.ExecuteScript.class) {
                        LOGGER.info("===Client connected and command accepted!===");
                        try {
                            c.execute(args, clientPort, user);
                        } catch (Exception e) {
                            //ignored
                        }
                        LOGGER.info("===Command executed {" + c.getName() + "}!===");
                        return c;
                    }
                    if (c.getClass() != Command.Save.class) {
                        LOGGER.info("===Client connected and command accepted!===");
                        try {
                            c.execute(args, clientPort, user);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LOGGER.info("===Command executed {" + c.getName() + "}!===");
                        return c;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void run() {
        LOGGER.info("===Server started!===");
        while (inWork) {
            noCommand = true;
            try {
                LOGGER.info("===Waiting for connection from client and command!===");
                Future<SavedCommand> request = requestPool.submit(this::waitCommand);
                SavedCommand savedCommand = request.get();
                if (savedCommand != null && !Objects.equals(savedCommand.getName(), "sign_up")
                        && dbManeger.validateUser(savedCommand.getUser(), savedCommand.getPassword())) {
                    CompletableFuture
                            .supplyAsync(savedCommand::getThisSC)
                            .thenApplyAsync(savedCommand1 -> {
                                return executeCommand(savedCommand1);
                            }, commandExecutePool)
                            .thenAcceptAsync(command -> {
                                try {
                                    command.sendResponse(command.getAns(), command.getClientPort());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                LOGGER.info("===Command executed {" + command.getName() + "} and answer sent!===");
                            }, responseSendingPool);
                } else if (Objects.equals(savedCommand.getName(), "sign_up")) {
                    registerUser(savedCommand);
                } else {
                    LOGGER.warn("===Wrong user/pass!===");
                    throw new Exception("===Wrong user/pass!===");
                }
            } catch (InterruptedException ie) {
                //igonred
            }    catch (Exception e) {
                LOGGER.warn(e.getMessage());
            }
        }
        LOGGER.info("===Server closed!===");
    }
}
