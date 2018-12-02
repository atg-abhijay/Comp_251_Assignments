import java.util.Scanner;
import java.io.*;

class Node {
    public boolean visited;
    public int belongToIsland;
    public int landOrWater;

    Node(int lw) {
        this.visited = false;
        this.belongToIsland = 0;
        this.landOrWater = lw;
    }
}

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
            Node[][] ocean = new Node[numRows+2][numColumns+2];

            for(int k = 0; k < numColumns+2; k++) {
                ocean[0][k] = new Node(0);
                ocean[numRows+1][k] = new Node(0);
            }

            for(int j = 0; j < numRows+2; j++) {
                ocean[j][0] = new Node(0);
                ocean[j][numColumns+1] = new Node(0);
            }

            /**
             * reading in the data
             */
            for(int j = 0; j < numRows; j++) {
                String row = sc.next();
                for(int k = 0; k < numColumns; k++) {
                    char cell = row.charAt(k);
                    int landOrWater = 0;
                    if(cell == '-') {
                        landOrWater = 1;
                    }
                    ocean[j+1][k+1] = new Node(landOrWater);
                }
            }

            int numIslands = 0;
            for(int j = 0; j < numRows; j++) {
                for(int k = 0; k < numColumns; k++) {
                    Node cell = ocean[j+1][k+1];
                    if(cell.landOrWater == 1 && cell.belongToIsland == 0) {
                        numIslands++;
                        cell.belongToIsland = numIslands;
                        cell.visited = true;
                        markNeighbors(ocean, j+1, k+1, cell);
                    }
                }
            }
            System.out.println(numIslands);
        }
    }


    public static void markNeighbors(Node[][] ocean, int row, int column, Node original) {
        Node above = ocean[row-1][column];
        Node toLeft = ocean[row][column-1];
        Node below = ocean[row+1][column];
        Node toRight = ocean[row][column+1];

        if(above.landOrWater == 1 && !above.visited) {
            above.belongToIsland = original.belongToIsland;
            above.visited = true;
            markNeighbors(ocean, row-1, column, above);
        }

        if(toLeft.landOrWater == 1 && !toLeft.visited) {
            toLeft.belongToIsland = original.belongToIsland;
            toLeft.visited = true;
            markNeighbors(ocean, row, column-1, toLeft);
        }

        if(below.landOrWater == 1 && !below.visited) {
            below.belongToIsland = original.belongToIsland;
            below.visited = true;
            markNeighbors(ocean, row+1, column, below);
        }

        if(toRight.landOrWater == 1 && !toRight.visited) {
            toRight.belongToIsland = original.belongToIsland;
            toRight.visited = true;
            markNeighbors(ocean, row, column+1, toRight);
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