package A1;

import static A1.main.*;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
        // Implementing chain(int key)
        // from the class Chaining here.
        // It's variables will be prefixed
        // with an 'h' for hashing function h(k).
        int hProduct = this.A * key;
        int hModValue = hProduct % power2(this.w);
        int hHashValue = hModValue >> (this.w - this.r);

        // Implementing g(k, i) now.
        int gHashSumI = hHashValue + i;
        int gHashValue = gHashSumI % power2(this.r);
        return gHashValue;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
        int numCollisions = 0;
        for(int i=0; i < this.m; i++) {
            int hashValue = this.probe(key, i);
            if(!isSlotEmpty(hashValue)) {
                numCollisions += 1;
            }
            else {
                this.Table[hashValue] = key;
                break;
            }
        }
        return numCollisions;
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    public int removeKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
        int slotsVisited = 0;
        for(int i = 0; i < this.m; i++) {
            int hashValue = this.probe(key, i);
            if(this.Table[hashValue] != key) {
                slotsVisited += 1;
            }
            else {
                // removing key from hash table
                this.Table[hashValue] = -1;
                break;
            }

        }
        return slotsVisited;
    }

}
