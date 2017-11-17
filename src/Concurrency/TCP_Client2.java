/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Concurrency;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author mohitgupta
 */
public class TCP_Client2 {
 
    public static void main(String argv[]) throws Exception {
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 6789);
        System.out.println("Start the chitchat, type and press Enter key");

        String receiveMessage, sendMessage;               
        while (true) {
            OutputStream ostream = clientSocket.getOutputStream(); 
            PrintWriter pwrite = new PrintWriter(ostream, true);

            // receiving from server ( receiveRead  object)
            InputStream istream = clientSocket.getInputStream();
            BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
            
            sendMessage = keyRead.readLine();  // keyboard reading
            pwrite.println(sendMessage);       // sending to server
            pwrite.flush();                    // flush the data
            if (sendMessage.equals("EXIT")) {
                break;
            }
            if((receiveMessage = receiveRead.readLine()) != null) //receive from server
            {
                System.out.println(receiveMessage); // displaying at DOS prompt
            }
        }
        clientSocket.close();
    }
}