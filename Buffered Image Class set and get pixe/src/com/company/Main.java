package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\vishwanath\\IdeaProjects\\Buffered Image Class set and get pixe\\src\\com\\company\\lol.bmp"));
        BufferedImage bufferedImage1 = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight() , bufferedImage.getType());
        int[][] imagePixel = new int[bufferedImage.getHeight()][bufferedImage.getWidth()];
        for(int i=0;i<bufferedImage.getHeight();i++){
            for(int j=0;j<bufferedImage.getWidth();j++){
                imagePixel[i][j] = bufferedImage.getRGB(j,i);
                if(imagePixel[i][j] == 16777216){
                    imagePixel[i][j] = 0;
                }else {
                    imagePixel[i][j] = -1;
                }


                bufferedImage1.setRGB(j,i,imagePixel[i][j]);
                System.out.print(imagePixel[i][j]);
            }
            System.out.println();
        }
        File imageWrite = new File("C:\\Users\\vishwanath\\IdeaProjects\\Buffered Image Class set and get pixe\\src\\com\\company\\2.bmp");
        ImageIO.write(bufferedImage1,"bmp",imageWrite);
        System.out.println("Height is : "+ bufferedImage.getHeight());
        System.out.println("Width is : "+bufferedImage.getWidth());
    }
}
