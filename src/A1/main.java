package A1;

import A1.Chaining.*;
import A1.Open_Addressing.*;
import java.io.*;
import java.util.*;

public class main {

    /**
     * Calculate 2^w
     */
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }

    /**
     * Uniformly generate a random integer between min and max, excluding both
     */
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        //if the seed is equal or above 0, we use the input seed, otherwise not.
        if (seed >= 0) {
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max - min - 1);
        return i + min + 1;
    }

    /**
     * export CSV file
     */
    public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
        File file = new File(filePathName);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            int len = alphaList.size();
            fw.append("Alpha");
            for (int i = 0; i < len; i++) {
                fw.append("," + alphaList.get(i));
            }
            fw.append('\n');
            fw.append("Chain");
            for (int i = 0; i < len; i++) {
                fw.append("," + avColListChain.get(i));
            }
            fw.append('\n');
            fw.append("Open Addressing");
            for (int i = 0; i < len; i++) {
                fw.append(", " + avColListProbe.get(i));
            }
            fw.append('\n');
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*===========PART 1 : Experimenting with n===================*/
        //Initializing the three arraylists that will go into the output 
        ArrayList<Double> alphaList = new ArrayList<Double>();
        ArrayList<Double> avColListChain = new ArrayList<Double>();
        ArrayList<Double> avColListProbe = new ArrayList<Double>();

        //Keys to insert into both hash tables
        int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
            955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
            832, 881, 181, 784, 84, 133, 458, 36};

        //values of n to test for in the experiment
        int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        //value of w to use for the experiment on n
        int w = 10;

        for (int n : nList) {

            //initializing two hash tables with a seed
            Chaining MyChainTable = new Chaining(w, 137);
            Open_Addressing MyProbeTable = new Open_Addressing(w, 137);

            /*Use the hash tables to compute the average number of 
                        collisions over keys keysToInsert, for each value of n. 
                        The format of the three arraylists to fillis as follows:
                        
                        alphaList = arraylist of all tested alphas 
                                   (corresponding to each tested n)
                        avColListChain = average number of collisions for each
                                         Chain experiment 
                                         (make sure the order matches alphaList)
                        avColListProbe =  average number of collisions for each
                                         Linear Probe experiment
                                           (make sure the order matches)
                        The CSV file will output the result which you can visualize
             */
            //ADD YOUR CODE HERE
            double numKeys = (double) n;
            alphaList.add(numKeys/MyChainTable.m);
            int avgChainCollisions = 0;
            int avgProbeCollisions = 0;
            for(int i = 0; i < n; i++) {
                int key = keysToInsert[i];
                avgChainCollisions += MyChainTable.insertKey(key);
                avgProbeCollisions += MyProbeTable.insertKey(key);
            }
            avColListChain.add(avgChainCollisions/numKeys);
            avColListProbe.add(avgProbeCollisions/numKeys);
        }

        // generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

        /*===========    PART 2 : Test removeKey  ===================*/
 /* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
         */
        //Please not the output CSV will be slightly wrong; ignore the labels.
        ArrayList<Double> removeCollisions = new ArrayList<Double>();
        ArrayList<Double> removeIndex = new ArrayList<Double>();
        int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
            205, 186, 107, 179};

        //ADD YOUR CODE HERE
        Open_Addressing partTwoProbeTable = new Open_Addressing(w, 137);
        for(int i = 0; i < 16; i++) {
            partTwoProbeTable.insertKey(keysToInsert[i]);
        }
        for(int j = 0; j < keysToRemove.length; j++) {
            int key = keysToRemove[j];
            double numCollisions = (double) partTwoProbeTable.removeKey(key);
            removeCollisions.add(numCollisions);
            removeIndex.add((double) j);
        }
//        generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);

        /*===========PART 3 : Experimenting with w===================*/

 /*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
         */
        //generating random hash tables with no seed can be done by sending -1
        //as the seed. You can read the generateRandom method for detail.
        //randomNumber = generateRandom(0,55,-1);
        //Chaining MyChainTable = new Chaining(w, -1);
        //Open_Addressing MyProbeTable = new Open_Addressing(w, -1);
        //Lists to fill for the output CSV, exactly the same as in Task 1.
        ArrayList<Double> alphaList2 = new ArrayList<Double>();
        ArrayList<Double> avColListChain2 = new ArrayList<Double>();
        ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

        //ADD YOUR CODE HERE
        int[] wList = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        double numKeys = 32;
        int numSimulations = 10;
        for(int wVal: wList) {
            // making new hash tables
            // based on value of w
            Chaining partThreeChainingTable = new Chaining(wVal, -1);
            Open_Addressing partThreeOpenAddrTable = new Open_Addressing(wVal, -1);
            int mValue = partThreeChainingTable.m;
            alphaList2.add(numKeys/mValue);

            // arraylists to keep track of
            // averages over the 10 simulations
            ArrayList<Double> avgChainingCollisions = new ArrayList<Double>();
            ArrayList<Double> avgProbingCollisions = new ArrayList<Double>();


            // running 10 simulations
            // for each value of w
            for(int i = 0; i < numSimulations; i++) {
                double chainColl = 0;
                double probingColl = 0;
                ArrayList<Integer> partThreeKeysToInsert = new ArrayList<Integer>();
                // generating 32 random keys
                for(int j = 0; j < 32; j++) {
                    int randomNumber = generateRandom(234, 2672, -1);
                    if (!partThreeKeysToInsert.contains(randomNumber)) {
                        partThreeKeysToInsert.add(randomNumber);
                    }
                }
                for(int key: partThreeKeysToInsert) {
                    chainColl += partThreeChainingTable.insertKey(key);
                    probingColl += partThreeOpenAddrTable.insertKey(key);
                }
                avgChainingCollisions.add(chainColl/numKeys);
                avgProbingCollisions.add(probingColl/numKeys);
            }

            double totalAvgChainColl = 0;
            double totalAvgProbeColl = 0;
            for(double avgChainColl: avgChainingCollisions) {
                totalAvgChainColl += avgChainColl;
            }
            for(double avgProbeColl: avgProbingCollisions) {
                totalAvgProbeColl += avgProbeColl;
            }

            avColListChain2.add(totalAvgChainColl/numSimulations);
            avColListProbe2.add(totalAvgProbeColl/numSimulations);
        }
         generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);

    }

}
