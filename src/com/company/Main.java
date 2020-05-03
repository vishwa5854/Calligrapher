package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Currently there are 2 fonts available");
        System.out.print("Choose 1 or 2 : ");
        setDataIntoMatrices(scanner.nextInt());
        Input input = new Input(Data.capitalLetters, Data.smallLetters, Data.numbers, Data.specialCharacters);
        HashMap<Integer, int[][]> data = input.returnData();
        int count = 1;
        while (true) {
            System.out.println("Enter the number of lines of text: ");
            int lines = scanner.nextInt();
            polishInput(scanner, lines);
            Data.result = new GenerateHandwriting(data, Data.text).result;
            System.out.println("Image has been successfully saved");
            generatePicture(count);
            File file = new File(Files.DIRECTORY_PATH);
            Desktop dt = Desktop.getDesktop();
            dt.open(file.getAbsoluteFile());
            count += 1;
        }
    }

    private static void polishInput(Scanner scanner, int lines) {
        Data.text = new ArrayList<>();
        for(int i = 0;i<lines;i++){
            String lol = scanner.next();
            lol += scanner.nextLine();
            lol = lol.toLowerCase();
            if(lol.compareTo("~") == 0){
                Data.text.add(" ");
            }
            else {
                int count = 0;
                for(int j=0;j<lol.length();j++){
                    if(lol.charAt(j) == '`'){
                        count += 1;
                    }
                }
                StringBuilder space = new StringBuilder();
                for(int j=0;j<count;j++){
                    space.append(" ");
                }
                if(count != 0){
                    Data.text.add("    " + space +  lol.substring(count));
                }else {
                    Data.text.add("    " + lol);
                }
            }
        }
    }

    private static void setDataIntoMatrices(int fontChoice) throws IOException {
        File file = new File(Files.DIRECTORY_PATH + fontChoice + "\\" + Files.CAPITAL_LETTERS_FILE_NAME);
        Data.capitalLetters = getMatrixFromPicture(file);
        file = new File(Files.DIRECTORY_PATH + fontChoice + "\\" + Files.SMALL_LETTERS_FILE_NAME);
        Data.smallLetters = getMatrixFromPicture(file);
        file = new File(Files.DIRECTORY_PATH + fontChoice + "\\" + Files.NUMBERS);
        Data.numbers = getMatrixFromPicture(file);
        file = new File(Files.DIRECTORY_PATH + fontChoice + "\\" + Files.SPECIAL_CHARACTERS);
        Data.specialCharacters = getMatrixFromPicture(file);
    }

    private static int[][] getMatrixFromPicture(File file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        int[][] temp = new int[bufferedImage.getHeight()][bufferedImage.getWidth()];
        for(int i=0;i<temp.length;i++){
            for(int j=0;j<temp[0].length;j++){
                temp[i][j] = bufferedImage.getRGB(j,i);
            }
        }
        return temp;
    }

    private static void generatePicture(int count){
        BufferedImage output = new BufferedImage(Data.result[0].length,Data.result.length,5);
        for(int i=0;i<Data.result.length;i++){
            for(int j=0;j<Data.result[0].length;j++){
                output.setRGB(j,i,Data.result[i][j]);
            }
        }
        writeFinalGeneratedImage(output, count);
    }

    private static void writeFinalGeneratedImage(BufferedImage writeImage, int count) {
        File writer = new File(Files.DIRECTORY_PATH + Files.OUTPUT_FILE_NAME + count + ".jpg");
        try {
            ImageIO.write(writeImage,"jpg",writer);
        } catch (IOException e) {
            System.out.println("Failed to write the image into destination path ");
        }
    }

}
