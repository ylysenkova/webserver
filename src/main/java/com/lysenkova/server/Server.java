package com.lysenkova.server;

import com.lysenkova.util.MultiRequestHandler;
import com.lysenkova.util.RequestHandler;
import com.lysenkova.util.ResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private int port;
    private ResourceReader resourceReader;

    public static void main(String[] args) {
        String path = "src/main/resources/page";
        if (args.length != 0) {
            path = args[0];
        }
        Server server = new Server();
        server.setPort(3000);
        server.setWebappPath(path);
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    executorService.execute(new MultiRequestHandler(socket, resourceReader));

                } catch (IOException e) {
                    LOGGER.error("Error during opening/closing stream.", e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error during opening a server socket", e);
        }

    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setWebappPath(String path) {
        resourceReader = new ResourceReader();
        resourceReader.setWebappPath(path);
    }
}
