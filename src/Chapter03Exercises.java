// Chapter 3: Control (loops, blocks, branches)

public class Chapter03Exercises {
    public static void main(String[] args) {

        // TRYING CONTROL STRUCTURES
        int x = 1;

        // If statement
//        if(x == 1){
//            System.out.println("Variable x is " + x);
//        }
//        else{
//            System.out.println("Failure!");
//        }

        // Switch statement - old way
//        switch (x){
//            case 1:
//            case 2:
//                System.out.println("Variable x is " + x);
//                break;
//            default:
//                System.out.println("Failure!");
//        }

        // Switch statement - new way
        switch (x){
            case 1, 2 -> System.out.println("Variable x is " + x);
            default -> System.out.println("Failure!");
        }
        System.out.println();


        // Exercise 1 - count how many times it takes for two dice to get a value of 1
        int firstDie; //first die
        int secondDie; // second die
        int roll = 0; // result of roll
        int count = 0; // count

        while(roll != 2){
            firstDie = (int)(Math.random()*6) + 1;
            secondDie = (int)(Math.random()*6) + 1;
            roll = firstDie + secondDie;
            count++;
        }

        System.out.println("EXERCISE 1");
        System.out.println("It took " + count + " number of rolls until both dice showed a value of 1");
        System.out.println();


        // Exercise 2 - Which integer between 1 and 10000 has the largest number of divisors, and how many divisors?
        int testDivisor; // A number between 1 and N that is a
        int num; // Range of numbers we will test, between 1 and 10000
        int divisorCount; // Number of divisors of N that have been found.
        int maxDivisorCount = 0; // To store largest divisor count
        int maxDivisorNum = 0; // To identify largest divisor num

        for (num = 1; num <= 10000; num++){
            divisorCount = 0;
            for (testDivisor = 1; testDivisor <= num; testDivisor++) {
                if ( num % testDivisor == 0 )
                    divisorCount++;
            }
            if(divisorCount > maxDivisorCount){
                maxDivisorCount = divisorCount;
                maxDivisorNum = num;
            }
        }

        /* Display the result. */
        System.out.println("EXERCISE 2");
        System.out.println("The number " + maxDivisorNum + " has the largest number of divisors, which is " + maxDivisorCount);
        System.out.println();


        // Exercise 4 - take a sentence and print out each word separately
        String sentence = "He said, \"That's not a good idea\"";
        String word = ""; // String to collect separate words
        int index; // index
        char ch; // to test for characters

        System.out.println("EXERCISE 4");
        for(index = 0; index < sentence.length(); index++){
            ch = sentence.charAt(index);
            if(Character.isLetter(ch) || ch == '\'') {
                word += ch;

            } else {
                System.out.println(word);
                word = "";
            }
        }


        // Exercise 6 - print out all numbers, between 1 and 10000, that have the maximum number of divisors
        int testDiv; // A number between 1 and N that is a
        int number; // Range of numbers we will test, between 1 and 10000
        int divCount; // Number of divisors of N that have been found.
        int maxCount = 0; // To store largest divisor count
        int [] divisorsList; // Array to store count of divisors
        divisorsList = new int[10000]; // create array

        // First populate the divisorsList array with the number of divisors for each integer
        for (number = 1; number <= 10000; number++){
            divCount = 0;
            for (testDiv = 1; testDiv <= number; testDiv++) {
                if ( number % testDiv == 0 )
                    divCount++;
            }
            divisorsList[number - 1] = divCount; // Populate the array here
            if(divCount > maxCount){
                maxCount = divCount;
            }
        }
        System.out.println("EXERCISE 4");
        System.out.println("Among integers between 1 and 10000,");
        System.out.println("The maximum number of divisors was " + maxCount);
        System.out.println("Numbers with that many divisors include:");
        // Loop over array and print out numbers with highest divisor counts
        for (number = 0; number < divisorsList.length; number++){
            if(divisorsList[number] == maxCount){
                System.out.println(number + 1);
            }
        }
    }
}
