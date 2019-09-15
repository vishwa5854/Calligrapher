package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        // First Read the handwriting of the user based on alphabets and numbers
        // Then segregate each of the alphabet into a unique pixelMatrix
        // Now read the text and call the corresponding pixel matrix and generate a final picture which is made out of these elements


        AlphabetDataStructure[] alphabetDataStructures = new AlphabetDataStructure[72];
        BufferedImage readImage = null;
        BufferedImage writeImage = null;

        try {
            readImage = ImageIO.read(new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\az.png"));
            writeImage = new BufferedImage(readImage.getWidth(),readImage.getHeight(),readImage.getType());
        } catch (IOException e) {
            System.out.println("Couldn't read the user generated text file ");
        }

        int[][] pixelMatrix = new int[Objects.requireNonNull(readImage).getHeight()][readImage.getWidth()];

        setTheDataIntoPixelMatrix(readImage, writeImage, pixelMatrix);
        extractAlphabetFromPicture(pixelMatrix,alphabetDataStructures,readImage);
        //writeFinalGeneratedImage(writeImage);
    }

    private static void setTheDataIntoPixelMatrix(BufferedImage readImage, BufferedImage writeImage, int[][] pixelMatrix) {
        for(int i=0;i<readImage.getHeight();i++){
            for(int j=0;j<readImage.getWidth();j++){
                pixelMatrix[i][j] = readImage.getRGB(j,i);
                System.out.print(pixelMatrix[i][j]);
                writeImage.setRGB(j,i,pixelMatrix[i][j]);
            }
            System.out.println();
        }
    }

    private static void writeFinalGeneratedImage(BufferedImage writeImage) {
        File writer = new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\temp.png");
        try {
            ImageIO.write(writeImage,"png",writer);
        } catch (IOException e) {
            System.out.println("Failed to write the image into destination path ");
        }
    }

    private static void extractAlphabetFromPicture(int[][] pixelMatrix , AlphabetDataStructure[] alphabetDataStructures, BufferedImage readImage ){
        System.out.println("For pixel matrix row : " + pixelMatrix.length + "\n column : "+pixelMatrix[0].length);
        int[][] transpose = rotatePictureClockWise(pixelMatrix);
        System.out.println("Row size is : "+transpose.length);
        System.out.println("Column size is : "+transpose[0].length);
        System.out.println("Image type is : " +readImage.getType());
        BufferedImage write = new BufferedImage(transpose[0].length,transpose.length,readImage.getType());
        for (int i=0;i<pixelMatrix[0].length;i++) {
            for (int j = 0; j < pixelMatrix.length; j++) {
                write.setRGB(j,i,transpose[i][j]);
            }
        }
        writeFinalGeneratedImage(write);
    }

    private static int[][] rotatePictureClockWise(int[][] input){
        int numberOfRows = input.length;
        int numberOfColumns = input[0].length;
        int[][] result = new int[numberOfColumns][numberOfRows];
        for(int i=0;i<numberOfRows;i++){
            for(int j=0;j<numberOfColumns;j++){
                result[j][i] = input[numberOfRows - 1 - i][j];
            }
        }

        return result;
    }

    private static void separateCharacters(int[][] pixelsTransformed, AlphabetDataStructure[] alphabetDataStructures){
        for(int i=0;i<pixelsTransformed.length;i++){
            int count = 0;
            for(int j=0;j<pixelsTransformed[0].length;j++){
                if(pixelsTransformed[i][j] == -1){
                    count++;
                }
            }
            if(count == pixelsTransformed[0].length){

            }
        }
    }
}
