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
        Your program should use a subroutine to handle each request that the server receives.
    It should not stop after handling one request; it should remain open and continue to
    accept new requests.
*/

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;

import static java.lang.System.out;

public class Chapter11Exercise3NetworkServer {

    public static void main(String[] args) {
    /*
        When the server starts up, it needs to know the name of the directory
        that contains the collection of files. This information can be provided as a
        command-line argument.
    */
        ServerSocket server; // server listening socket
        File directory; // to process files in directory
        Socket connection; // server to connect to client

        // Connect to server
        try {
            directory = new File (args[0]); // Check command line for directory
            server = new ServerSocket(1728); // listener
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            while(true) {
                // While loop keeps connection open
                connection = server.accept(); // accept client Socket
                out.println("Client accepted");
                handleConnection(directory, connection); // handle client input
            }
        }
        catch (IOException e) {
            out.println(e);
        }
    } // end class main

    private static void handleConnection(File directory, Socket connection) throws IOException {
        BufferedReader incoming; // incoming stream from client
        PrintWriter outgoing; // writer to send response to client
        String command = "No command"; // content of client commands

        try {
            // Create outgoing and incoming streams
            incoming = new BufferedReader( new InputStreamReader(connection.getInputStream()) );
            outgoing = new PrintWriter(connection.getOutputStream(), true);
            command = incoming.readLine(); // Capture client command
            out.println("Command is " + command); // Log client command

            // Client command, 'index', requests the file names in the directory
            if (command.equalsIgnoreCase("index")) {
                sendIndex(directory, outgoing);
            }
            // Client command, 'get <filename>', requests the contents of a file in a directory
            else if(command.length() >= 3 && command.substring(0,3).equalsIgnoreCase("get")){
                // Checking for a correct command line input
                if (command.length() > 3) {
                    String fileName = command.substring(3, command.length()).trim();
                    sendFileContents(directory, outgoing, fileName);
                }
                else {
                    // If client input is only "get"
                    outgoing.println("You did not type in a file name in addition to the get command.");
                }
            }
            else {
                out.println("ERROR: wrong command entered");
                outgoing.println("ERROR");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }
    } // end handleConnection()

    // Method returns the names of all files in a given directory
    private static void sendIndex(File dir, PrintWriter outgoing) {
        String[] list = dir.list(); // Turn directory into list of file names
        // Send the list of file names to the client
        try {
            for (String item : list) {
                outgoing.println(item);
                out.println(item);
            }
            outgoing.flush();
        }
        catch (Exception e){
            out.println("Error sending index of files");
        }
    } // end sendIndex()

    // Method sends the file contents of a given file in the given directory
    private static void sendFileContents(File dir, PrintWriter outgoing, String fileName) {
        String[] list = dir.list(); // Turn directory into list of file names
        BufferedReader data;
        try {
            for (String item : list) {
                // First check if the file exists
                if(!item.equalsIgnoreCase(fileName)) {
                    outgoing.println("There is no such file"); // Tell client file is not there
                }
                outgoing.println("Ok"); // Confirms that the file exists\
                // New BufferedReader to send the file contents to client, line-by-line
                data = new BufferedReader( new FileReader(fileName) );
                String line = data.readLine();
                while(true){
                    if(line == null) break;
                    outgoing.println(line);
                    line = data.readLine();
                }
            }
            outgoing.flush();
        }
        catch (Exception e){
            out.println("Error sending file content");
        }
    } // end sendFileContents()

} // end class Chapter11Exercise3NetworkServer