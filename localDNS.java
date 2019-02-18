package my706project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class localDNS {

    public static void main(String argv[]) throws Exception {
    	//Creates an array list of records and stores the values in it
        List<Record> records = new ArrayList<>();
        records.add(new Record("herCDN.com", "NSherCDN.com", "NS"));
        records.add(new Record("NSherCDN.com", "127.0.01", "A"));
        records.add(new Record("hiscinema.com", "NShiscinema.com", "NS"));
        records.add(new Record("NShiscinema.com", "127.0.01", "A"));

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

      //Creates a server socket
        DatagramSocket serverSocket = new DatagramSocket(9877);

        while(true) {
        	
        	//Creates space for received datagram
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
          //Receives the datagram
            serverSocket.receive(receivePacket);
            System.out.println("We have received the client's packet.\n");
            
          //Gets the data of the received and stores it into the host
            String host = new String(receivePacket.getData());
            System.out.println("The host that was requested from the client is: " + host + "\n");
            
          //Gets the IP address and port number of the sender.
            InetAddress IPAdd = receivePacket.getAddress();
            int port = receivePacket.getPort();
           
          //Goes through each of the records in the records[] array.
            for(Record record : records){

            	//Gets the name and type, if the type is not equal to A. 
                //Then, it just stores the value in the variable.
                if(host.contains(record.getTheName()) && !(record.getTheType().equals("A"))){
                    host = record.getTheValue();
                }
                
              //Gets the name and type, if the type is equal is to A.
              //Then, stores the value in a variable.
              //Also, stores the bytes into the sendData variable.
                else if(host.contains(record.getTheName()) && (record.getTheType().equals("A"))){
                    host = record.getTheValue();
                    System.out.println("The IP Address is: " + host + "\n");
                    sendData = host.getBytes();
                    break;
                }
                
              //If it doesn't have the record name at all.
              //Then, prints out that it doesn't exist.
                else if(!host.contains(record.getTheName())){
                    sendData = "No host exists like that here.".getBytes();
                }
            }

          //Creates a datagram with data-to-send length, IP address, and port
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAdd, port);
          //Sends the datagram packet from the server.
            serverSocket.send(sendPacket);

        }


    }


	}
