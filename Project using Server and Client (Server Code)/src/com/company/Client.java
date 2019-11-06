package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Client extends Thread{

    private Socket socket;

    Client(Socket socket1){
        this.socket = socket1;
    }


    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            int[][] CAPITAL_LETTERS = (int[][]) objectInputStream.readObject();
            int[][] SMALL_LETTERS = (int[][]) objectInputStream.readObject();
            int[][] NUMBERS = (int[][]) objectInputStream.readObject();
            String textData = (String) objectInputStream.readObject();
            Input input = new Input(CAPITAL_LETTERS, SMALL_LETTERS, NUMBERS);
            HashMap<Integer, int[][]> data = input.returnData();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new GenerateHandwriting(data, textData).result);
        }catch (Exception ignored){}
    }


}
