package com.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Input input = new Input("caps.png","small.png","num.png");
        HashMap<Character,int[][]> data= input.returnData();
        input.splitTheDataIntoHashMap();
        Set<Character> keys = data.keySet();
        for(char a : keys){
            System.out.println(Arrays.deepToString(data.get(a)));
        }
        for(int i=0;i<input.allAlphabet.length;i++){
            for(int j=0;j<input.allAlphabet[0].length;j++){
                System.out.print(input.allAlphabet[i][j]);
            }
            System.out.println();
        }
    }
}
