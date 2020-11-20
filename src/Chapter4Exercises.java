// Chapter 4: Subroutines (writing methods)

import static java.lang.System.out;

public class Chapter4Exercises {
    public static void main(String[] args) {
        out.println("EXERCISE 1 - Capitalize a sentence");
        printCapitalized("He said, \"That is not a good idea\"");
        out.println();

        out.println("EXERCISE 2 - Turn a hex input into its Base-10 version");
        printHexAsBase10("FADE");
        out.println();

        out.println("EXERCISE 3 - Count number of dice rolls");
        rollDice(2);
        out.println();

        out.println("EXERCISE 4 - Average number of times to roll dice results");
        averageNumberOfRolls();
        out.println();

        out.println("EXERCISE 5 - Lambda Expressions");
        double[] numsList = {1, 2, 3, 10, 1000, -1, 10};
        out.println("The largest number in the array is " + maxValue.apply(numsList));
        out.println("The smallest number in the array is " + minValue.apply(numsList));
        out.println("The sum of all numbers in the array is " + sum.apply(numsList));
        out.println("The average of the sum all numbers in the array is " + average.apply(numsList));
        out.println("The number of times 10.0 occurs in the array is " + counter(10.0).apply(numsList));
    }

    // EXERCISE 1
    // change the first letter of each word in the string to upper case
    // Pre-condition: input needs to be a string with only letters, no punctuation marks, except quotes
    // Post-condition: will out put sentence with capitalized case
    public static void printCapitalized(String str) {
        char current, previous; // variables to check current and previous characters in string
        String sentence = ""; // resulting sentence
        sentence += Character.toUpperCase(str.charAt(0)); // add first capitalized letter to new sentence

        // This part checks each letter in the sentence
        for (var i = 1; i < str.length(); i++){
            current = str.charAt( i );
            previous = str.charAt( i - 1 );
            // If the previous character is empty or quotation mark,
            // this indicates current is the first letter in the sentence. Capitalize!
            if( !Character.isLetter( previous ) && current != '\'') {
                Character.toUpperCase(current);
            }
            // Add the current character to the sentence variable
            sentence += current;
        }
        out.println(sentence);
    }

    // EXERCISE 2
    // This method reads a hexadecimal number input by the user and prints the
    // base-10 equivalent.  If the input contains characters that are not
    // hexadecimal numbers, then an error message is printed.
    public static void printHexAsBase10 (String hex) {
        int digit = 0;
        for ( var i = 0; i < hex.length(); i++ ) {
            digit = hexValue(hex.charAt(i)); // helper method below
            if(digit == -1) {
                out.println("Error: Input is not a hexadecimal number");
                return;
            }
            digit = digit*16 + digit;
        }
        out.println("Base-10 value of input is: " + digit);
    }

    // EXERCISE 2 - HELPER
    //    Returns the hexadecimal value of a given character, or -1 if it is not
    //    a valid hexadecimal digit.
    public static int hexValue (char ch) {
        switch (Character.toUpperCase(ch)) { // Note: handles lower and upper case characters
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            case 'A': return 10;
            case 'B': return 11;
            case 'C': return 12;
            case 'D': return 13;
            case 'E': return 14;
            case 'F': return 15;
            default: return -1;
        }
    }

    // EXERCISE 3
    // Simulate rolling a pair of dice until the total on the dice comes up to a given number.
    // Precondition: method takes a parameter of type int, between 2 and 12. Anything else throws an error.
    // Post-condition: method return the number of times the dice were rolled to get the given number.
    public static int rollDice(int result){
        // If the input is larger than 12 or smaller than 2, throw an error
        if(result > 12 || result < 2) {
            throw new IllegalArgumentException("You can't roll a value larger than 12 or smaller than 2 with two dice.");
        }

        int firstDie; //first die
        int secondDie; // second die
        int roll = 0; // result of roll
        int count = 0; // count the number of rolls

        // While you don't roll a pair of dice that adds up to the result, keep rolling and track the number of rolls
        while(roll != result){
                firstDie = (int)(Math.random()*6) + 1;
                secondDie = (int)(Math.random()*6) + 1;
                roll = firstDie + secondDie;
                count++;
        }
//        out.println("It took " + count + " number of rolls to get the number " + result);
        return count;
    }

    // EXERCISE 4
    // Calculate the average number of rolls it takes to get each possible total value, using two pair of dice.
    // The experiment is performed by rolling to get each value 10,000 times.
    public static void averageNumberOfRolls() {
        int count = 0; // keep track of number of rolls
        int result; // result of dice rolls, between 2 and 12

        // For each possible result of a roll of a pair of dice
        for( result = 2; result <=12; result ++){
            int total = 0; // Track the number of rolls it took to get the result
            int average; // Average of number of rolls

            // Roll to get the result 10000 times
            while (count < 10000) {
                total += rollDice(result); // Add the total rolls with each loop
                count++;
            }
            average = total / 10000;
            out.println("Average number of times to roll " + result + ": " + average);
            count = 0;
        }

    }

    // EXERCISE 5
    // These methods are  public static member variables of
    // type ArrayProcessor that process arrays in various ways. One method
    // counts the occurrences of a given values in an array.
    // (Note that these methods depend on interface ArrayProcessor.)

    public static final ArrayProcessor maxValue = array -> {
        double max = array[0];
        for (int i = 0; i < array.length; i++) {
            if(array[i] > max) max = array[i];
        }
        return max;
    };

    public static final ArrayProcessor minValue = array -> {
        double min = array[0];
        for (int i = 0; i < array.length; i++) {
            if(array[i] < min) min = array[i];
        }
        return min;
    };

    public static final ArrayProcessor sum = array -> {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    };

    public static final ArrayProcessor average = array -> {
        double total = 0;
        for (int i = 0; i < array.length; i++) {
            total += array[i];
        }
        return total / array.length;
    };

    public static final ArrayProcessor counter ( double value ) {
        return array -> {
            int count = 0;
            for (int i = 0; i < array.length; i++) {
                if(array[i] == value) count++;
            }
            return count;
        };
    }
}