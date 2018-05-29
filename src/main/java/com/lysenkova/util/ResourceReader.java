package com.lysenkova.util;

import com.lysenkova.exception.FileNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ResourceReader {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String webappPath;

    public InputStream getResource(String path) {
        try {
            InputStream bufferedReader = null;
            File fileName = new File(webappPath + path);
            return new BufferedInputStream(new FileInputStream(fileName));
        } catch (IOException e) {
            LOGGER.error("Error during searching file");
            throw new FileNotFoundRuntimeException("Error during searching file", e);
        }
    }

    public String getWebappPath() {
        return webappPath;
    }

    public void setWebappPath(String webappPath) {
        this.webappPath = webappPath;
    }
}
