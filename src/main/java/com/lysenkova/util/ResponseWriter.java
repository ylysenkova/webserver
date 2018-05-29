package com.lysenkova.util;

import com.lysenkova.entity.StatusCode;
import com.lysenkova.exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ResponseWriter {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public void writeSuccessResponse(InputStream reader, OutputStream writer) {

        try {
            byte[] pageLine = new byte[8192];
            int byteCount;
            writeHeader(writer, StatusCode.OK);
            while ((byteCount = reader.read(pageLine))!=-1) {
                writer.write(pageLine, 0, byteCount);
            }
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Error during writing success response.");
            throw new InternalServerException("Something wrong during writing success response", e);
        }
    }

    public void writeNotFoundResponse(OutputStream writer) {
        try {
            writeHeader(writer, StatusCode.NOT_FOUND);
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Error during writing not found response.");
            throw new InternalServerException("Something wrong during writing not found response", e);
        }
    }

    public void writeBadResponse(OutputStream writer) {
        try {
            writeHeader(writer, StatusCode.BAD_REQUEST);
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Error during writing bad response.");
            throw new InternalServerException("Something wrong during writing bad response", e);
        }
    }

    private void writeHeader(OutputStream writer, StatusCode statusCode) throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("HTTP/1.1");
        headerBuilder.append(" ");
        headerBuilder.append(statusCode.getCode());
        headerBuilder.append(" ");
        headerBuilder.append(statusCode.getName());
        headerBuilder.append("\r\n");
        headerBuilder.append("\r\n");
        writer.write(headerBuilder.toString().getBytes());
    }
}
