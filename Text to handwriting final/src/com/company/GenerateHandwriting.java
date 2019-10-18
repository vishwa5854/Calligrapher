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
        char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ".toCharArray();
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
        createBufferedImageObject(out);
    }

    private void createBufferedImageObject(int[][] out) {
        BufferedImage lol = new BufferedImage(out[0].length,out.length,5);
        for(int i=0;i<out.length;i++){
            for(int j=0;j<out[0].length;j++){
                lol.setRGB(j,i,out[i][j]);
            }
        }
        writeFinalGeneratedImage(lol, "Vani");
    }

    private void createWhitePaper(int[][] out) {
        for(int i=0;i<1080;i++){
            for(int j=0;j<1920;j++){
                out[i][j] = -1;
            }
        }
    }

    private void copyCharacterIntoArray(int[][] input , int column, int[][] output,int row){
        for (int[] ints : input) {
            System.arraycopy(ints, 0, output[row++], column, ints.length);
        }
    }

    static void writeFinalGeneratedImage(BufferedImage writeImage, String file) {
        File writer = new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\" + file + ".jpg");
        try {
            ImageIO.write(writeImage,"jpg",writer);
        } catch (IOException e) {
            System.out.println("Failed to write the image into destination path ");
        }
    }

}
