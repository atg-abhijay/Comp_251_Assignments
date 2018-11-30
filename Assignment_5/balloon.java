import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;

public class balloon {
    public static void main(String[] args) {
        File inputFile = new File("HW5/test.txt"); //testBalloons
        Scanner sc = null;
        try{
            sc = new Scanner(inputFile);
        }
        catch(Exception e){
            System.out.println("File not found! :(");
            System.exit(1);
        }

        int numProblems = sc.nextInt();
        int[] problemSizes = new int[numProblems];
        int[] numBalloonsReq = new int[numProblems];

        /**
         * reading in the
         * different problem sizes
         */
        for(int i = 0; i < numProblems; i++) {
            problemSizes[i] = sc.nextInt();
        }

        for(int i = 0; i < problemSizes.length; i++) {
            int problemSize = problemSizes[i];
            LinkedList<Integer> balloonPositions = new LinkedList<Integer>();
            // System.out.println(problemSize);
            /**
             * reading in the balloon
             * positions for a specific problem
             */
            int numBalloons = 0;
            int currentBalloonHeight = 0;
            for(int j = 0; j < problemSize; j++) {
                balloonPositions.add(sc.nextInt());
                // System.out.print(balloonPositions[j] + " ");
            }
            // System.out.println();

        }
    }
}