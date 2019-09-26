package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


// We read the picture into a matrix and then make it black and white and store in a different matrix which has only black and white
// We store the values of pixels from actual picture based on only these blacks in the generated b&w picture

class Read {

    int[][] readDataFromPicture(String fileName){

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\" + fileName));
        } catch (IOException e) {
            System.out.println("Could't Read the Given input Image ");
        }
        int[][] pixelRequired = new int[Objects.requireNonNull(bufferedImage).getHeight()][bufferedImage.getWidth()];
        int[][] pixelBW = generateBlackAndWhite(fileName);
        for(int i=0;i<pixelRequired.length;i++){
            for(int j=0;j<pixelRequired[0].length;j++){
                if(pixelBW[i][j] != -1){
                    pixelRequired[i][j] = bufferedImage.getRGB(j,i);
                }
                else {
                    pixelRequired[i][j] = -1;
                }
            }
        }
        return pixelRequired;
    }

    private int[][] generateBlackAndWhite(String fileName){
        File input = new File("C:\\Users\\vishwanath\\Pictures\\Hand Writing Project\\" + fileName);
        BufferedImage image = null;
        try {
            image = ImageIO.read(input);
        } catch (IOException e) {
            System.out.println("Couldn't make the B&W of given picture");
        }
        int[][] pixel = new int[Objects.requireNonNull(image).getHeight()][image.getWidth()];
        BufferedImage result = new BufferedImage(
                Objects.requireNonNull(image).getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D graphic = result.createGraphics();
        graphic.drawImage(image, 0, 0, Color.WHITE, null);
        graphic.dispose();
        for(int i=0;i<result.getHeight();i++){
            for(int j=0;j<result.getWidth();j++){
                pixel[i][j] = result.getRGB(j,i);
            }
        }
        return pixel;
    }
}
