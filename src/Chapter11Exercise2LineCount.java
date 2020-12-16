/*
        Chapter 11: I/O streams, readers, writers
        Write a program that will count the number of lines in each file that is specified on the
        command line. Assume that the files are text files. Note that multiple files can be specified,
        as in: Chapter11Exercise2LineCount file1.txt file2.txt file3.txt
        Write each file name, along with the number of lines in that file, to standard output. If an
        error occurs while trying to read from one of the files, you should print an error message
        for that file, but you should still process all the remaining files. Use a Scanner or a BufferedReader
        to process each file.
*/

import java.io.*;
import static java.lang.System.out;

public class Chapter11Exercise2LineCount  {
    public static void main(String[] args) {
        /*
         testing command line argument of:
         Chapter11Exercise2LineCount file1.txt file2.txt file3.txt
                for(String arg : args) {
                    out.println(arg);
                };
        */
        BufferedReader text;
        File file;
        int count;

        for(String arg : args) {
            count = 0;
            try {
                file = new File(arg);
                text = new BufferedReader(new FileReader(file));
                try {
                    while (text.readLine() != null) {
                        count++;
                    };
                }
                catch (IOException e) {
                    out.println("Error processing the file " + arg);
                }
                out.println("The file named " + arg + " has " + count + " number of lines.");
            }
            catch (FileNotFoundException e) {
                out.println("The file named " + arg + " was not found.");
            }
        }
    }
}

