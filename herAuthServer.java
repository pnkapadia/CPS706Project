package my706project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//should return IP address for www.herCDN.com
public class herAuthServer {

    public static void main(String argv[]) throws Exception {

    	//Set the records and put it into a test array.
    	Record rec = new Record("herCDN.com", "www.herCDN.com", "CN");
        Record rec2 = new Record("www.herCDN.com", "IPwww.herCDN.com", "A");
        Record test[] = new Record[2];
        test[0] = rec;
        test[1] = rec2;

        //Creates a server socket
        DatagramSocket serverSocket = new DatagramSocket(9877);

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        
//TCP server 
        
        while(true) {
        	
        	//Creates space for received datagram
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
            //Receives the datagram
            serverSocket.receive(receivePacket);
            System.out.println("The herCDN.com packet is received \n");
            
            //Gets the data of the received and stores it into the var
            String var = new String(receivePacket.getData());

            System.out.println("The packet data for herCDN.com is: " + var + "\n");

            //Gets the IP address and port number of the sender.
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

           //Goes through each of the records in the test[] array.
            for(Record record : test){
                String name = var;
                
                //Gets the name and type, if the type is not equal to A. 
                //Then, it just stores the value in the variable.
                if(var.contains(record.getTheName()) && !(record.getTheType().equals("A"))){
                	var = record.getTheValue();
                }
                
                //Gets the name and type, if the type is equal is to A.
                //Then, stores the value in a variable.
                //Also, stores the bytes into the sendData variable.
                else if(var.contains(record.getTheName()) && (record.getTheType().equals("A"))){
                	var = record.getTheValue();
                    System.out.println("We have found the IP Address for " + name + "\n");
                    sendData = var.getBytes();
                    break;
                }
                
                //If the var doesn't have the record name at all.
                //Then, prints out that it doesn't exist.
                else if(!var.contains(record.getTheName())){
                    sendData = "No host exists like that here.".getBytes();
                }
            }

            //Creates a datagram with data-to-send length, IP address, and port
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            //Sends the datagram packet from the server.
            serverSocket.send(sendPacket);
            serverSocket.close();

        }
    }
}