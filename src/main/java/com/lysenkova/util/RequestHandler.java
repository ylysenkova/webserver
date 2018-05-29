package com.lysenkova.util;

import com.lysenkova.entity.Request;
import com.lysenkova.exception.BadRequestRuntimeException;
import com.lysenkova.exception.FileNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class RequestHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private BufferedReader bufferedReader;
    private BufferedOutputStream bufferedOutputStream;
    private ResourceReader resourceReader;

    public void handle() {
        ResponseWriter responseWriter = new ResponseWriter();
        try {
            RequestParser requestParser = new RequestParser();
            Request request = requestParser.parseRequest(getBufferedReader());

            InputStream pageReader = getResourceReader().getResource(request.getUrl());
            responseWriter.writeSuccessResponse(pageReader, getBufferedOutputStream());
        } catch (FileNotFoundRuntimeException e) {
            responseWriter.writeNotFoundResponse(getBufferedOutputStream());
        } catch (BadRequestRuntimeException e) {
            responseWriter.writeBadResponse(getBufferedOutputStream());
        }
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public BufferedOutputStream getBufferedOutputStream() {
        return bufferedOutputStream;
    }

    public void setBufferedOutputStream(BufferedOutputStream bufferedOutputStream) {
        this.bufferedOutputStream = bufferedOutputStream;
    }

    public ResourceReader getResourceReader() {
        return resourceReader;
    }

    public void setResourceReader(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }
}
