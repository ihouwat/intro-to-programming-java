// Chapter 5: Objects and Classes

import static java.lang.System.out;
import java.util.Arrays;

public class Chapter5Exercises {
    public static void main(String[] args) {
        PairOfDice dice = new PairOfDice();
        out.println("EXERCISE 2: Pair of Dice class with getter, setter, and a method to show result of a die roll");
        out.println("Getter method for first die: " + dice.getDie1());
        out.println("Getter method second die: " + dice.getDie2());
        out.println("toString() method outputs die rolls: " + dice.toString());
        out.println();

        out.println("EXERCISE 7a - Administer an addition quiz and show number of correct answers");
        AdministerQuiz quiz = new AdministerQuiz();
        out.println("Getter list of first numbers: " + quiz.getFirstNumber());
        out.println("Getter list of second numbers: " + quiz.getSecondNumber());
        out.println("Getter list of answers: " + quiz.getAnswers());
        out.println("toString() method outputs how many correct answers: " + quiz.toString());
        out.println();

        out.println("EXERCISE 7b - create an interface that allows for different implementations of mathematical operations on integers");
        AdditionQuestion addition = new AdditionQuestion();
        out.println("Interface implementation to add two numbers");
        out.println("What is the question? " + addition.getQuestion());
        out.println("What is the correct answer? " + addition.getCorrectAnswer());
        out.println();
        SubtractionQuestion subtraction = new SubtractionQuestion();
        out.println("Interface implementation to subtract two numbers");
        out.println("What is the question? " + subtraction.getQuestion());
        out.println("What is the correct answer? " + subtraction.getCorrectAnswer());
        out.println();
        MultiplicationQuestion multiplication = new MultiplicationQuestion();
        out.println("Interface implementation to multiply two numbers");
        out.println("What is the question? " + multiplication.getQuestion());
        out.println("What is the correct answer? " + multiplication.getCorrectAnswer());
        out.println();
    }

//    Exercise 1
    public static class PairOfDice {
        private int die1 = 3; // Number showing on the first die.
        private int die2 = 4; // Number showing on the second die.
        public void roll() {
        // Roll the dice by setting each of the dice to be
        // a random number between 1 and 6.
            die1 = (int)(Math.random()*6) + 1;
            die2 = (int)(Math.random()*6) + 1;
        }

        public int getDie1() {
            return die1;
        }

        public int getDie2() {
            return die2;
        }

        @Override
        public String toString() {
            this.roll(); // For the purposes of this exercise, roll the dice in here so that I get different numbers
            if(die1 == die2){
                return "double" + die1;
            }
            else {
                return die1 + " and " + die2;
            }
        }
    } // end class PairOfDice

    //    Exercise 7a - Administer an addition quiz and output how many answers were correct
    public static class AdministerQuiz {
        // The first array holds the first number from every question
        private int[] listOfFirstNumbers = new int[10];
        // The second array holds the second number from every question
        private int[] listOfSecondNumbers = new int[10];
        // This array holds the answers from a fictitious user, where they are trying to get the answer to:
        // listOfFirstNumbers[i] + listOfSecondNumbers[i]
        private int[] listOfAnswers = new int[10];
        int counter = 0; // counts correct answers

        public AdministerQuiz() {
            // Constructor loops over and populates the arrays
            for(var i = 0; i < listOfFirstNumbers.length; i++){
                listOfFirstNumbers[i] = (int)(Math.random()*10) + 1;
                listOfSecondNumbers[i] = (int)(Math.random()*10) + 1; // Cheating here since the second array is of same length
                listOfAnswers[i] = (int)(Math.random()*20) + 1; // in 'real life', this array stores the user's quic answers
            }
        }

        public void gradeQuiz() {
            for(var i = 0; i < listOfAnswers.length; i++) {
                if(listOfAnswers[i] == (listOfFirstNumbers[i] + listOfSecondNumbers[i])){
                    counter++;
                }
            }
        }

        public String getFirstNumber() {
            return Arrays.toString(listOfFirstNumbers);
        }

        public String getSecondNumber() {
            return Arrays.toString(listOfSecondNumbers);
        }

        public String getAnswers() {
            return Arrays.toString(listOfAnswers);
        }

        public String toString() {
            this.gradeQuiz();
            if(counter == 0) {
                return "You got no correct answers.";
            }
            else if(counter == 1) {
                return "You got 1 correct answer.";
            }
            else {
                return "You got " + counter + " correct answers.";
            }
        }
    }

    //    Exercise 7b - Administer an interface for doing multiple operations with integers
    //    (here only addition, subtraction, and multiplication)
    public interface IntQuestion {
        String getQuestion();
        int getCorrectAnswer();
    }

    public static class AdditionQuestion implements IntQuestion{
        private int a, b;
        public AdditionQuestion() { // constructor
            a = (int)(Math.random()*20) + 1;
            b = (int)(Math.random()*10) + 1;
        }
        public String getQuestion() {
            return "Add the integers " + a + " and " + b;
        }

        public int getCorrectAnswer() {
            return a + b;
        }
    }

    public static class SubtractionQuestion implements IntQuestion {
        private int a, b;

        public SubtractionQuestion() { // constructor
            a = (int) (Math.random() * 50) + 1;
            b = (int) (Math.random() * 50) + 1;

            if (a < b) { // Making sure subtraction always results in a positive integer
                int temp = a;
                a = b;
                b = temp;
            }
        }

        public String getQuestion() {
            return "Subtract the integer " + a + " from " + " the integer " + b;
        }

        public int getCorrectAnswer() {
            return a - b;
        }
    }

    public static class MultiplicationQuestion implements IntQuestion {
        private int a, b;
        public MultiplicationQuestion() { // constructor
            a = (int)(Math.random()*50) + 1;
            b = (int)(Math.random()*50) + 1;

        }

        public String getQuestion() {
            return "Multiply the integers " + a + " and " + b;
        }

        public int getCorrectAnswer() {
            return a * b;
        }
    }

}