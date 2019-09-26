package com.company;

import java.util.HashMap;

/*This class is all about reading the input from the
* user in the format of pictures and then store then in an array
* and then store individual alphabet into a hash map with key being the alphabet*/

class Input {
    private char[] letters;
    private HashMap<Character , int[][]> data;
    private int[][] capitalLetters;
    private int[][] smallLetters;
    private int[][] numbersAndSpecialCharacters;
    int[][] allAlphabet;


    Input(String fC , String fS, String fN){
        Read read = new Read();
        this.capitalLetters = read.readDataFromPicture(fC);
        this.smallLetters = read.readDataFromPicture(fS);
        this.numbersAndSpecialCharacters = read.readDataFromPicture(fN);
        this.allAlphabet = new int[smallLetters.length][capitalLetters[0].length + smallLetters[0].length + numbersAndSpecialCharacters[0].length + 20];
        data = new HashMap<>();
        setTheCharactersValuesIntoListBasedOnAscii();
        copyAllPicturesIntoOne();
        this.letters = new char[26*2 + 10];
    }

    private void copyAllPicturesIntoOne() {
        int a = 0;
        for(int i=0;i<smallLetters.length;i++){
            System.arraycopy(smallLetters[i], a, allAlphabet[i], a, smallLetters[0].length);
        }
        a += smallLetters[0].length + 1;
        for(int i=0;i<capitalLetters.length;i++){
            System.arraycopy(capitalLetters[i], 0, allAlphabet[i], a, capitalLetters[0].length);
        }
        a += capitalLetters[0].length + 1;
        for(int i=0;i<numbersAndSpecialCharacters.length;i++){
            System.arraycopy(numbersAndSpecialCharacters[i], 0, allAlphabet[i], a, numbersAndSpecialCharacters[0].length);
        }
    }

    private void setTheCharactersValuesIntoListBasedOnAscii() {
        letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    }

    void splitTheDataIntoHashMap(){
        int[][][] temp = new int[capitalLetters.length][capitalLetters[0].length][capitalLetters[0].length*2];
        int indexForKey = 1;
        int countForTemp = 0;
        int tempLol = 0;
        for (int[] value : allAlphabet) {
            int count = 0;
            for (int j = 0; j < allAlphabet[0].length; j++) {
                if (value[j] == -1) {
                    count++;
                }
            }
            if (count != value.length) {
                System.arraycopy(value, 0, temp[tempLol][countForTemp++], 0, value.length);
            }
            if (count == value.length) {
                if (isArrayEmpty(temp[tempLol])) {
                    continue;
                }
                data.put(letters[indexForKey++], temp[tempLol]);
                countForTemp = 0;
                tempLol++;
            }
        }
    }

    private static boolean isArrayEmpty(int[][] temp){
        int count = 0;
        for (int[] ints : temp) {
            for (int anInt : ints) {
                if (anInt == 0) {
                    count++;
                }
            }
        }
        return count == (temp.length*temp[0].length);
    }

    HashMap<Character , int[][]> returnData(){
        return this.data;
    }

}
