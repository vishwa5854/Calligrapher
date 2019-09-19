package com.company;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[][] matrix = new int[3][9];
        input(matrix);
        int[][] transpose = transposeOfMatrix(matrix);
        HashMap<Integer , int[][]> hs = new HashMap<>();
        process(transpose, hs);
        bringBackTheValuesUnRotated(hs);
        printHashMap(hs);
    }

    private static int[][] rotatePictureAntiClockWise(int[][] input){
        int numberOfRows = input.length;
        int numberOfColumns = input[0].length;
        int[][] result = new int[numberOfColumns][numberOfRows];
        for(int i=0;i<numberOfRows;i++){
            for(int j=0;j<numberOfColumns;j++){
                result[j][i] = input[i][numberOfColumns-j-1];
            }
        }

        return result;
    }

    private static void bringBackTheValuesUnRotated(HashMap<Integer , int[][]> hs){
        for(int i=1;i<=hs.size();i++){
            hs.put(i+100,rotatePictureAntiClockWise(hs.get(i)));
            hs.remove(i);
        }
    }

    private static void printHashMap(HashMap<Integer, int[][]> hs) {
        for(int i=101;i<=hs.size()+100;i++){
            int[][] tem = hs.get(i);
            System.out.println(i);
            for (int[] ints : tem) {
                for (int anInt : ints) {
                    System.out.print(anInt + " ");
                }
                System.out.println();
            }
        }
    }

    private static void input(int[][] matrix) {
        System.out.println("Enter the values : ");
        Scanner scanner = new Scanner(System.in);
        for(int i=0;i<3;i++){
            for(int j=0;j<9;j++){
                matrix[i][j] = scanner.nextInt();
            }
        }
    }

    private static void process(int[][] transpose, HashMap<Integer, int[][]> hs) {
        int[][][] temp = new int[10][3][3];
        int indexForKey = 1;
        int countForTemp = 0;
        int tempLol = 0;
        for (int[] value : transpose) {
            int count = 0;
            for (int j = 0; j < transpose[0].length; j++) {
                if (value[j] == -1) {
                    count++;
                }
            }
            if (count != value.length) {
                System.arraycopy(value, 0, temp[tempLol][countForTemp++], 0, value.length);
            }
            if (count == value.length) {
                if (isArrayEmpty(temp[tempLol])) {
                    continue;
                }
                //makeZeroAsMinusOne(temp[tempLol]);
                hs.put(indexForKey++, temp[tempLol]);
                countForTemp = 0;
                tempLol++;
            }
        }
    }



    private static int[][] transposeOfMatrix(int[][] input){
        int numberOfRows = input.length;
        int numberOfColumns = input[0].length;
        int[][] result = new int[numberOfColumns][numberOfRows];
        for(int i=0;i<numberOfRows;i++){
            for(int j=0;j<numberOfColumns;j++){
                result[j][i] = input[numberOfRows-1-i][j];
            }
        }

        return result;
    }

    private static boolean isArrayEmpty(int[][] temp){
        int count = 0;
        for (int[] ints : temp) {
            for (int anInt : ints) {
                if (anInt == 0) {
                    count++;
                }
            }
        }
        return count == (temp.length*temp[0].length);
    }

    private static void makeZeroAsMinusOne(int[][] input){
        for(int i=0;i<input.length;i++){
            for(int j=0;j<input[i].length;j++){
                if(input[i][j] == 0){
                    input[i][j] = -1;
                }
            }
        }
    }

}
