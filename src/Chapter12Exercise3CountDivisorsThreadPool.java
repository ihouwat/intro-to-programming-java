/*
    Chapter 12: Threads and multiprocessing
    Exercise 3: write a program to to find the integer in the range 1 to 100000 that has the largest
    number of divisors (ie: remainder is 0), using multiple threads. By using threads, your program will take less
    time to do the computation when it is run on a multiprocessor computer. At the end of
    the program, output the elapsed time, the integer that has the largest number of divisors,
    and the number of divisors that it has.

    Implement a thread pool strategy and blocking queue for solving this problem.
    Use two queues in your program. Use a queue of tasks, to hold the tasks that will
    be executed by the thread pool. But also use a queue of results produced by the threads.
    When a task completes, the result from that task should be placed into the result queue.
    The main program can read results from the second queue AS THEY BECOME AVAILABLE,
    and combine all the results to get the final answer.
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.System.out;

public class Chapter12Exercise3CountDivisorsThreadPool {
    private static final int END = 100000;
    private static volatile int maxDivisors; // Maximum number of divisors seen in all threads.
    private static volatile int numWithMaxDivisors;   // A value that has the given number of divisors.
    private static volatile boolean running;  // used to signal the thread to abort
    private static WorkerThread[] workers;  // the threads that compute the tasks
    private static volatile int threadsRunning; // how many threads are still running?
    private static ConcurrentLinkedQueue<Task> taskQueue;  // holds individual tasks
    private static LinkedBlockingQueue<Result> resultsQueue; // blocking queue that stores the results of each

    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        countDivisorsWithThreadPool(processors); // determines how many threads. Argument can be any value of type integer
    } // end main()

    // Class to set up threads
    static class WorkerThread extends Thread {
        public void run() {
            while (true) {
                Task task = taskQueue.poll();
                if (task == null) // task queue is empty, so end the thread
                    break;
                task.compute();
            }
        }
    }

    // Class to define the task of counting divisors for a given range of integers
    static class Task {
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
        public void compute() {

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
            resultsQueue.add( new Result(maxDivisorsInRange, whichInt) ); // add result to the queue
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
        Method to set up workers and create tasks and results queues
        The method accepts an argument of type integer which determines the number of threads.
     */
    private static void countDivisorsWithThreadPool(int numberOfThreads) {
        System.out.println("\nCounting divisors between " + 1 + " and "
                + (END) + " using " + numberOfThreads + " threads...\n");

        long startTime = System.currentTimeMillis(); // for testing time

        // Set up task queue, result blocking queue, and worker threads
        taskQueue = new ConcurrentLinkedQueue<Task>();
        resultsQueue = new LinkedBlockingQueue<Result>();

        workers = new WorkerThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            workers[i] = new WorkerThread();
        }

        /*
            Create the tasks and add them to the task queue.  Each
            task consists of a range of 1000 integers, so the number of
            tasks is (END+999)/1000.  (The "+999"  gives the correct number
            of tasks when MAX is not an exact multiple of 1000.  The last
            task in that case will consist of the last (END%1000)) ints.
        */
        int numberOfTasks = (END + 999) / 1000;
        for (int i = 0; i < numberOfTasks; i++) {
            int start = i*1000 + 1;
            int end = (i+1)*1000;
            if (end > END)
                end = END;
            //System.out.println(start + " " + end);  // for testing
            taskQueue.add( new Task(start,end) ); // add to the task queue
        }

        // Now the task queue is set, start the workers
        for (int i = 0; i < numberOfThreads; i++) {
            workers[i].start();
        }

        // At this point, the threads are executing the tasks and filling the results blocking queue with new results.
        // Have the main program read the results from the queue and compute the final answer
        maxDivisors = 0;
        numWithMaxDivisors = 0;
        /*
            Retrieve and process each result from the results queue.
            The program knows the number of tasks that it needs to process, as specified in the for loop
         */

        for (int i = 0; i < numberOfTasks; i++) {
            try {
                Result result = resultsQueue.take();
                // Updated the global maxDivisors and numWithMaxDivisors variables if needed
                if (result.maxDivisorFromTask > maxDivisors){
                    maxDivisors = result.maxDivisorFromTask;
                    numWithMaxDivisors = result.intWithMaxFromTask;
                }
            }
            catch (InterruptedException e) {
            }
        }

        // Report the results
        long elapsedTime = System.currentTimeMillis() - startTime; // for testing time

        out.println();
        out.println("Among integers between 1 and 100000,");
        out.println("the largest number of divisors between 1 and " + END + " is " + maxDivisors + ".");
        out.println("One integer with that number of divisors is " + numWithMaxDivisors + ".");
        System.out.println("\nTotal elapsed time for processing " + numberOfTasks + " tasks with "
                + numberOfThreads + " threads, using a task queue strategy, was "
                + (elapsedTime/1000.0) + " seconds.\n");
    }   // end countDivisorsWithThreads()

} // end class Chapter12Exercise3CountDivisorsThreadPool
