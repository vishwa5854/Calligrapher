package com.company;

// This class is used to generate the handwritten output for given input string
// We read the text from a file(temporary)

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

class GenerateHandwriting {

    private ArrayList<String> textFromFile;
    private static final String DIRECTORY_PATH = "C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\";
    private String input;
    private HashMap<Character , Integer> alphabet;
    private HashMap<Integer , int[][]> data;
    int[][] result;

    GenerateHandwriting(HashMap<Integer,int[][]> d, String in){
        this.data = d;
        this.input = in;
        this.textFromFile = new ArrayList<>();
        this.alphabet = new HashMap<>();
        char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ".toCharArray();
        for(int i = 0; i< letters.length; i++){
            alphabet.put(letters[i] , i+1);
        }
        extractTextAndSetIntoStringArray();
        this.result = generateHandWriting();
    }

    private void extractTextAndSetIntoStringArray(){
        for(int i=0;i<this.input.length();i += 40){
            if(i+40 < this.input.length()) {
                this.textFromFile.add(this.input.substring(i, i+40));
            }
            else {
                this.textFromFile.add(this.input.substring(i,this.input.length()-1));
            }
        }
    }

    private int getTheMaxWidthOfArray(){
        Set<Integer> keys = this.data.keySet();
        int max = this.data.get(1)[0].length;
        for(int key : keys){
            if(this.data.get(key)[0].length > max){
                max = this.data.get(key)[0].length;
            }
        }
        return max;
    }

    private int[][] generateHandWriting(){
        int[][] out = new int[1080][1920];
        createWhitePaper(out);
        int row = 0;
        for (String s : textFromFile) {
            int indexForI = 10;
            for (int i = 0; i < s.length(); i++) {
                copyCharacterIntoArray(data.get(alphabet.get(s.charAt(i))), indexForI, out,row);
                System.out.println(Arrays.deepToString(data.get(alphabet.get(s.charAt(i)))));
                indexForI += data.get(alphabet.get(s.charAt(i)))[i].length + 5;
            }
            row += data.get(alphabet.get(s.charAt(s.length() -1))).length;
        }
        return out;
    }

    static void writeFinalGeneratedImage(BufferedImage writeImage, String file) {
        File writer = new File(DIRECTORY_PATH + file + ".jpg");
        try {
            ImageIO.write(writeImage,"jpg",writer);
        } catch (IOException e) {
            System.out.println("Failed to write the image into destination path ");
        }
    }



    private void createWhitePaper(int[][] out) {
        for(int i=0;i<1080;i++){
            for(int j=0;j<1920;j++){
                out[i][j] = -1;
            }
        }
    }

    private void copyCharacterIntoArray(int[][] input , int column, int[][] output,int row){
        for (int[] lol : input) {
            System.arraycopy(lol, 0, output[row++], column, lol.length);
        }
    }

}
