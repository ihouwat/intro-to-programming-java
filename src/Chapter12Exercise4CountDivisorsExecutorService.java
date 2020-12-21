/*
    Chapter 12: Threads and multiprocessing
    Exercise 4: write a program to to find the integer in the range 1 to 100000 that has the largest
    number of divisors (ie: remainder is 0), using multiple threads. By using threads, your program will take less
    time to do the computation when it is run on a multiprocessor computer. At the end of
    the program, output the elapsed time, the integer that has the largest number of divisors,
    and the number of divisors that it has.

    Write a more program to solve the problem, this time using an ExecutorService and Futures.
    The program should still break up the computation into a fairly large number of fairly small tasks,
    and it should still print out the largest number of divisors and the integer that has that number of divisors.
 */
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.out;

public class Chapter12Exercise4CountDivisorsExecutorService {
    public static void main(String[] args) {
        countDivisorsWithExecutor(); // determines how many threads. Argument can be any value of type integer
    } // end main()

    private static final int END = 100000;
    private static volatile int maxDivisors; // Maximum number of divisors seen in all threads.
    private static volatile int numWithMaxDivisors;   // A value that has the given number of divisors.

    // Class to define the task of counting divisors for a given range of integers
    static class Task implements Callable<Result> {
        int min, max;

        public Task(int min, int max) { // Constructor
            this.min = min;
            this.max = max;
        }
        /*
            compute() method to process all the values of min to max, and
            update the values of max divisors within the current range of ints
            and identify an int that has that number of divisors
        */
        public Result call() {

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

            System.out.println("Processing the divisors between " + min + " and " + max);
            Result result = new Result (maxDivisorsInRange, whichInt);

            return result;
        }
    }

    /*
            Class that stores a result from a given Task. It accepts two variables:
            an integer representing the maximum number of divisors found in that thread
            an integer representing an integer that has that number of divisors.
       */
    private static class Result {
        int maxDivisorFromTask, intWithMaxFromTask; // max number of divisors in the current task and associated integer

        public Result(int divisorCount, int currentNumber) { // Constructor
            this.maxDivisorFromTask = divisorCount;
            this.intWithMaxFromTask = currentNumber;

        }
    } // end method updateGlobalCount

    /*
        Method to create workers based on the number of available threads.
        The method accepts an argument of type integer which determines the number of threads.
     */
    private static void countDivisorsWithExecutor() {
        /*
            Create an executor service to execute the subtasks, with one thread per processor.
            Also create an array list of objects of type Futures that are created by the tasks submitted to the
            ExecutorService
        */
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        ArrayList<Future<Result>> results = new ArrayList<>();

        out.println("\nCounting divisors between " + 1 + " and "
                + (END) + " using " + processors + " threads...\n");

        long startTime = System.currentTimeMillis(); // for testing time

        // Create a large number of tasks
        int numberOfTasks = (END + 999) / 1000;
        for (int i = 0; i < numberOfTasks; i++) {
            int start = i * 1000 + 1;
            int end = (i + 1) * 1000;
            if (end > END)
                end = END;
            //System.out.println(start + " " + end);  // for testing
            Task task = new Task(start, end); // Create task object
            Future<Result> result = executor.submit(task); // create Future object to retrieve future result of task
            results.add(result); // add result to ArrayList of results
        }

        /* Executor has to be shut down, or its existence will stop the Java Virtual
         * Machine from exiting.  (Threads in the executor are not daemon threads.) */
        executor.shutdown();


        // Process results of the subtasks stored in the results ArrayList
        maxDivisors = 0;
        for ( Future<Result> res : results ) {
            try {
                if (res.get().maxDivisorFromTask > maxDivisors){
                    maxDivisors = res.get().maxDivisorFromTask;
                    numWithMaxDivisors = res.get().intWithMaxFromTask;
                }
            }
            catch (Exception e) {
                out.println("Error occurred while computing: " + e);
            }
        }

        // Report result
        long elapsedTime = System.currentTimeMillis() - startTime; // for testing time

        out.println();
        out.println("Among integers between 1 and 100000,");
        out.println("the largest number of divisors between 1 and " + END + " is " + maxDivisors + ".");
        out.println("One integer with that number of divisors is " + numWithMaxDivisors + ".");
        System.out.println("\nTotal elapsed time for processing " + numberOfTasks + " tasks with "
                + processors + " threads, using a task queue strategy, was "
                + (elapsedTime/1000.0) + " seconds.\n");
    } // countDivisorsWithThreads

} // end class Chapter12Exercise4CountDivisorsExecutorService
