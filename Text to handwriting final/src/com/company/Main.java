package com.company;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Input input = new Input("caps5.jpg","small2.png","num5.jpg");
        HashMap<Integer , int[][]> data = input.returnData();
        new GenerateHandwriting("test",data);
    }
}
