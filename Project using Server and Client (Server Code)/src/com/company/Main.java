package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(5854);
        Socket socket = serverSocket.accept();
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        int[][] CAPITAL_LETTERS = (int[][]) objectInputStream.readObject();
        int[][] SMALL_LETTERS = (int[][]) objectInputStream.readObject();
        int[][] NUMBERS = (int[][]) objectInputStream.readObject();
        String textData = (String) objectInputStream.readObject();
        Input input = new Input(CAPITAL_LETTERS,SMALL_LETTERS,NUMBERS);
        HashMap<Integer , int[][]> data = input.returnData();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(new GenerateHandwriting(data,textData).result);
    }
}
