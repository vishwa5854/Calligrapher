package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/*This class is all about reading the input from the
* user in the format of pictures and then store then in an array
* and then store individual alphabet into a hash map with key being the alphabet
*/

class Input {

    private HashMap<Integer , int[][]> data;

    Input(int[][] caps,int[][] small,int[][] num){
        data = new HashMap<>();
        Read read = new Read();
        int[][] capitalLetters = read.returnRequiredPixels(caps);
        int[][] smallLetters = read.returnRequiredPixels(small);
        int[][] numbersAndSpecialCharacters = read.returnRequiredPixels(num);
        splitTheDataIntoHashMap(smallLetters,1);
        splitTheDataIntoHashMap(capitalLetters,27);
        splitTheDataIntoHashMap(numbersAndSpecialCharacters,53);
    }

    private void splitTheDataIntoHashMap(int[][] input,int index){
        // This function basically takes the entire alphabet set as an 2D Array and then figures out alphabets from it
        // and stores the corresponding 2D Array in a data structure ( hash map ) for further use

        int[][] temp = new int[input.length][300];
        int[][] rotated = rotateClockWise(input);
        int indexForKey = index;
        int countForTemp = 0;

        for (int[] value : rotated) {
            int count = 0;
            for (int j = 0; j < rotated[0].length; j++) {
                if (value[j] == -1) {
                    count++;
                }
            }
            if (count != value.length) {
                System.arraycopy(value, 0, temp[countForTemp++], 0, value.length);
            }
            if (count == value.length) {
                if (isArrayEmpty(temp)) {
                    continue;
                }
                int[][] test = new int[countTillNumber(temp,0,true)][temp[0].length];

                for (int a = 0; a < test.length; a++) {
                    System.arraycopy(temp[a], 0, test[a], 0, test[a].length);
                }

                int[][] intermediate = rotatePictureAntiClockWise(test);
                int northToSouth;
                for(northToSouth=0;northToSouth<intermediate.length;northToSouth++){
                    if(intermediate[northToSouth][0] == -1){
                        break;
                    }
                }
                int[][] inter = new int[intermediate.length - northToSouth][intermediate[0].length];

                for (int[] ints : inter) {
                    System.arraycopy(intermediate[northToSouth++], 0, ints, 0, intermediate[0].length);
                }

                // inter = cutDownTheWhiteBarsInPicture(inter);
                this.data.put(indexForKey++, changeColor(inter));
                countForTemp = 0;
                verifyTheImage(indexForKey, changeColor(inter));
                temp = new int[input.length][300];
            }
        }
        generateSpacePicture(indexForKey);
    }

    private void generateSpacePicture(int indexForKey) {
        // As the title suggests this function generates a white or blank picture and add's it to the Data Structure ( Hash Map )

        int[][] temp;
        temp = new int[100][20];
        for(int i=0;i<temp.length;i++){
            for(int j=0;j<temp[0].length;j++){
                temp[i][j] = -1;
            }
        }
        this.data.put(indexForKey,temp);
    }

    private void verifyTheImage(int indexForKey, int[][] result) {
        BufferedImage lol = new BufferedImage(result[0].length,result.length,5);
        for(int a=0;a<result.length;a++){
            for(int b=0;b<result[0].length;b++){
                lol.setRGB(b,a,result[a][b]);
            }
        }
        GenerateHandwriting.writeFinalGeneratedImage(lol, String.valueOf(indexForKey-1));
    }

    private int countTillNumber(int[][] lol,int number,Boolean decision){
        int i;
        for(i = 0; i<lol.length; i++){
            int count = 0;
            for(int j=0;j<lol[i].length;j++){
                if(lol[i][j] == number){
                    count++;
                }
            }
            if(decision) {
                if (count == lol[i].length) {
                    break;
                }
            }else {
                if (count != lol[i].length) {
                    break;
                }
            }
        }
        return i;
    }

    private static int[][] rotatePictureAntiClockWise(int[][] input){
        int numberOfRows = input.length;
        int numberOfColumns = input[0].length;
        int[][] result = new int[numberOfColumns][numberOfRows];
        for(int i=0;i<numberOfRows;i++){
            for(int j=0;j<numberOfColumns;j++){
                result[j][i] = input[i][numberOfColumns-j-1];
            }
        }
        return result;
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


    private static int[][] rotateClockWise(int[][] input){
        int numberOfRows = input.length;
        int numberOfColumns = input[0].length;
        int[][] result = new int[numberOfColumns][numberOfRows];
        for(int i=0;i<numberOfRows;i++){
            for(int j=0;j<numberOfColumns;j++){
                result[j][i] = input[numberOfRows-1-i][j];
            }
        }
        return result;
    }


    HashMap<Integer , int[][]> returnData(){
        return this.data;
    }


//    private int[][] cutDownTheWhiteBarsInPicture(int[][] input){
//        int top = countTillNumber(input,-1,false);
//        int[][] temp = new int[input.length - top][input[0].length];
//        for(int i=top;i<input.length;i++){
//            System.arraycopy(input[i],0,temp[i-top],0,input[i].length);
//        }
//        int bottom = temp.length - countTillNumber(input,-1,false);
//        int[][] result = new int[input.length - top - bottom][input[0].length];
//        for(int i=top;i<bottom;i++){
//            System.arraycopy(input[i],0,result[i-top],0,input[i].length);
//        }
//        return temp;
//    }

    private int[][] changeColor(int[][] input){
        Color color = new Color(71, 91, 255);
        int colorRequired = color.getRGB();
        for(int i=0;i<input.length;i++){
            for(int j=0;j<input[0].length;j++){
                if(input[i][j] != -1){
                    input[i][j] = colorRequired;
                }
            }
        }
        return input;
    }

}
