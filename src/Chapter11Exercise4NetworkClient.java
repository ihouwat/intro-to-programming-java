import java.net.*;
import java.util.Scanner;
import java.io.*;

class Chapter11Exercise4NetworkClient {

    // Port number on server, if none is specified on the command line.
    static final String DEFAULT_PORT = "1728";

    public static void main(String[] args) throws IOException {

        String computer;  // The computer where the server is running,
        // as specified on the command line.  It can
        // be either an IP number or a domain name.

        String portStr;   // Port number as a string.
        int port;         // The port on which the server listens.

        Socket connection;      // For communication with the server.

        BufferedReader incoming;  // Stream for receiving data from server.
        PrintWriter outgoing;     // Stream for sending data to server.
        String messageOut;        // A message to be sent to the server.
        String messageIn;         // A message received from the server.

        Scanner userInput;        // A wrapper for System.in, for reading
        // lines of input from the user.


        /* First, get the computer and port number. */

        if (args.length == 0) {
            Scanner stdin = new Scanner(System.in);
            System.out.print("Enter computer name or IP address: ");
            computer = stdin.nextLine();
            System.out.print("Enter port, or press return to use default:");
            portStr = stdin.nextLine();
            if (portStr.length() == 0)
                portStr = DEFAULT_PORT;
        } else {
            computer = args[0];
            if (args.length == 1)
                portStr = DEFAULT_PORT;
            else
                portStr = args[1];
        }
        try {
            port = Integer.parseInt(portStr);
            if (port <= 0 || port > 65535)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Illegal port number, " + args[1]);
            return;
        }


        // Open a connection to the server.  Create streams for communication.

        try {
            System.out.println("Connecting to " + computer + " on port " + port);
            connection = new Socket(computer, port);
            incoming = incoming = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()) );
            outgoing = new PrintWriter(connection.getOutputStream());
            System.out.println("Connected.  Enter your first message.");
        } catch (Exception e) {
            System.out.println("An error occurred while opening connection.");
            System.out.println(e.toString());
            return;
        }

        /* Exchange messages with the other end of the connection.
        * You can send two types of messages: INDEX or GET <filename>   */

        try {
            userInput = new Scanner(System.in);

                System.out.print("SEND:      ");
                messageOut = userInput.nextLine(); // captures your command
                outgoing.println(messageOut); // send command to server
                outgoing.flush();

                System.out.println("WAITING...");
                messageIn = incoming.readLine(); // capture first line of server response
                System.out.println("RECEIVED:  " + messageIn);

                // Loop that prints out all lines from server response
                while (true) {
                    String in = incoming.readLine();
                    if(in == null) break;
                    System.out.println(in);
                }

            if (outgoing.checkError()) {
                throw new IOException("Error occurred while transmitting message.");
            }
        }
        catch (Exception e) {
            System.out.println("Sorry, an error has occurred.  Connection lost.");
            System.out.println(e.toString());
            System.exit(1);
        }
        finally {
            connection.close(); // Close the connection
        }
    }  // end main()


} //end class Chapter11Exercise4NetworkClient
