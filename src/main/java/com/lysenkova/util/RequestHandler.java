package com.lysenkova.util;

import com.lysenkova.entity.Request;
import com.lysenkova.exception.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class RequestHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private BufferedReader bufferedReader;
    private BufferedOutputStream bufferedOutputStream;
    private ResourceReader resourceReader;

    public void handle() {
        ResponseWriter responseWriter = new ResponseWriter();
        try {
            RequestParser requestParser = new RequestParser();
            Request request = requestParser.parseRequest(bufferedReader);

            InputStream pageReader = getResourceReader().getResource(request.getUrl());
            responseWriter.setWriter(bufferedOutputStream);
            responseWriter.writeSuccessResponse(pageReader);
            LOGGER.info("Successful response");
        } catch (ResponseException e) {
            if (e.getStatusCode().getCode()==404) {
                responseWriter.writeNotFoundResponse();
                LOGGER.debug("FILE_NOT_FOUND response");
            } else if (e.getStatusCode().getCode()==400) {
                responseWriter.writeBadRequestResponse();
                LOGGER.debug("BAD_REQUEST response");
            } else if (e.getStatusCode().getCode()==415) {
                responseWriter.writeMethodNotSupportedResponse();
                LOGGER.debug("METHOD_NOT_SUPPORTED response");
            } else if (e.getStatusCode().getCode()==500) {
                responseWriter.writeInternalServerErrorResponse();
                LOGGER.error("INTERNAL_SERVER_ERROR response");
            }
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
