import java.util.Scanner;

import jdk.nashorn.internal.codegen.types.NumericType;

import java.io.*;

public class islands {
    // final int largeValue = (int) Math.pow(10, 8);
    public static void main(String[] args) throws IOException{
        File inputFile = new File("testIslands.txt");
        // BufferedWriter writer = new BufferedWriter(new FileWriter("testIslands_solution.txt")); //TODO: have to change output file name
        Scanner sc = null;
        try{
            sc = new Scanner(inputFile);
        }
        catch(Exception e){
            System.out.println("File not found! :(");
            System.exit(1);
        }

        int numProblems = sc.nextInt();
        for(int i = 0; i < numProblems; i++) {
            int numRows = sc.nextInt();
            int numColumns = sc.nextInt();

            int[][][] ocean = new int[numRows+2][numColumns+2][2];
            /**
             * reading in the data
             */
            for(int j = 0; j < numRows; j++) {
                String row = sc.next();
                for(int k = 0; k < numColumns; k++) {
                    char cell = row.charAt(k);
                    if(cell == '-') {
                        ocean[j+1][k+1][0] = 1;
                    }
                    // ocean[j+1][k+1]
                }
            }

            int numIslands = 0;
            for(int j = 0; j < numRows; j++) {
                for(int k = 0; k < numColumns; k++) {
                    int[] cell = ocean[j+1][k+1];
                    if(cell[0] == 1 && cell[1] == 0) {
                        cell[1] = checkSurroundings(ocean, j+1, k+1);
                    }
                }
            }
            // printArray(ocean);
        }
    }

    public static int checkSurroundings(int[][][] ocean, int row, int column) {
        int max = 1;
        int islandNumber = 0;
        if(ocean[row-1][column][0] == 1) {
            islandNumber = ocean[row-1][column][1];
            if(max < islandNumber) {
                max = islandNumber;
            }
        }

        if(ocean[row][column-1][0] == 1) {
            islandNumber = ocean[row][column-1][1];
            if(max < islandNumber) {
                max = islandNumber;
            }
        }

        if(ocean[row+1][column][0] == 1) {
            islandNumber = ocean[row+1][column][1];
            if(max < islandNumber) {
                max = islandNumber;
            }
        }

        if(ocean[row][column+1][0] == 1) {
            islandNumber = ocean[row][column+1][1];
            if(max < islandNumber) {
                max = islandNumber;
            }
        }

        return max;
    }


    public static void printArray(int[][][] arr) {
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j][0]);
            }
            System.out.println();
        }
    }
}