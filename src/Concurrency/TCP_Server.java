/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Concurrency;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohitgupta
 */
public class TCP_Server {

    public static void main(String argv[]) throws Exception {

        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("Server has started");
        Responder h = new Responder();
        // server runs for infinite time and
        // wait for clients to connect
        while (true) {
            // waiting..
            Socket connectionSocket = welcomeSocket.accept();

            // on connection establishment start a new thread for each client
            // each thread shares a common responder object
            // which will be used to respond every client request
            // need to synchronize method of common object not to have unexpected behaviour
            Thread t = new Thread(new MyServer(h, connectionSocket));

            // start thread
            t.start();
        }
    }
}

class MyServer implements Runnable {

    Responder h;
    Socket connectionSocket;

    public MyServer(Responder h, Socket connectionSocket) {
        this.h = h;
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {

        while (h.responderMethod(connectionSocket)) {
            try {
                // once an conversation with one client done,
                // give chance to other threads
                // so make this thread sleep
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        try {
            connectionSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

class Responder {

    String serverSentence;
    BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));

    // on client process termination or
    // client sends EXIT then to return false to close connection
    // else return true to keep connection alive
    // and continue conversation
    synchronized public boolean responderMethod(Socket connectionSocket) {
        try {


            // sending to client (pwrite object)
            OutputStream ostream = connectionSocket.getOutputStream(); 
            PrintWriter pwrite = new PrintWriter(ostream, true);

            // receiving from server ( receiveRead  object)
            InputStream istream = connectionSocket.getInputStream();
            BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

            String receiveMessage, sendMessage;               
            if((receiveMessage = receiveRead.readLine()) != null) {
                   System.out.println(receiveMessage);         
                }         
                sendMessage = keyRead.readLine(); 
                pwrite.println(sendMessage);             
                pwrite.flush();

            return true;

        } catch (SocketException e) {
            System.out.println("Disconnected");
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
