package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


// We read the picture into a matrix and then make it black and white and store in a different matrix which has only black and white
// We store the values of pixels from actual picture based on only these blacks in the generated b&w picture

public class Read {

    int[][] returnRequiredPixels(int[][] input){
        int[][] pixelBW = generateBlackAndWhite(input);
        int[][] pixelRequired = new int[input.length][input[0].length];
        for(int i=0;i<pixelRequired.length;i++){
            for(int j=0;j<pixelRequired[0].length;j++){
                if(pixelBW[i][j] != -1){
                    pixelRequired[i][j] = input[i][j];
                }
                else {
                    pixelRequired[i][j] = -1;
                }
            }
        }
        return pixelRequired;
    }

    private int[][] generateBlackAndWhite(int[][] input){
        BufferedImage image = new BufferedImage(input[0].length,input.length ,5 );

        for(int i=0;i<input.length;i++){
            for(int j=0;j<input[0].length;j++){
                image.setRGB(j,i,input[i][j]);
            }
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
