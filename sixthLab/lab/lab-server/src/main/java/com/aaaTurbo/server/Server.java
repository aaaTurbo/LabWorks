package com.aaaTurbo.server;

import com.aaaTurbo.common.commands.Command;
import com.aaaTurbo.common.util.classes.RouteCollection;
import com.aaaTurbo.server.commandLine.CommandListener;
import com.aaaTurbo.server.commandLine.RequestListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;


public final class Server {
    private static final int SERVERPORT = 6666;

    private static boolean inWork = true;


    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    private static void serverSetUP(DatagramChannel serverChannel) throws Exception {
        Command.UtilCommandForSetUp utilCommand = new Command.UtilCommandForSetUp();
        RouteCollection mainCollection = new RouteCollection();
        utilCommand.setCollection(mainCollection);
        Command.UtilCommandForSetUp.setServerForResponds(serverChannel);
        RequestListener requestListener = new RequestListener(serverChannel, inWork, mainCollection, utilCommand);
        CommandListener commandListener = new CommandListener(inWork, requestListener);
        requestListener.getrL().start();
        commandListener.getcL().start();

    }

    public static void main(String[] args) throws Exception {
        DatagramChannel serverChannel = DatagramChannel.open();
        serverChannel.configureBlocking(false);
        SocketAddress adress = new InetSocketAddress(InetAddress.getLocalHost(), SERVERPORT);
        serverChannel.bind(adress);
        serverSetUP(serverChannel);
    }
}
