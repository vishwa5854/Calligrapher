package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        Input input = new Input("caps.png","small.png","num.png");
        BufferedImage read = ImageIO.read(new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\caps.png"));
        input.splitTheDataIntoHashMap();
        BufferedImage write = new BufferedImage(input.allAlphabet[0].length,input.allAlphabet.length ,read.getType());
        for(int i=0;i<input.allAlphabet.length;i++){
            for(int j=0;j<input.allAlphabet[0].length;j++){
                write.setRGB(j,i,input.allAlphabet[i][j]);
            }
        }
        writeFinalGeneratedImage(write);
        HashMap<Integer , int[][]> data = input.returnData();
        GenerateHandwriting handwriting = new GenerateHandwriting("test1",data);
    }

    private static void writeFinalGeneratedImage(BufferedImage writeImage) {
        File writer = new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\" + "lol" + ".png");
        try {
            ImageIO.write(writeImage,"png",writer);
        } catch (IOException e) {
            System.out.println("Failed to write the image into destination path ");
        }
    }
}
