/*
    Chapter 11: I/O streams, readers, writers
    For this exercise, you will write a network server program.
    The program is a simple file server that makes a collection of files available for transmission
    to clients. You can assume that the directory contains only text files and no
    subdirectories

    When a client connects to the server, the server first reads a one-line command from
    the client. The command can be the string INDEX. In this case, the server responds by
    sending a list of names of all the files that are available on the server. Or the command can
    be of the form GET <filename>, where <filename> is a file name. The server checks
    whether the requested file actually exists. If so, it first sends the word OK as a message
    to the client. Then it sends the contents of the file and closes the connection. Otherwise,
    it sends a line beginning with the word ERROR to the client and closes the connection.
    (The error response can include an error message on the rest of the line.)
    Your program should use a subroutine to handle each request that the server receives.
    It should not stop after handling one request; it should remain open and continue to
    accept new requests. See the DirectoryList example in Subsection 11.2.2 for help with the
    problem of getting the list of files in the directory.

*/

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.out;
import static java.lang.System.in;

public class Chapter11Exercise3NetworkServer1 {

    public static void main(String[] args) {
    /*
        When the server starts up, it needs to know the name of the directory
        that contains the collection of files. This information can be provided as a
        command-line argument.
    */
        ServerSocket server; // server listening socket
        File directory;
        Socket connection; // server to connect to client

        // Connect to server
        try {
//            if (args.length != 1) throw new IllegalArgumentException("Enter one, and only one, valid directory name in the command line.");
            directory = new File (args[0]);
            server = new ServerSocket(1728);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            while(true) {
                connection = server.accept();
                handleConnection(directory, connection);
            }
        }
        catch (IOException e) {
            out.println(e);
        }
    }

    private static void handleConnection(File directory, Socket connection) {
        Scanner incoming;
        PrintWriter outgoing;
        String command = "No command";

        try {
            out.println("Client accepted");
            incoming = new Scanner( connection.getInputStream() );
            outgoing = new PrintWriter(connection.getOutputStream(), true);
            while(true){
                command = incoming.nextLine();
                if (command.equalsIgnoreCase("index")) {
                    out.print("index");
                    outgoing.println("index");
                }
                else if(command.equalsIgnoreCase("get")){
                    out.println("GET WAS THE INPUT!");
                    outgoing.println("GET IN!");
                }
                else {
                    out.println("ERROR: wrong command entered");
                    outgoing.println("ERROR");
                }
                outgoing.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            }
            catch (IOException e) {
            }
        }
    }
}