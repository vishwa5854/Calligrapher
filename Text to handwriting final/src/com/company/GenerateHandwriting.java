package com.company;


// This class is used to generate the handwritten output for given input string
// We read the text from a file(temporary)

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class GenerateHandwriting {
    private String inputFileName;
    private ArrayList<String> textFromFile;
    private HashMap<Character , Integer> alphabet;
    private HashMap<Integer , int[][]> data;

    GenerateHandwriting(String fileName, HashMap<Integer,int[][]> d){
        this.inputFileName = fileName;
        this.data = d;
        this.textFromFile = new ArrayList<>();
        this.alphabet = new HashMap<>();
        char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        for(int i = 0; i< letters.length; i++){
            alphabet.put(letters[i] , i+1);
        }
        extractTextAndSetIntoStringArray();
        generateHandWriting();
    }

    private void extractTextAndSetIntoStringArray(){
        File file = new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\" + this.inputFileName + ".txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String lol;
            while ((lol = reader.readLine()) != null){
                this.textFromFile.add(lol);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't open the given text file ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateHandWriting(){
        int[][] outputFile = new int[1920][1080];
        for(int i=0;i<1920;i++){
            for(int j=0;j<1080;j++){
                outputFile[i][j] = -1;
            }
        }
        int indexForI = 10;
        //int indexForJ = 10;
        for(int i=0;i<textFromFile.get(0).length();i++){
            copyCharacterIntoArray(data.get(alphabet.get(textFromFile.get(0).charAt(i))),indexForI,outputFile);
            System.out.println(Arrays.deepToString(data.get(alphabet.get(textFromFile.get(0).charAt(i)))));
            indexForI += data.get(alphabet.get(textFromFile.get(0).charAt(i)))[i].length+5;
        }
        BufferedImage write = new BufferedImage(1080,1920 , 5);
        for(int i=0;i<1920;i++){
            for(int j=0;j<1080;j++){
                write.setRGB(j,i,outputFile[i][j]);
            }
        }
        writeFinalGeneratedImage(write,inputFileName);
    }

    private void copyCharacterIntoArray(int[][] input , int start, int[][] output){
        for(int i=start;i<input.length;i++){
            System.arraycopy(input[i],0,output[i],start,input[i].length);
        }
    }

    private static void writeFinalGeneratedImage(BufferedImage writeImage, String file) {
        File writer = new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\" + file + ".png");
        try {
            ImageIO.write(writeImage,"png",writer);
        } catch (IOException e) {
            System.out.println("Failed to write the image into destination path ");
        }
    }

}
