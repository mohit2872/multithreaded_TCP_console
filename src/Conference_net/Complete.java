/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference_net;

import java.io.IOException;

/**
 *
 * @author mohitgupta
 */
public class Complete {
    public static void main(String[] args) throws IOException {
        Server server = new Server(1100);
        Client client1 = new Client("localhost", 1100);
        System.out.println("Client 1 has started");
        Client client2 = new Client("localhost", 1100);
    }
}
