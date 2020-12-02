// Chapter 8: Correctness, Robustness, Efficiency
// Exceptions, try..catch, annotations, assertions, etc.

import static java.lang.System.out;

public class Chapter8Exercises {
    public static void main(String[] args) {
        Chapter8Exercises OuterClass = new Chapter8Exercises();
        Chapter8Exercises.romanNumerals romanNumString = OuterClass.new romanNumerals("MCMXCV");
        Chapter8Exercises.romanNumerals romanNumInt = OuterClass.new romanNumerals(3);
        Chapter8Exercises.romanNumerals romanNumError = OuterClass.new romanNumerals(3999);

        out.println("Exercise 3");
        out.println("Method that accepts a roman numeral in either letter or integer format");
        out.println("And can output the value in both integer and letter format");
        out.println("Method will throw NumberFormatException error in 4 input cases:");
        out.println("Empty string, invalid roman letter, integer < 0, integer > 3999");
        out.println();

        out.println("Example 1 with integer input: 3");
        out.println("toInt() method returns: " + romanNumInt.toInt());
        out.println("toString() method returns: " + romanNumInt.toString());
        out.println();

        out.println("Example 2 with input of roman numeral: MCMXCV");
        out.println("toInt() method returns: " + romanNumString.toInt());
        out.println("toString() method returns: " + romanNumString.toString());
        out.println();

    }

    // Exercise 3
    // Write a class to represent roman numerals in either roman or arabic numerals
    public class romanNumerals {
        private final int num; // The number input

        // Two arrays that represent roman and arabic numerals in descending order. Note that each index
        // in one array is equal to the value of the the corresponding index in the other array.
        // So, arabicNumbers[i] = romanNumbers[i]
        private int[] arabicNumbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        private String[] romanNumbers = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        // Constructor 1
        // Creates a number based on a letter input
        // Throws a NumberFormatException if the string input is empty.
        public romanNumerals(String str) {
            if (str.length() == 0) throw new NumberFormatException("An empty string is not a valid numeral.");

            int total = 0; // total value of roman numeral
            int i = 0; // counter
            str = str.toUpperCase(); // convert string to upper case

            // While loop traverses the string
            while (i < str.length()) {
                char letter = str.charAt(i);
                // Use helper method letterToNumber (char c) to determine value of the current character
                int currentNumber = letterToNumber(letter);
                i++;

                if (i == str.length()) {
                    // this is the last iteration of the loop, add the value of the final roman numeral to the total
                    total += currentNumber;
                }
                else {
                    // If this is not the last iteration of the loop,
                    // check the value of the next number
                    int nextNumber = letterToNumber(str.charAt(i));

                    // When a letter of smaller value is followed by a letter of larger value,
                    // the smaller value is subtracted from the larger value
                    if (nextNumber > currentNumber) {
                        total += (nextNumber - currentNumber);
                        i++;
                    }
                    // If the next number is smaller than the current number, simply add the current to the total
                    else {
                        total += currentNumber;
                    }
                }
            }
            num = total; // initialize num variable declared above
        }

        // Constructor 2
        // Takes in an integer and stores it in the global variable num;
        // Throws a NumberFormatException if the input is less than 1 or over 3999.
        public romanNumerals(int number) {
            if (number < 1) throw new NumberFormatException("Value of Roman numeral must be positive.");
            if (number > 3999) throw new NumberFormatException("Value of Roman numeral must be less than 3999");
            num = number;
        }

        // Helper method for the constructor with a parameter of type string
        // This method takes a roman letter as a input of type char
        // and returns the corresponding numerical value
        // Throws an error in case the letter is not a valid roman numeral
        private int letterToNumber(char c) {
            switch(c) {
                case 'M': return 1000;
                case 'D': return 500;
                case 'C': return 100;
                case 'L': return 50;
                case 'X': return 10;
                case 'V': return 5;
                case 'I': return 1;
                default: throw new NumberFormatException("The letter, " + c + ", is not a valid Roman numeral");
            }
        }

        // Returns the roman numeral as an integer
        public int toInt() {
            return num;
        }

        // Returns the roman numeral as a string
        public String toString() {
            String roman = ""; // String that will be returned
            int N = num; // the global variable initialized in the constructor

            // Traverse the array, pretty smart!
            for (var i = 0; i < arabicNumbers.length; i++){
                // While the value of the input number is less than the value of the arabic number at the current index
                while (N >= arabicNumbers[i]){
                    roman += romanNumbers[i]; // Add another letter to the string
                    N -= arabicNumbers[i]; // Decrement that value from the input number
                }
            }
            return roman;
        }
    }
}