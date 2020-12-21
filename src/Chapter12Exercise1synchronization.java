/*
    Chapter 12: Threads and multiprocessing
    Exercise 1: the need for synchronization in multithreaded programs,
    Explore if you can get really get errors by using an unsynchronized counter with multiple
    threads, using the given unsynchronized counter class.

    Solution below: without synchronization, the counter output is incorrect as the number of increments is over 1000, for example.
    Adding synchronization modifer to the inc() method in the Counter class to solve that issue.
*/

import static java.lang.System.out;

public class Chapter12Exercise1synchronization {

    static Counter counter;
    static int numberOfIncrements; // how many times each thread increments the counter

    public static void main(String[] args) {
        // Setup counter, number of increments per thread, number of threads, and workers
        counter = new Counter();
        numberOfIncrements = 100000;
        int numberOfThreads = 30;
        CounterThread[] workers = new CounterThread[numberOfThreads];

        System.out.println("Using " + numberOfThreads + " threads.");
        System.out.println("Each thread increments the counter "
                + numberOfIncrements + " times.");

        // Create workers based on number of threads requested
        for (int i = 0; i < numberOfThreads; i++) {
            workers[i] = new CounterThread();
        }
        // Start each thread
        for (int i = 0; i < numberOfThreads; i++) {
            workers[i].start();
        }
        // Wait for threads to execute
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        out.println("The final value of the counter should be "
                + (numberOfIncrements * numberOfThreads));
        out.println("The counter is actually " + counter.getCount());
    }

    static class Counter {
        int count;
        void inc() {   // Add synchronized modifier To solve the counter issue for larger num of increments or num of threads
            count = count+1;
        }
        int getCount() {
            return count;
        }
    } // end class Counter

    static class CounterThread extends Thread {
        public void run() {
            for (int i = 0; i < numberOfIncrements; i++){
                counter.inc();
            }
        }
    } // end class countDivisorsWithThreads

} // end class Chapter12Exercise1synchronization