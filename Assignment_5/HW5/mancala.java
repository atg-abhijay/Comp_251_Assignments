import java.io.*;
import java.util.Scanner;

public class mancala {
    public static void main(String[] args) throws IOException {
        File inputFile = new File("testMancala.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter("testMancala_solution.txt")); //TODO: have to change output file name
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
            int[] mancalaBoard = new int[12];
            /**
             * building the mancala board
             */
            for(int j = 0; j < 12; j++) {
                mancalaBoard[j] = sc.nextInt();
            }

            boolean stop = false;
            while(!stop) {
                stop = true;
                // int firstCavity, secondCavity, thirdCavity;
                // firstCavity = secondCavity = thirdCavity = 0;
                for(int j = 0; j < 10; j++) {
                    int firstCavity = mancalaBoard[j];
                    int secondCavity = mancalaBoard[j+1];
                    int thirdCavity = mancalaBoard[j+2];

                    if(firstCavity == 0 && secondCavity == 1 && thirdCavity == 1) {
                        mancalaBoard[j] = 1;
                        mancalaBoard[j+1] = 0;
                        mancalaBoard[j+2] = 0;
                        stop = false;
                    }
                }
            }

            int numPebblesLeft = 0;
            for(int j = 0; j < 12; j++) {
                numPebblesLeft += mancalaBoard[j];
            }

            /**
             * writing to the output file
             */
            if(i != numProblems-1) {
                writer.append(numPebblesLeft + "\n");
            }
            else{
                writer.append(numPebblesLeft + "");
            }
        }
        sc.close();
        writer.close();
    }
}