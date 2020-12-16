/*
    Chapter 11: I/O streams, readers, writers
    Exercise 1: Create a program that lists of files in a directory specified by the user.
    If a directory contains subdirectories, the function will recursively call itself to print
    files at all levels of nesting.

*/

import java.io.File;
import java.util.Scanner;
import static java.lang.System.out;
import static java.lang.System.in;

/*
 * This program lists the files in a directory specified by
 * the user.  The user is asked to type in a directory name.
 * If the name entered by the user is not a directory, a
 * message is printed and the program ends.
 */
public class Chapter11Exercise1DirectoryList {

    public static void main(String[] args) {
        String directoryName;  // Directory name entered by the user.
        File directory;        // File object referring to the directory.
        Scanner scanner;       // For reading a line of input from the user.


        scanner = new Scanner(in);  // scanner reads from standard input.

        out.print("Enter a directory name: ");
        directoryName = scanner.nextLine().trim();
        directory = new File(directoryName);

        if (directory.isDirectory() == false) {
            if (directory.exists() == false)
                out.println("There is no such directory!");
            else
                out.println("That file is not a directory.");
        }
        else {
            out.println("Files in directory \"" + directory + "\":");
            listDirectoryContents(directory, "");
        }

    } // end main()

    public static void listDirectoryContents( File dir, String fileName ) {
        // Constructs the File object representing a file
        // named fileName in the directory specified by dir.
        String[] files = dir.list(); // Array of file names in the directory.
        File f = null; // file object to be used in loop
        for (int i = 0; i < files.length; i++) {
            // Loop through the files in the directory list
            f = new File(dir, files[i]); // Create a new file object
            if(f.isDirectory() == true) { // If that File is a directory, recursively call the function
                listDirectoryContents(f, files[i]);
            }
            else { // If that File is a file, print its name
                out.println(files[i]);
            }
        }
    } //end listDirectoryContents()

} // end class Chapter11Exercise1DirectoryList
