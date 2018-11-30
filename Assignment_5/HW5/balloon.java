import java.io.*;
import java.util.Scanner;

public class balloon {
    public static void main(String[] args) throws IOException{
        long startTime = System.currentTimeMillis();
        final int largeValue = (int) Math.pow(10, 8);
        File inputFile = new File("testBalloons.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter("1.txt"));
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
        // int[] numArrowsReq = new int[numProblems];

        /**
         * reading in the
         * different problem sizes
         */
        for(int i = 0; i < numProblems; i++) {
            problemSizes[i] = sc.nextInt();
        }

        for(int i = 0; i < problemSizes.length; i++) {
            int problemSize = problemSizes[i];
            int[] balloonPositions = new int[problemSize];
            // System.out.println(problemSize);
            /**
             * reading in the balloon
             * positions for a specific problem
             */
            for(int j = 0; j < problemSize; j++) {
                balloonPositions[j] = sc.nextInt();
                // System.out.print(balloonPositions[j] + " ");
            }

            int currentArrowHeight = balloonPositions[0]-1;
            balloonPositions[0] = largeValue;
            int numBalloonsShot = 1;
            int index = 1;
            boolean firstJump = false;
            int startIdxNextIter = -1;
            int numArrows = 0;
            while(numBalloonsShot < problemSize) {
                if(currentArrowHeight == balloonPositions[index]) {
                    balloonPositions[index] = largeValue;
                    numBalloonsShot++;
                    currentArrowHeight--;
                }

                else{
                    if(!firstJump && balloonPositions[index] != largeValue) {
                        startIdxNextIter = index;
                        firstJump = true;
                    }
                }
                index++;

                if(numBalloonsShot == problemSize) {
                    numArrows++;
                    break;
                }

                if(index == problemSize || currentArrowHeight == 0) {
                    numArrows++;
                    index = startIdxNextIter;
                    currentArrowHeight = balloonPositions[index];
                    firstJump = false;
                }
            }
            // numArrowsReq[i] = numArrows;
            if(i != problemSizes.length-1) {
                writer.append(numArrows + "\n");
            }
            else{
                writer.append(numArrows + "");
            }

        }
        sc.close();
        writer.close();
        long endTime = System.currentTimeMillis();
        System.out.println("Runtime: " + (endTime-startTime));
    }
}