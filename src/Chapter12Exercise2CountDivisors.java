/*
    Chapter 12: Threads and multiprocessing
    Exercise 2: write a program to to find the integer in the range 1 to 100000 that has the largest
    number of divisors (ie: remainder is 0), using multiple threads. By using threads, your program will take less
    time to do the computation when it is run on a multiprocessor computer. At the end of
    the program, output the elapsed time, the integer that has the largest number of divisors,
    and the number of divisors that it has.
 */
import static java.lang.System.out;

public class Chapter12Exercise2CountDivisors {
    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        countDivisorsWithThreads(processors); // determines how many threads. Argument can be any value of type integer

        out.println("Among integers between 1 and 100000,");
        out.println("the largest number of divisors between 1 and " + END + " is " + maxDivisors + ".");
        out.println("One integer with that number of divisors is " + numWithMaxDivisors + ".");

    } // end main()

    private static final int END = 100000;
    private static volatile int maxDivisors; // Maximum number of divisors seen in all threads.
    private static volatile int numWithMaxDivisors;   // A value that has the given number of divisors.

    // Thread
    static class CountDivisorsThread extends Thread {
        int min, max;

        public CountDivisorsThread(int min, int max) { // Constructor
            this.min = min;
            this.max = max;
        }

        /*
            run() method to process all the values of min to max, and
            update the values of max divisors within the current range of ints
            and identify an int that has that number of divisors
        */
        public void run() {
            long startTime = System.currentTimeMillis(); // for testing time

            int maxDivisorsInRange = 0; // number of divisors in the current range
            int whichInt = 0; // the number with the most divisors
            for (int N = min; N <= max; N++) {
                int D;  // A number to be tested to see if it's a divisor of N.
                int count = 0; // int to count divisors

                for (D = 1; D <= N; D++) {  // Count the divisors of N.
                    if (N % D == 0)
                        count++;
                }
                if (count > maxDivisorsInRange) {
                    maxDivisorsInRange = count;
                    whichInt = N;
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime; // for testing time

            System.out.print("Processing the divisors between " + min + " and " + max);
            System.out.println("\nTotal elapsed time for  thread" + this.getName() + ": "
                    + (elapsedTime/1000.0) + " seconds.\n");
            updateGlobalCount(maxDivisorsInRange, whichInt); // calls a synchronized method
        }
    }

       /*
            Synchronized method accepts two arguments from any given thread:
            an integer representing the maximum number of divisors found in that thread
            an integer representing an integer that has that number of divisors.

            The method then updates the global maxDivisors and numWithMaxDivisors variables
       */

    synchronized private static void updateGlobalCount(int divisorCount, int currentNumber) {
       if (divisorCount > maxDivisors) {
            maxDivisors = divisorCount;
            numWithMaxDivisors = currentNumber;
        }
    } // end method updateGlobalCount

    /*
        Method to create workers based on the number of available threads.
        The method accepts an argument of type integer which determines the number of threads.
     */
    private static void countDivisorsWithThreads(int numberOfThreads) {
        System.out.println("\nCounting divisors between " + 1 + " and "
                + (END) + " using " + numberOfThreads + " threads...\n");

        // Setting up worker threads
        CountDivisorsThread[] workers = new CountDivisorsThread[numberOfThreads];
        int integersPerThread = END/numberOfThreads;
        int start = 1;  // Starting point of the range of ints for first thread.
        int end = start + integersPerThread - 1;   // End point of the range of ints.

        // Set up the range of ints to be processed in each thread
        for (int i = 0; i < numberOfThreads; i++) {
            if (i == numberOfThreads - 1) {
                end = END;  // Make sure that the last thread's range goes all
                // the way up to MAX.  Because of rounding, this
                // is not automatic.
            }
            workers[i] = new CountDivisorsThread( start, end );
            start = end+1;    // Determine the range of ints for the NEXT thread.
            end = start + integersPerThread - 1;
        }

        maxDivisors = 0;

        // Starting threads
        for (int i = 0; i < numberOfThreads; i++) {
            workers[i].start();
        }
        // Wait till all threads execute
        for (int i = 0; i < numberOfThreads; i++) {
            while (workers[i].isAlive()) {
                try {
                    workers[i].join();
                }
                catch (InterruptedException e) {
                }
            }
        }
    } // countDivisorsWithThreads
} // end class Chapter12Exercise2CountDivisors
