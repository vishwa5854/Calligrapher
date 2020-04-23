package com.company;

// This class is used to generate the handwritten output for given input string
// We read the text from a file(temporary)

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GenerateHandwriting {

    private ArrayList<String> textFromFile;
    private HashMap<Character , Integer> alphabet;
    private HashMap<Integer , int[][]> data;
    public int[][] result;

    public GenerateHandwriting(HashMap<Integer,int[][]> d, ArrayList<String> in){
        this.data = d;
        this.textFromFile = in;
        this.textFromFile.add(0, " ");
        this.alphabet = new HashMap<>();
        char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()-_+={}[]:;\"'<>,.?/|\\ ".toCharArray();
        for(int i = 0; i< letters.length; i++){
            alphabet.put(letters[i] , i+1);
        }
        System.out.println(alphabet);
        this.result = generateHandWriting();
    }



    private int[][] generateHandWriting(){
        int[][] out = new int[3840][2160];
        createWhitePaper(out);
        int row = 0;
        for (String s : textFromFile) {
            int indexForI = 20;
            for (int i = 0; i < s.length(); i++) {
                copyCharacterIntoArray(data.get(alphabet.get(s.charAt(i))), indexForI, out,row);
                indexForI += data.get(alphabet.get(s.charAt(i)))[i].length + 6;
            }
            row += data.get(alphabet.get(s.charAt(s.length() -1))).length + 10;

        }
        return subtract(out);
    }



    private void createWhitePaper(int[][] out) {
        for(int i=0;i<3840;i++){
            for(int j=0;j<2160;j++){
                out[i][j] = -1;
            }
        }
    }

    private void copyCharacterIntoArray(int[][] input , int column, int[][] output,int row){
        for (int[] lol : input) {
            System.arraycopy(lol, 0, output[row++], column, lol.length);
        }
    }

    private int[][] subtract(int[][] in){
        File file = new File("C:\\Users\\vishw\\OneDrive\\Pictures\\Hand Writing Project\\lol.jpg");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] out = new int[3840][2160];
        for(int i=0;i<bufferedImage.getHeight();i++){
            for(int j=0;j<bufferedImage.getWidth();j++){
                out[i][j] = bufferedImage.getRGB(j,i);
            }
        }
        for(int i=0;i<3840;i++){
            for(int j=0;j<2160;j++){
                if(in[i][j] == -1) {
                    out[i][j] -= in[i][j];
                }else {
                    out[i][j] = in[i][j];
                }
            }
        }
        return out;
    }

    static void writeFinalGeneratedImage(BufferedImage writeImage, String file) {
        File writer = new File("C:\\Users\\vishw\\OneDrive\\Pictures\\Hand Writing Project\\" + file + ".jpg");
        try {
            ImageIO.write(writeImage,"jpg",writer);
        } catch (IOException e) {
            System.out.println("Failed to write the image into destination path ");
        }
    }

}
