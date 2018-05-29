package com.lysenkova.server;

import com.lysenkova.util.RequestHandler;
import com.lysenkova.util.ResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private int port;
    private ResourceReader resourceReader;

    public static void main(String[] args) {
        Server server = new Server();
        server.setPort(3000);
        server.setWebappPath("src/main/resources/page");
        server.start();
    }

    public void start()  {
        try(ServerSocket serverSocket = new ServerSocket(getPort())) {
            while (true) {
                try(Socket socket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream())) {
                    RequestHandler requestHandler = new RequestHandler();
                    requestHandler.setBufferedReader(bufferedReader);
                    requestHandler.setBufferedOutputStream(outputStream);
                    requestHandler.setResourceReader(resourceReader);
                    requestHandler.handle();
                }catch (IOException e) {
                    LOGGER.error("Error during opening/closing stream.", e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error during opening a socket", e);
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
