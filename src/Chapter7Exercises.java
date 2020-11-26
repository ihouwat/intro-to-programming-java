// Chapter 7: Arrays and ArrayLists

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;

public class Chapter7Exercises {
    public static void main(String[] args) {
        out.println("EXERCISE 1");
        out.println("Create an ArrayList, populate it with a specified number of random integers,");
        out.println("each integer having a value of 1 to a specified max value.");
        out.println(integerArrayList(10, 15));
        out.println();

        out.println("EXERCISE 2");
        out.println("Take a 2D array and return a transposed array");
        int[][] orig = {
                { 1, 2, 3, 4, 5, 6 },
                { 10, 20, 30, 40, 50, 60 },
                { 100, 200, 300, 400, 500, 600 }
        };
        out.println("Original array:");
        print2DArray(orig);
        out.println();
        out.println("Transposed array:");
        print2DArray(computeTranspose(orig));
        out.println();

        out.println("EXERCISE 5");
        out.println("Read a sequence of real numbers and sort from smallest to largest");
        double[] numsArray = {3, 4, 1, 5, 3.444, 100.2, 3000.1, 0.5};
        out.println();
        out.println("Original sequence of numbers, in array format:");
        out.println(Arrays.toString(numsArray));
        out.println();
        out.println("The numbers in sorted order are:");
        sortNums(numsArray);
    }

    //Exercise 1
    // Create an ArrayList containing several random integers
    // in the range from 1 up to some specified maximum.
    // The number of integers in the ArrayList and the specified max value are parameters
    public static ArrayList integerArrayList (int numOfIntegers, int maxValue) {
        ArrayList result = new ArrayList<Integer>();
        for (var i = 0; i < numOfIntegers; i++) {
            int num = (int)(Math.random() * maxValue) + 1;
            result.add(num);
        }
        return result;
    }

    // Exercise 2
    // Suppose that M is a two-dimensional array that has R rows and C columns. The transpose
    // of M is defined to be an array T that has C rows and R columns such that
    // T[i][j] = M[j][i] for each i and j. Write a function that takes in a matrix and returns the transposed array.
    public static int[][] computeTranspose( int[][] matrix ) {
        int[][] transpose;
        int R = matrix.length;     // the number of rows in matrix
        int C = matrix[0].length;  // the number of columns in matrix
        transpose = new int[C][R];
        for ( int i = 0; i < C; i++) { // goes through ROWS of the transpose
            for ( int j = 0; j < R; j++ ) { // goes through COLUMNS of the transpose
                transpose[i][j] = matrix[j][i];
            }
        }
        return transpose;
    }

    public static void print2DArray ( int[][] matrix ) {
        for(var i = 0; i < matrix.length; i++) {
            out.println( Arrays.toString(matrix[i]) );
        }
    }

    // Exercise 5
    // Read a sequence of positive real numbers and print them sorted from smallest to largest
    // Use variable arity to take in multiple arguments
    // Will use selection sort
    public static void sortNums (double ...nums) {
        // Testing the inputs to ensure all are positive numbers
        for (double number : nums) {
            if(number < 0){
                throw new IllegalArgumentException ("Exercise 5: You cannot include negative numbers in your input");
            }
        }
        // selection sort
        for( var i = 0; i < nums.length; i++) {
            int lowest = i; // keeping track of index including lowest value
            for ( var j = i + 1; j < nums.length; j++ ){
                // if any array element is lower than the current lowest, change lowest
                if( nums[j] < nums[lowest] ) lowest = j;
            }
            // If the element at index i is not the same as the element at index lowest,
            // it means we have found a new lowest. Make the swap
            if ( i != lowest ) {
                double temp = nums[i];
                nums[i] = nums[lowest];
                nums[lowest] = temp;
            }
        }
        for (double number : nums) {
            out.println(number);
        }
    }
}