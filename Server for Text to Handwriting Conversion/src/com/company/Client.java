package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Client extends Thread{

    private Socket socket;

    Client(Socket socket1){
        this.socket = socket1;
    }


    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            int[][] CAPITAL_LETTERS = (int[][]) in.readObject();
            int[][] SMALL_LETTERS = (int[][]) in.readObject();
            int[][] NUMBERS = (int[][]) in.readObject();
            int[][] CHARACTERS = (int[][]) in.readObject();
            ArrayList<String> textData;
            textData =  (ArrayList<String>) in.readObject();
            System.out.println(textData);
            Input input = new Input(CAPITAL_LETTERS, SMALL_LETTERS, NUMBERS, CHARACTERS);
            HashMap<Integer, int[][]> data = input.returnData();
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new GenerateHandwriting(data, textData).result);
        }catch (Exception ignored){}
    }

}
