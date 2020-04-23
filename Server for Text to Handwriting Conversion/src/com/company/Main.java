package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(5854);
        //noinspection InfiniteLoopStatement

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New Client");
            Thread client = new Client(socket);
            client.start();
        }
    }

}
