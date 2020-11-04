public class Exercises {
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
    }
}
