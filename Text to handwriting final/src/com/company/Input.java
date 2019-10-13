package com.company;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/*This class is all about reading the input from the
* user in the format of pictures and then store then in an array
* and then store individual alphabet into a hash map with key being the alphabet
*/

class Input {
    private HashMap<Integer , int[][]> data;


    Input(String fC , String fS, String fN){
        data = new HashMap<>();
        Read read = new Read();
        int[][] capitalLetters = read.readDataFromPicture(fC);
        int[][] smallLetters = read.readDataFromPicture(fS);
        int[][] numbersAndSpecialCharacters = read.readDataFromPicture(fN);
        splitTheDataIntoHashMap(smallLetters,1);
        splitTheDataIntoHashMap(capitalLetters,27);
        splitTheDataIntoHashMap(numbersAndSpecialCharacters,53);
    }

    private void splitTheDataIntoHashMap(int[][] input,int index){
        int[][] temp = new int[input.length][100];
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
                int[][] test = new int[countTillZeroRow(temp)][temp[0].length];

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
                for(int z=0;z<inter.length;z++){
                    System.arraycopy(intermediate[northToSouth++],0,inter[z],0,intermediate[0].length);
                }
//
//                int eastToWest;
//                for(eastToWest = 0;eastToWest<inter[0].length;eastToWest++){
//                    if(inter[0][eastToWest] != -1){
//                        break;
//                    }
//                }
//
//                int[][] lol = new int[inter.length][inter[0].length - eastToWest + 1];
//                for(int z=0;z<intermediate.length;z++){
//                    System.arraycopy(inter[eastToWest++],0,lol[z],0,inter[0].length);
//                }

                this.data.put(indexForKey++, inter);
                countForTemp = 0;
                verifyTheImage(temp, indexForKey, inter);
                temp = new int[input.length][300];
            }
        }
        generateSpacePicture(indexForKey);
    }

    private void generateSpacePicture(int indexForKey) {
        int[][] temp;
        temp = new int[100][20];
        for(int i=0;i<temp.length;i++){
            for(int j=0;j<temp[0].length;j++){
                temp[i][j] = -1;
            }
        }
        this.data.put(indexForKey,temp);
    }

    private void verifyTheImage(int[][] temp, int indexForKey, int[][] result) {
        BufferedImage lol = new BufferedImage(temp.length,temp[0].length,5);
        for(int a=0;a<result.length;a++){
            for(int b=0;b<result[0].length;b++){
                lol.setRGB(b,a,result[a][b]);
            }
        }
        GenerateHandwriting.writeFinalGeneratedImage(lol, String.valueOf(indexForKey-1));
    }

    private int countTillZeroRow(int[][] lol){
        int i;
        for(i = 0; i<lol.length; i++){
            int count = 0;
            for(int j=0;j<lol[i].length;j++){
                if(lol[i][j] == 0){
                    count++;
                }
            }
            if(count == lol[i].length){
                break;
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

}
