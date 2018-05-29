package com.lysenkova.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ResponseServer {
        public static void main(String[] args) throws Exception {
            ServerSocket serverSocket = new ServerSocket(3000);
            Socket socket = serverSocket.accept();
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String value;
            while (!(value = bufferedReader.readLine()).isEmpty()) {
                System.out.println(value);
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("HTTP/1.1 200 OK");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("Hello world");
            bufferedWriter.close();
        }
}
