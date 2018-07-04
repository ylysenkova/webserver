package com.lysenkova.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MultiRequestHandler implements Runnable {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final Socket socketIn;
    private final ResourceReader resourceReader;

    public MultiRequestHandler(Socket socket, ResourceReader resourceReader) {
        this.socketIn = socket;
        this.resourceReader = resourceReader;
    }

    @Override
    public void run() {
        try (Socket socket = socketIn;
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream())) {
            RequestHandler requestHandler = new RequestHandler();
            requestHandler.setBufferedReader(bufferedReader);
            requestHandler.setBufferedOutputStream(outputStream);
            requestHandler.setResourceReader(resourceReader);
            requestHandler.handle();
        } catch (IOException e) {
            LOGGER.error("Error during opening/closing stream.", e);
        }
    }
}
