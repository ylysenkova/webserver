package com.lysenkova.util;

import com.lysenkova.entity.HttpMethod;
import com.lysenkova.entity.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public Request parseRequest(BufferedReader bufferedReader) {
        String requestLine;
        Request request = new Request();
        try {
            requestLine = bufferedReader.readLine();
            injectUrlAndMethod(request, requestLine);
            injectHeaders(request, bufferedReader);
        }catch(Exception e) {
            LOGGER.error("Error during parsing request.");
            throw new RuntimeException("Error during parsing request.", e);
        }
        LOGGER.debug("Parsed request {}", request);
        return request;
    }

    private void injectUrlAndMethod(Request request, String requestLine) {
        String[] requestLineArray = requestLine.split(" ");
        request.setHttpMethod(HttpMethod.getByName(requestLineArray[0]));
        request.setUrl(requestLineArray[1]);
    }

    private void injectHeaders(Request request, BufferedReader requestReader) throws IOException {
        Map<String, String> headerMap = new HashMap<>();
        String[] headersArray;
        String requestHeaderLine;
        while (!(requestHeaderLine = requestReader.readLine()).isEmpty()) {
            headersArray = requestHeaderLine.trim().split(": ");
            headerMap.put(headersArray[0], headersArray[1]);
            request.setHeaders(headerMap);
        }
    }
}
