package my706project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

class UDPClient {

    public static void main(String argv[]) throws Exception {
        int port;

        //Asks the client if they want to go to hisCinema or herCDN
        System.out.print("Enter 1 to connect to hisCinema OR Enter 2 to connect to herCDN: ");
        Scanner scanner = new Scanner(System.in);
        int click = scanner.nextInt();
        
        //If you type 1, connects to hisCinema
        if(click == 1) {
            port = 9876;
            System.out.println("Connected to hisCinema...");
        }
        
        //If you type 2, connects to herCDN
        else if(click == 2){
            port = 9877;
            System.out.println("Connected to herCDN...");
        }
        
        //Otherwise, it will not connect
        else{
            port = 9876;
            System.out.println("Cannot read what you have typed in...");
        }

        while(true) {
        	
        	//Creates an input stream
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            //Creates a client socket
            DatagramSocket clientSocket = new DatagramSocket();

           //Translates the hostname to IP address with DNS
            InetAddress IPAddress = InetAddress.getByName("localhost");

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            String var = input.readLine();

            sendData = var.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String returnedVar = new String(receivePacket.getData());

            System.out.println("FROM SERVER: " + returnedVar.trim());
            clientSocket.close();
        }

    }
}
