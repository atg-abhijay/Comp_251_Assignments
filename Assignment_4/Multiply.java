import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }

    public static int[] naive(int size, int x, int y) {

        // YOUR CODE GOES HERE  (Note: Change return statement)
        if(size == 1) {
            return new int[] {x*y, 1};
        }
        int[] result = new int[2];

        /**
         * can achieve -
         * 1. floor upon division by 2
         * 2. ceiling upon division by 2
         * 3. x mod (2^p), for some p
         * by using bit shifting as demonstrated
         * below for m,a,b,c,d
         */
        /**
         * right bit shift corresponds
         * to taking the floor upon
         * division by 2. if we want
         * to take ceiling upon division
         * by 2, we must add the last
         * bit of 'size' to the floor value
         */
        int m = (size >> 1) + (size & 1);
        int a = x >> m;
        int b = x & ((1 << m) - 1);

        int c = y >> m;
        int d = y & ((1 << m) - 1);

        /**
         * doing the multiplications and
         * updating the number of operations
         * perfomed by the recursive call as well
         */
        int[] acMult = naive(m, a, c);
        int e = acMult[0];
        result[1] += acMult[1];

        int[] bdMult = naive(m, b, d);
        int f = bdMult[0];
        result[1] += bdMult[1];

        int[] bcMult = naive(m, b, c);
        int g = bcMult[0];
        result[1] += bcMult[1];

        int[] adMult = naive(m, a, d);
        int h = adMult[0];
        result[1] += adMult[1];

        result[0] = (e << (2*m)) + ((g + h) << m) + f;
        /**
         * 3 arithmetic operations on numbers
         * of size m performed in calculating result[0] above
         */
        result[1] += 3*m;

        return result;
    }

    public static int[] karatsuba(int size, int x, int y) {

        // YOUR CODE GOES HERE  (Note: Change return statement)
        if(size == 1) {
            return new int[] {x*y, 1};
        }
        int[] result = new int[2];

        /**
         * same idea as for the naive multiplication
         */
        int m = (size >> 1) + (size & 1);
        int a = x >> m;
        int b = x & ((1 << m) - 1);

        int c = y >> m;
        int d = y & ((1 << m) - 1);

        int[] acMult = karatsuba(m, a, c);
        int e = acMult[0];
        result[1] += acMult[1];

        int[] bdMult = karatsuba(m, b, d);
        int f = bdMult[0];
        result[1] += bdMult[1];

        int[] abcdMult = karatsuba(m, a-b, c-d);
        int g = abcdMult[0];
        result[1] += abcdMult[1];

        result[0] = (e << (2*m)) + ((e + f - g) << m) + f;
        /**
         * we recurse less number of times here but
         * 6 arithmetic operations on numbers of size
         * m performed here to calculate result[0] above
         */
        result[1] += 6*m;

        return result;

    }

    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);

                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }

                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }

                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   }
}