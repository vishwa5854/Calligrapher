package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",5854);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        for(int i=0;i<3;i++) {
            objectOutputStream.writeObject(returnRequiredMatrix(i));
        }
        String data = "himynameisriteeshreddynenuchaalamanchivadininannumeeruvadilestenenvelliptaelsebuggaaytarendukaadanteemcheyalenusodayachesijavanerchukondilekapothebuggaavvadamkhayam";
        objectOutputStream.writeObject(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        int[][] result = (int[][]) objectInputStream.readObject();
        for(int[] a : result){
            System.out.println(Arrays.toString(a));
        }
        BufferedImage output = new BufferedImage(result[0].length,result.length,5);
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[0].length;j++){
                output.setRGB(j,i,result[i][j]);
            }
        }
        writeFinalGeneratedImage(output);
    }

    private static int[][] returnRequiredMatrix(int count) throws IOException {
        File file = null;
        switch (count){
            case 0:file = new File(FilePath.DIRECTORY_PATH + FilePath.CAPITAL_LETTERS_FILE_NAME);
                    break;
            case 1:file = new File(FilePath.DIRECTORY_PATH + FilePath.SMALL_LETTERS_FILE_NAME);
                    break;
            case 2:file = new File(FilePath.DIRECTORY_PATH + FilePath.NUMBERS_SPECIAL_CHARACTERS);
                    break;
            default:
                System.out.println("Invalid");
        }

        assert file != null;
        BufferedImage bufferedImage = ImageIO.read(file);
        int[][] temp = new int[bufferedImage.getHeight()][bufferedImage.getWidth()];
        for(int i=0;i<temp.length;i++){
            for(int j=0;j<temp[0].length;j++){
                temp[i][j] = bufferedImage.getRGB(j,i);
            }
        }
        return temp;
    }

    private static void writeFinalGeneratedImage(BufferedImage writeImage) {
        File writer = new File(FilePath.DIRECTORY_PATH + FilePath.OUTPUT_FILE_NAME + ".jpg");
        try {
            ImageIO.write(writeImage,"jpg",writer);
        } catch (IOException e) {
            System.out.println("Failed to write the image into destination path ");
        }
    }
}
