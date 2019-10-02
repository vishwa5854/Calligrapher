package com.company;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/*This class is all about reading the input from the
* user in the format of pictures and then store then in an array
* and then store individual alphabet into a hash map with key being the alphabet
*/

class Input {
    private HashMap<Integer , int[][]> data;
    private int[][] capitalLetters;
    private int[][] smallLetters;
    private int[][] numbersAndSpecialCharacters;
    int[][] allAlphabet;


    Input(String fC , String fS, String fN){
        data = new HashMap<>();
        Read read = new Read();
        this.capitalLetters = read.readDataFromPicture(fC);
        this.smallLetters = read.readDataFromPicture(fS);
        this.numbersAndSpecialCharacters = read.readDataFromPicture(fN);
        this.allAlphabet = new int[smallLetters.length][capitalLetters[0].length + smallLetters[0].length + numbersAndSpecialCharacters[0].length + 20];
        copyAllPicturesIntoOne();
    }

    private void copyAllPicturesIntoOne() {
        int a = 0;
        for(int i=0;i<smallLetters.length;i++){
            System.arraycopy(smallLetters[i], a, allAlphabet[i], a, smallLetters[0].length);
        }
        a += smallLetters[0].length;
        for(int i=0;i<capitalLetters.length;i++){
            System.arraycopy(capitalLetters[i], 0, allAlphabet[i], a, capitalLetters[0].length);
        }
        a += capitalLetters[0].length;
        for(int i=0;i<numbersAndSpecialCharacters.length;i++){
            System.arraycopy(numbersAndSpecialCharacters[i], 0, allAlphabet[i], a, numbersAndSpecialCharacters[0].length);
        }
    }

    void splitTheDataIntoHashMap(){
        int[][] temp = new int[allAlphabet[0].length/60][allAlphabet.length];
        int[][] rotated = rotateClockWise(allAlphabet);
        int indexForKey = 1;
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
                for(int a =0;a<test.length;a++){
                    System.arraycopy(temp[a],0,test[a],0,test[a].length);
                }
                this.data.put(indexForKey++,rotatePictureAntiClockWise(test));
                countForTemp = 0;
                temp = new int[allAlphabet[0].length/60][allAlphabet.length];
            }
        }
        writeHashMap();
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

    private void writeHashMap(){
        Set<Integer> keys = this.data.keySet();
        for(int a : keys){
            System.out.println(a);
            System.out.println(Arrays.deepToString(data.get(a)));
            BufferedImage write = new BufferedImage(data.get(a)[0].length,data.get(a).length, 5);
            for(int i=0;i<data.get(a).length;i++){
                for(int j=0;j<data.get(a)[0].length;j++){
                    write.setRGB(j,i,data.get(a)[i][j]);
                }
            }
            writeFinalGeneratedImage(write, String.valueOf(a));
        }
    }

    private static void writeFinalGeneratedImage(BufferedImage writeImage,String fileName) {
        File writer = new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\" + fileName + ".png");
        try {
            ImageIO.write(writeImage,"png",writer);
        } catch (IOException e) {
            System.out.println("Failed to write the image into destination path ");
        }
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

}
