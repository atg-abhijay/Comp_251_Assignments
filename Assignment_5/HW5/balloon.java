import java.io.*;
import java.util.Scanner;

public class balloon {
    public static void main(String[] args) throws IOException{
        long startTime = System.currentTimeMillis();
        /**
         * use large arbitrary value for
         * heights of balloons that are popped
         */
        final int largeValue = (int) Math.pow(10, 8);
        File inputFile = new File("testBalloons.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter("testBalloons_solution.txt"));
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
        /**
         * reading in the
         * different problem sizes
         */
        for(int i = 0; i < numProblems; i++) {
            problemSizes[i] = sc.nextInt();
        }

        for(int i = 0; i < problemSizes.length; i++) {
            int problemSize = problemSizes[i];
            int[] balloonHeights = new int[problemSize];
            /**
             * reading in the heights for the
             * balloons specific to this problem
             */
            for(int j = 0; j < problemSize; j++) {
                balloonHeights[j] = sc.nextInt();
            }

            /**
             * consider the first balloon already and
             * change the arrow height, number of balloons
             * popped etc.
             */
            int currentArrowHeight = balloonHeights[0]-1;
            balloonHeights[0] = largeValue;
            int numBalloonsPopped = 1;
            int index = 1;
            int numArrows = 0;
            /**
             * these two will keep track of the first time
             * we encounter a balloon that is at the same
             * height or above along our downward journey
             */
            boolean firstJump = false;
            int startIdxNextIter = -1;
            /**
             * keep iterating until all
             * the balloons have been popped
             */
            while(numBalloonsPopped < problemSize) {
                if(currentArrowHeight == balloonHeights[index]) {
                    balloonHeights[index] = largeValue;
                    numBalloonsPopped++;
                    currentArrowHeight--;
                }

                else{
                    /**
                     * three conditions come into play here -
                     * 1. arrow height != balloon height
                     * 2. we have not made the first jump yet
                     * 3. the balloon height is not the arbitrary large value
                     *
                     * we set firstJump to true since we have found
                     * the new start index for the iteration that will follow this one
                     * (saves us time since we do not have to iterate from the
                     * beginning once again)
                     */
                    if(!firstJump && balloonHeights[index] != largeValue) {
                        startIdxNextIter = index;
                        firstJump = true;
                    }
                }
                index++;

                if(numBalloonsPopped == problemSize) {
                    numArrows++;
                    break;
                }

                /**
                 * when we have reached the end of the list
                 * or the arrow has dropped to height 0 then,
                 * we update #arrows used, set the index value
                 * for the next iteration, make the arrow
                 * height equal to that of the balloon at that index
                 * and reset firstJump so that we can locate the new
                 * start index later on
                 */
                if(index == problemSize || currentArrowHeight == 0) {
                    numArrows++;
                    index = startIdxNextIter;
                    currentArrowHeight = balloonHeights[index];
                    firstJump = false;
                }
            }

            /**
             * writing to the output file
             */
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