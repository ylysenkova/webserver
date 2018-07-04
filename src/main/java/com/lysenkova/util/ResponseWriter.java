package com.lysenkova.util;

import com.lysenkova.entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ResponseWriter {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String LINE_SEPARATOR = "\r\n";
    private OutputStream writer;


    public void writeSuccessResponse(InputStream reader) {

        try {
            byte[] pageLine = new byte[8192];
            int byteCount;
            writeStatusLine(StatusCode.OK);
            while ((byteCount = reader.read(pageLine))!=-1) {
                writer.write(pageLine, 0, byteCount);
            }
        } catch (IOException e) {
            LOGGER.error("Error during writing success response.");
            throw new RuntimeException("Something wrong during writing success response", e);
        }
    }

    public void writeNotFoundResponse() {
        try {
            writeStatusLine(StatusCode.NOT_FOUND);
        } catch (IOException e) {
            LOGGER.error("Error during writing not found response.");
            throw new RuntimeException("Something wrong during writing not found response", e);
        }
    }

    public void writeBadRequestResponse() {
        try {
            writeStatusLine(StatusCode.BAD_REQUEST);
        } catch (IOException e) {
            LOGGER.error("Error during writing bad response.");
            throw new RuntimeException("Something wrong during writing bad response", e);
        }
    }

    public void writeInternalServerErrorResponse() {
        try {
            writeStatusLine(StatusCode.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOGGER.error("Error during writing bad response.");
            throw new RuntimeException("Something wrong during writing bad response", e);
        }
    }

    public void writeMethodNotSupportedResponse() {
        try {
            writeStatusLine(StatusCode.METHOD_NOT_SUPPORTED);
        } catch (IOException e) {
            LOGGER.error("Error during writing bad response.");
            throw new RuntimeException("Something wrong during writing bad response", e);
        }
    }

    private void writeStatusLine(StatusCode statusCode) throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("HTTP/1.1");
        headerBuilder.append(" ");
        headerBuilder.append(statusCode.getCode());
        headerBuilder.append(" ");
        headerBuilder.append(statusCode.getMessage());
        headerBuilder.append(LINE_SEPARATOR);
        headerBuilder.append(LINE_SEPARATOR);
        writer.write(headerBuilder.toString().getBytes());
    }

    public void setWriter(OutputStream writer) {
        this.writer = writer;
    }
}
