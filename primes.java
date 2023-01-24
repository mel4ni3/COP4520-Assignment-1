import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.*;

public class primes {

    public static long sum = 0;
    public static long numPrimes = 0;

    public static final int MAXPRIME = 100000000;

    // an array of 0s and 1s to determine if the
    // number of the index is prime
    public static int[] Primes = new int[MAXPRIME + 1];

    // finds the sum and count of primes
    public static void sumCount(int[] Primes) {

        for (int i = 0; i < Primes.length; i++) {

            if (Primes[i] == 1) {
                sum += i;
                numPrimes++;
            }
        }
    }

    public static int[] maxPrimes() {
        int[] MaxPrimes = new int[10]; // the top ten maximum primes
        int count = 9;
        int index = Primes.length - 1;

        while (count >= 0) {

            while (Primes[index] != 1) {
                index--;
            }

            if (Primes[index] == 1) {
                MaxPrimes[count] = index;
                index--;
            }

            count--;

        }

        return MaxPrimes;

    }

    public static void printOutput(long time, long numPrimes, long sum) {

        String data = "Time in milliseconds: " + time + " Number of primes: " + numPrimes + " Sum of primes: " + sum;
        String data2 = " Top ten maximum primes: \n";

        int[] max = maxPrimes();

        try {

            FileWriter primeFile = new FileWriter("primes.txt");
            PrintWriter output = new PrintWriter(primeFile);

            output.print(data);
            output.print(data2);
            for (int n : max) {
                output.print(n);
                output.print("\n");
            }

            output.close();
        }

        catch (Exception e) {

            e.getStackTrace();
        }

    }

    // find prime numbers using the
    // Sieve of Eratosthenes method
    static Runnable primeChecker(int n) {

        // take the square of the current number in the array
        // go to the top of the array and find primes up to n
        for (int i = 2; i * i <= n; i++) {

            // if this number is prime, check numbers
            // greater than or equal to x^2 and divisible
            // by x and mark them as not prime
            if (Primes[i] == 1) {

                for (int j = i * i; j <= n; j += i) {

                    Primes[j] = 0;
                }
            }
        }
        return null;

    }

    public static void main(String[] args) {

        long start = 0;
        long end = 0;
        long duration = 0;
        int n = 10; // used for the maximum number each thread will compute

        // initally mark all numbers in the array as prime
        for (int i = 0; i <= MAXPRIME; i++)
            Primes[i] = 1;

        // 0 and 1 are not prime
        Primes[0] = 0;
        Primes[1] = 0;

        // create an array containing 8 threads
        Thread[] thread = new Thread[8];

        start = System.currentTimeMillis();

        // run the primeChecker function on each thread
        for (int i = 0; i < 8; i++) {

            thread[i] = new Thread(primeChecker(n));
            thread[i].start();

            // change the max number that each thread will compute
            n = n * 10;
        }

        for (int i = 0; i < 8; i++) {

            try {

                // stop each thread
                thread[i].join();

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }

        end = System.currentTimeMillis();

        duration = end - start;

        sumCount(Primes);
        printOutput(duration, numPrimes, sum);

    }

}
