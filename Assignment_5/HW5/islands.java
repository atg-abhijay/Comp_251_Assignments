import java.util.Scanner;
import java.io.*;

class Node {
    /**
     * belongToIsland -
     * which island number does the 'cell' belong
     * to. 0 at the beginning for every cell.
     *
     * landOrWater -
     * 1 if 'cell' is land, 0 for water
     *
     */
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
    public static void main(String[] args) throws IOException{
        // long startTime = System.currentTimeMillis();
        File inputFile = new File("testIslands.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter("testIslands_solution.txt"));
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
            /**
             * creating a larger array wherein we
             * surround the given array with water
             * on all sides so that we can handle
             * the corner or edge cells without
             * having to worry about going out of bounds
             *
             * new array looks like this -
             *      #########
             *      # given #
             *      # array #
             *      #########
             */
            Node[][] ocean = new Node[numRows+2][numColumns+2];

            /**
             * making the extra water part
             */
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
            /**
             * even though we have an array larger
             * than the original one, we only iterate
             * over those cells in the original array
             */
            for(int j = 0; j < numRows; j++) {
                for(int k = 0; k < numColumns; k++) {
                    Node cell = ocean[j+1][k+1];
                    /**
                     * if the cell is a piece of land
                     * and it does not belong to any island
                     */
                    if(cell.landOrWater == 1 && cell.belongToIsland == 0) {
                        numIslands++;
                        cell.belongToIsland = numIslands;
                        cell.visited = true;
                        markNeighbors(ocean, j+1, k+1, cell);
                    }
                }
            }

            /**
             * writing to the output file
             */
            if(i != numProblems-1) {
                writer.append(numIslands + "\n");
            }
            else{
                writer.append(numIslands + "");
            }
        }

        sc.close();
        writer.close();
        // long endTime = System.currentTimeMillis();
        // System.out.println("Runtime: " + (endTime-startTime));
    }


    /**
     * Recursively mark the neighbors of the
     * original cell with the island number
     * of the original cell
     *
     * @param ocean - 2D array of Node
     * @param row - row number of original cell
     * @param column - column number of original cell
     * @param original - cell whose neighbors we wish to mark
     */
    public static void markNeighbors(Node[][] ocean, int row, int column, Node original) {
        Node above = ocean[row-1][column];
        Node toLeft = ocean[row][column-1];
        Node below = ocean[row+1][column];
        Node toRight = ocean[row][column+1];

        if(evaluateNeighbor(original, above)) {
            markNeighbors(ocean, row-1, column, above);
        }

        if(evaluateNeighbor(original, toLeft)) {
            markNeighbors(ocean, row, column-1, toLeft);
        }

        if(evaluateNeighbor(original, below)) {
            markNeighbors(ocean, row+1, column, below);
        }

        if(evaluateNeighbor(original, toRight)) {
            markNeighbors(ocean, row, column+1, toRight);
        }
    }

    /**
     * If the neighboring cell to the parent is
     * a piece of land and it has not been visited
     * yet, we set the neighbor's island number
     * to that of the parent and set its visit
     * status to true
     *
     * @param parent
     * @param neighbor
     * @return true if we can recursively iterate over
     * the neighbors of this neighbor, false otherwise
     */
    public static boolean evaluateNeighbor(Node parent, Node neighbor) {
        if(neighbor.landOrWater == 1 && !neighbor.visited) {
            neighbor.belongToIsland = parent.belongToIsland;
            neighbor.visited = true;
            return true;
        }
        return false;
    }
}
