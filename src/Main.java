import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Client är nu redo");

        //Init stuff. Set as null to be initialized as "something"
        Socket socket = null;
        InputStreamReader inputSR = null;
        OutputStreamWriter outputSW = null;
        BufferedReader bReader = null;
        BufferedWriter bWriter = null;

        //Starta Klienten
        try {
            //Init Socket med specifik port
            socket = new Socket("localhost", 4321);

            //Initiera Reader och Writer och koppla dem till socket
            inputSR = new InputStreamReader(socket.getInputStream());
            outputSW = new OutputStreamWriter(socket.getOutputStream());
            bReader = new BufferedReader(inputSR);
            bWriter = new BufferedWriter(outputSW);

            //Initiera Scanner för att skriva i konsol
            Scanner scan = new Scanner(System.in);

            while (true) {
                System.out.print("Skriv in ditt meddelande: ");
                String message = scan.nextLine();

                //Skicka meddelande till server
                bWriter.write(message);
                bWriter.newLine();
                bWriter.flush();

                //Skriv ut responseMeddelande från server
                System.out.println(bReader.readLine());

                //Avsluta om QUIT
                if (message.equalsIgnoreCase("quit")) break;
            }

        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                //Stäng kopplingar
                if (socket != null ) socket.close();
                if (inputSR != null ) inputSR.close();
                if (outputSW != null ) outputSW.close();
                if (bWriter != null ) bWriter.close();
                if (bReader != null ) bReader.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Client Avslutas");
        }
    }
}