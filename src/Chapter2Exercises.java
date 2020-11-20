// Chapter 2: Names and Things

public class Chapter2Exercises {
    public static void main(String[] args) {
        // Exercise #2
        int firstDie; //first die
        int secondDie; // second die
        int roll; // result of roll

        firstDie = (int)(Math.random()*6) + 1;
        secondDie = (int)(Math.random()*6) + 1;
        roll = firstDie + secondDie;

        System.out.println("EXERCISE 2");
        System.out.println("The first die comes up " + firstDie);
        System.out.println("The second die comes up " + secondDie);
        System.out.println("The total roll is " + roll);
        System.out.println();

        // Exercise #6
        String name = "Igor Houwat";
        int space = name.indexOf(' ');
        String firstName = name.substring(0, space);
        String lastName = name.substring(space+1);

        System.out.println("EXERCISE 3");
        System.out.println("Your first name is " + firstName + ", which has " + firstName.length() + " characters");
        System.out.println("Your last name is " + lastName + ", which has " + lastName.length() + " characters");
        System.out.println("Your initials are " + firstName.charAt(0) + lastName.charAt(0));
    }
}
