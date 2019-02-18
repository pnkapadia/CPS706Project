package my706project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class hisAuthServer {

	//this should return the URL for herCDN.com
	
    public static void main(String argv[]) throws Exception {
		
    		//Set the records and put it into a test array.
	    	Record rec = new Record("herCDN.com","www.herCDN.com","CN");
	    	Record rec2 = new Record("www.herCDN.com","192.168.0.2","A");
	        Record rec3 = new Record("video.netcinema.com", "herCDN.com", "R");
	
	        Record num[] = new Record[3];
	        num[0] = rec;
	        num[1] = rec2;
	        num[2] = rec3;

	    //Creates a server socket
        DatagramSocket serverSocket = new DatagramSocket(9876);

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while(true) {
        	
        	//Creates space for received datagram
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
            //Receives the datagram
            serverSocket.receive(receivePacket);
            System.out.println("The hisCinema.com packet is received \n");
            
            //Gets the data of the received and stores it into the host
            String host = new String(receivePacket.getData());
            System.out.println("The data packet for hisCinema.com is: " + host.trim() + "\n");

            //Gets the IP address and port number of the sender.
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
 
          //Goes through each of the records in the num[] array.
            for(Record record : num){
                String name = host;
                
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
                    System.out.println("The IP address is: " + name + "\n");
                    sendData = host.getBytes();
                    break;
                }
                
              //If it doesn't have the record name at all.
              //Then, prints out that it doesn't exist.
                else if(!host.contains(record.getTheName())){
                    sendData = "No host exists like that here.".getBytes();
                	System.out.println("There's no hostname like that here.");
                }
            }

            //Checks if the host exists.
            //If it does have the name, then it stores the value and bytes into the sendData variable.
            System.out.println("Checks if the host exists. \n");
            if(host.contains(rec.getTheName())){
                sendData = rec.getTheValue().getBytes();
                System.out.println("Success, the host does exist.\n");
            }
            
            //If there is no host, it will store a string and the bytes into the sendData variable.
            else {
                System.out.println("Fails, the host doesn't exist.\n");
                sendData = "No host exists like that here.".getBytes();
            }

          //Creates a datagram with data-to-send length, IP address, and port
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
          //Sends the datagram packet from the server.
            serverSocket.send(sendPacket);
            serverSocket.close();
        }
    }
} 
